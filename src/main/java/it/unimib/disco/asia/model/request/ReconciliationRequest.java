package it.unimib.disco.asia.model.request;

import java.util.Objects;

public class ReconciliationRequest extends ASIARequest {
    private String label;
    private String type;
    private double threshold;
    private String conciliator;

    public ReconciliationRequest(String label, String type, double threshold, String conciliator) {
        this.label = label;
        this.type = type;
        this.threshold = threshold;
        this.conciliator = conciliator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReconciliationRequest that = (ReconciliationRequest) o;
        return Double.compare(that.threshold, threshold) == 0 &&
                label.equals(that.label) &&
                Objects.equals(type, that.type) &&
                conciliator.equals(that.conciliator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, type, threshold, conciliator);
    }
}
