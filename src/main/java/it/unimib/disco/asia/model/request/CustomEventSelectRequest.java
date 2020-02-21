package it.unimib.disco.asia.model.request;

import java.util.Objects;

public class CustomEventSelectRequest extends ASIARequest {

    private String id;
    private String propIds;

    public CustomEventSelectRequest() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPropIds() {
        return propIds;
    }

    public void setPropIds(String propIds) {
        this.propIds = propIds;
    }

    public CustomEventSelectRequest(String id, String propIds) {
        this.id = id;
        this.propIds = propIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomEventSelectRequest that = (CustomEventSelectRequest) o;
        return id.equals(that.id) &&
                propIds.equals(that.propIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, propIds);
    }
}
