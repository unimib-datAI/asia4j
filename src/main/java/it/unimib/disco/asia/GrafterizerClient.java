package it.unimib.disco.asia;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unimib.disco.asia.model.*;

import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

public class GrafterizerClient implements ASIA4J {

    private String endpoint;

    GrafterizerClient(String endpoint) {
        this.endpoint = endpoint;

        if (this.endpoint != null && this.endpoint.charAt(endpoint.length() -1) != '/')
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

    public String extend(String id, String property, String conciliator) {
        try {
            String query = String.format("{\"ids\":[\"%s\"],\"properties\":[{\"id\":\"%s\"}]}", id, property);
            String url = String.format("%sextend?extend=%s&conciliator=%s",
                    this.endpoint, URLEncoder.encode(query, "UTF-8"), conciliator);

            // Handle different kind of objects -> keep the first and return it as a string
            return new ObjectMapper().readTree(new URL(url)).get("rows").get(id).get(property).get(0).elements().next().asText();

        } catch (Exception ignored) { }

        return "";
    }

    public String geoExactMatch(String id, String source, String target) {
        try {
            String url = String.format("%sgeoExactMatch?ids=%s&source=%s&target=%s",
                    this.endpoint, URLEncoder.encode(id, "UTF-8"), source, target);

            return new ObjectMapper().readTree(new URL(url)).get("rows").get(id).get("exactMatch").get(0).elements().next().asText();
        } catch (Exception ignored) { }

        return "";
    }

    public String extendWeather(String id, String date, String aggregator, String weatherParam, String offset) {
        try {
            String url = String.format("%sweather?ids=%s&dates=%s&aggregators=%s&weatherParams=%s&offsets=%s",
                    this.endpoint, id, date, aggregator, weatherParam, offset);
            JsonNode value = new ObjectMapper().readTree(new URL(url)).get(0).get("weatherParameters").get(0).get("value");
            if (!value.isNull()) {
                return value.asText();
            }
        } catch (Exception ignored) { }

        return "";
    }
}
