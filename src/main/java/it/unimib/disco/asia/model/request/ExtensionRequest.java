package it.unimib.disco.asia.model.request;

import java.util.Objects;

public class ExtensionRequest extends ASIARequest {
    private String id;
    private String property;
    private String conciliator;

    public ExtensionRequest(String id, String property, String conciliator) {
        this.id = id;
        this.property = property;
        this.conciliator = conciliator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtensionRequest that = (ExtensionRequest) o;
        return id.equals(that.id) &&
                property.equals(that.property) &&
                conciliator.equals(that.conciliator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, property, conciliator);
    }
}
