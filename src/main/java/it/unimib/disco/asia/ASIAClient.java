package it.unimib.disco.asia;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sisyphsu.dateparser.DateParserUtils;

import java.net.URL;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;

public class ASIAClient implements ASIA4J {

    private String endpoint;

    ASIAClient(String endpoint) {
        this.endpoint = endpoint;

        if (this.endpoint != null && this.endpoint.charAt(endpoint.length() -1) != '/')
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

            String formattedDate = DateParserUtils.parseDateTime(date).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String url = String.format("%sweather?ids=%s&dates=%s&aggregators=%s&weatherParams=%s&offsets=%s",
                    this.endpoint, id, formattedDate, aggregator, weatherParam, offset);
//            System.out.println(new ObjectMapper().readTree(new URL(url)).get(0).get("weatherParameters").get(0).asText());
            JsonNode value = new ObjectMapper().readTree(new URL(url)).get(0).get("weatherParameters").get(0).get(aggregator+"Value");
            if (!value.isNull()) {
                return value.asText();
            }
        } catch (Exception ignored) {
            System.out.println(ignored.getMessage());
        }

        return "";
    }
}
