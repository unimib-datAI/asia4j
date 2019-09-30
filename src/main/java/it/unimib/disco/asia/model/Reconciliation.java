package it.unimib.disco.asia.model;

import java.util.List;
import java.util.Objects;

public abstract class Reconciliation {
    private String value;
    private double threshold;
    private List<String> inferredTypes;

    public Reconciliation(String value, double threshold, List<String> inferredTypes) {
        this.value = value;
        this.threshold = threshold;
        this.inferredTypes = inferredTypes;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public List<String> getInferredTypes() {
        return inferredTypes;
    }

    public void setInferredTypes(List<String> inferredTypes) {
        this.inferredTypes = inferredTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reconciliation that = (Reconciliation) o;
        return Double.compare(that.threshold, threshold) == 0 &&
                Objects.equals(value, that.value) &&
                Objects.equals(inferredTypes, that.inferredTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, threshold, inferredTypes);
    }
}
