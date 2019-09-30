package it.unimib.disco.asia.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ReconciliationQuery {

    private String query;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("type")
    private String types;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("type_strict")
    private String typeStrict;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<PropertyValue> properties;

    public ReconciliationQuery(String query, List<String> types, List<PropertyValue> properties) {
        this.query = query;
        if (types != null && types.size() > 0) {
            this.typeStrict = "should";
            this.types = types.get(0); // TODO: change when the types array will be handled by ASIA-backend
        }
        this.properties = properties;
    }

    public ReconciliationQuery(String query, List<String> types) {
        this(query, types, null);
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(List<String> type) {
        if (type.size() > 0)
            this.types = type.get(0);
        else
            this.types = null;
    }

    public String getTypeStrict() {
        return typeStrict;
    }

    public void setTypeStrict(String typeStrict) {
        this.typeStrict = typeStrict;
    }

    public List<PropertyValue> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyValue> properties) {
        this.properties = properties;
    }
}
