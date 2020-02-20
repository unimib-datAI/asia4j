package it.unimib.disco.asia;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.github.sisyphsu.dateparser.DateParserUtils;
import it.unimib.disco.asia.model.request.CustomEventLogicCondition;
import it.unimib.disco.asia.model.request.CustomEventLogicRequest;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ASIAClient implements ASIA4J {

    private String endpoint;

    private ObjectMapper mapper = new ObjectMapper();

    private ObjectReader reader = mapper.readerFor(new TypeReference<Object>() {
    });

    ASIAClient(String endpoint) {
        this.endpoint = endpoint;

        if (this.endpoint != null && this.endpoint.charAt(endpoint.length() - 1) != '/')
            this.endpoint += '/';
    }

    public String reconcile(String label, String type, double threshold, String conciliator) {
        try {
            String query = (type == null || type.isEmpty()) ?
                    String.format("{\"q0\": {\"query\": \"%s\"}}", label) :
                    String.format("{\"q0\": {\"query\": \"%s\",  \"type\":\"%s\", \"type_strict\":\"should\"}}", label, type);

            String url = String.format("%sreconcile?queries=%s&conciliator=%s",
                    this.endpoint, URLEncoder.encode(query, "UTF-8"), conciliator);

            JsonNode results = new ObjectMapper().readTree(new URL(url)).get("q0").get("result");
            if (results.get(0).get("score").asDouble() >= threshold) {
                return results.get(0).get("id").asText();
            }
        } catch (Exception ignored) {
        }

        return "";
    }

    public String extend(String id, String property, String conciliator) {
        try {
            String query = String.format("{\"ids\":[\"%s\"],\"properties\":[{\"id\":\"%s\"}]}", id, property);
            String url = String.format("%sextend?extend=%s&conciliator=%s",
                    this.endpoint, URLEncoder.encode(query, "UTF-8"), conciliator);

            // Handle different kind of objects -> keep the first and return it as a string
            return new ObjectMapper().readTree(new URL(url)).get("rows").get(id).get(property).get(0).elements().next().asText();

        } catch (Exception ignored) {
        }

        return "";
    }

    public String geoExactMatch(String id, String source, String target) {
        try {
            String url = String.format("%sgeoExactMatch?ids=%s&source=%s&target=%s",
                    this.endpoint, URLEncoder.encode(id, "UTF-8"), source, target);

            return new ObjectMapper().readTree(new URL(url)).get("rows").get(id).get("exactMatch").get(0).elements().next().asText();
        } catch (Exception ignored) {
        }

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
    public String keywordClustering(String keyword) {
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
    public String customEventMatcher(List<CustomEventLogicCondition> filters)  {

        try {

            HttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(this.endpoint+"customevents/match");

            CustomEventLogicRequest req = new CustomEventLogicRequest();
            req.setFilters(filters);
            req.createkey();
            List<CustomEventLogicRequest> customEventLogicRequestList = new ArrayList<>();
            customEventLogicRequestList.add(req);
            String json = mapper.writeValueAsString(customEventLogicRequestList);;
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
    public String customEventSelect(String id, String propIds) {
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

}
