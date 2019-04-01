package it.unimib.disco.asia.model.request;

import java.util.Objects;

public class GeoExactMatchRequest extends ASIARequest {

    private String id;
    private String source;
    private String target;

    public GeoExactMatchRequest(String id, String source, String target) {
        this.id = id;
        this.source = source;
        this.target = target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoExactMatchRequest that = (GeoExactMatchRequest) o;
        return id.equals(that.id) &&
                source.equals(that.source) &&
                target.equals(that.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, source, target);
    }
}
