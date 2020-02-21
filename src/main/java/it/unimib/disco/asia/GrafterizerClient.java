package it.unimib.disco.asia;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.github.sisyphsu.dateparser.DateParserUtils;
import it.unimib.disco.asia.model.*;
import it.unimib.disco.asia.model.request.CustomEventMatchCondition;
import it.unimib.disco.asia.model.request.CustomEventMatchRequest;
import it.unimib.disco.asia.model.request.MediaAttentionRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URL;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GrafterizerClient implements ASIA4J {

    private String endpoint;
    private ObjectMapper mapper = new ObjectMapper();
    private ObjectReader reader = mapper.readerFor(new TypeReference<Object>() {});

    GrafterizerClient(String endpoint) {
        this.endpoint = endpoint;

        if (this.endpoint != null && this.endpoint.charAt(endpoint.length() - 1) != '/')
            this.endpoint += '/';
    }

    public String reconcile(Annotation annotation) {
        try {
            List<PropertyValue> pvList = new ArrayList<>();

            if (annotation.getReconciliation() instanceof MultiColumnReconciliation) {
                List<ReconciliationSupportColumn> supportColumns = ((MultiColumnReconciliation)annotation.getReconciliation()).getSupportColumns();

                for (ReconciliationSupportColumn sc: supportColumns) {
                    if (sc.getColumnAnnotation() != null && sc.getColumnAnnotation().getConciliator().equals(annotation.getConciliator())) {
                        pvList.add(new PropertyValueId(sc.getProperty(), sc.getValue()));
                    } else {
                        try {
                            double d = Double.parseDouble(sc.getValue());
                            pvList.add(new PropertyValueNumber(sc.getProperty(), d));
                        } catch (NumberFormatException | NullPointerException nfe) {
                            pvList.add(new PropertyValueString(sc.getProperty(), sc.getValue()));
                        }
                    }
                }
            }

            ReconciliationQuery q = new ReconciliationQuery(annotation.getReconciliation().getValue(),
                    annotation.getReconciliation().getInferredTypes(), pvList);

            ObjectMapper om = new ObjectMapper();

            String url = String.format("%sreconcile?queries=%s&conciliator=%s",
                    this.endpoint,
                    URLEncoder.encode(om.writeValueAsString(Collections.singletonMap("q0", q)), "UTF-8"),
                    annotation.getConciliator());

            JsonNode results = new ObjectMapper().readTree(new URL(url)).get("q0").get("result");
            if (results.get(0).get("score").asDouble() >= annotation.getReconciliation().getThreshold()) {
                return results.get(0).get("id").asText();
            }

        } catch (Exception ignored) { }

        return "";

    }

    @Deprecated
    public String reconcileSingleColumn(String label, String type, double threshold, String conciliator) {
        List<String> types = null;
        if (type != null)
            types = Collections.singletonList(type);
        return this.reconcile(new Annotation(new SingleColumnReconciliation(label, threshold, types), conciliator));
    }

    public String extendFromConciliator(String id, String property, String conciliator) {
        try {
            String query = String.format("{\"ids\":[\"%s\"],\"properties\":[{\"id\":\"%s\"}]}", id, property);
            String url = String.format("%sextend?extend=%s&conciliator=%s",
                    this.endpoint, URLEncoder.encode(query, "UTF-8"), conciliator);

            // Handle different kind of objects -> keep the first and return it as a string
            return new ObjectMapper().readTree(new URL(url)).get("rows").get(id).get(property).get(0).elements().next().asText();

        } catch (Exception ignored) { }

        return "";
    }

    public String extendSameAs(String id, String source, String target) {
        try {
            String url = String.format("%sgeoExactMatch?ids=%s&source=%s&target=%s",
                    this.endpoint, URLEncoder.encode(id, "UTF-8"), source, target);

            return new ObjectMapper().readTree(new URL(url)).get("rows").get(id).get("exactMatch").get(0).elements().next().asText();
        } catch (Exception ignored) { }

        return "";
    }

    public String extendWeather(String id, String date, String aggregator, String weatherParam, String offset) {
        try {
            String formattedDate = DateParserUtils.parseDateTime(date).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String url = String.format("%sweather?ids=%s&dates=%s&aggregators=%s&weatherParams=%s&offsets=%s",
                    this.endpoint, id, formattedDate, aggregator, weatherParam, offset);
            JsonNode value = new ObjectMapper().readTree(new URL(url)).get(0).get("weatherParameters").get(0).get(aggregator + "Value");
            if (!value.isNull()) {
                return value.asText();
            }
        } catch (Exception ignored) {
            System.out.println(ignored.getMessage());
        }

        return "";
    }

    @Override
    public String extendKeywordsCategories(String keyword) {
        try {
            String formattedkey = keyword.replace(" ", "%20");
            String url = String.format("%skeywordscategories?kws=%s", this.endpoint, formattedkey);
            JsonNode value = new ObjectMapper().readTree(new URL(url)).get(0).get("categories");
            if (!value.isNull()) {
                List<String> list = reader.readValue(value);
                return String.join(",", list);
            }
        } catch (Exception ignored) {
            System.out.println(ignored.getMessage());
        }

        return "";
    }

    @Override
    public String extendCustomEventID(List<CustomEventMatchCondition> filters)  {

        try {

            HttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(this.endpoint+"customevents/match");

            CustomEventMatchRequest req = new CustomEventMatchRequest();
            req.setFilters(filters);
            req.createkey();
            List<CustomEventMatchRequest> customEventMatchRequestList = new ArrayList<>();
            customEventMatchRequestList.add(req);
            String json = mapper.writeValueAsString(customEventMatchRequestList);;
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse response = client.execute(httpPost);

            return EntityUtils.toString(response.getEntity());

        } catch (Exception ignored) {
            System.out.println(ignored.getMessage());
        }

        return "";
    }

    @Override
    public String extendCustomEvent(String id, String propIds) {
        try {

            String url = String.format("%scustomevents/select?ids=%s&propIds=%s", this.endpoint, id, propIds);
            JsonNode value = new ObjectMapper().readTree(new URL(url)).get(0).get(propIds);
            if (!value.isNull()) {
                return value.asText();
            }
        } catch (Exception ignored) {
            System.out.println(ignored.getMessage());
        }

        return "";

    }


    @Override
    public String extendMediaAttention(int offset, String features, String categories,
                                 String startDate, String endDate, String aggregator){

        try {
            HttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(this.endpoint+"mediaattention/enrich");

            MediaAttentionRequest req = new MediaAttentionRequest();
            req.setForecast_offset(offset);
            req.setCategories(Arrays.asList(categories.split(",")));
            req.setFeatures(Arrays.asList(features.split(",")));
            req.setDates(Arrays.asList(startDate, endDate));

//            List<MediaAttentionRequest> mediaAttentionRequestList = Arrays.asList(req);

            String json = mapper.writeValueAsString(req);;
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse response = client.execute(httpPost);

            JsonNode value = new ObjectMapper().readTree(EntityUtils.toString(response.getEntity())).get(0).get("data").get(0).get(features+aggregator);
            if (!value.isNull()) {
                return value.asText();
            }

        } catch (Exception ignored) {
            System.out.println(ignored.getMessage());
        }

        return "";


    }
}
