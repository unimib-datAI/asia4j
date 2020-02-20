package it.unimib.disco.asia.model.request;

import java.util.Objects;

public class KeywordClusteringRequest extends ASIARequest {

    public KeywordClusteringRequest(String keyword) {
        this.keyword = keyword;
    }

    private String keyword;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeywordClusteringRequest that = (KeywordClusteringRequest) o;
        return keyword.equals(that.keyword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyword);
    }
}
