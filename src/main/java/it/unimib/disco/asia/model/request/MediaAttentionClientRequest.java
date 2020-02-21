package it.unimib.disco.asia.model.request;


import java.util.Objects;

public class MediaAttentionClientRequest extends ASIARequest {

    private int offset;
    private String features;


    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public MediaAttentionClientRequest(int offset, String features, String categories, String startDate, String endDate, String aggregator) {
        this.offset = offset;
        this.features = features;
        this.categories = categories;
        this.startDate = startDate;
        this.endDate = endDate;
        this.aggregator = aggregator;
    }

    public MediaAttentionClientRequest() {
    }

    private String categories;
    private String startDate;
    private String endDate;

    public String getAggregator() {
        return aggregator;
    }

    public void setAggregator(String aggregator) {
        this.aggregator = aggregator;
    }

    private String aggregator;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MediaAttentionClientRequest that = (MediaAttentionClientRequest) o;
        return offset == that.offset &&
                features.equals(that.features) &&
                categories.equals(that.categories) &&
                startDate.equals(that.startDate) &&
                endDate.equals(that.endDate) &&
                aggregator.equals(that.aggregator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offset, features, startDate, endDate, aggregator);
    }
}
