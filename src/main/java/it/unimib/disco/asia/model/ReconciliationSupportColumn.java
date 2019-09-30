package it.unimib.disco.asia.model;

public class ReconciliationSupportColumn {
    private String value;
    private String property;
    private SimilarityMeasure similarityMeasure;
    private double threshold;
    private Annotation columnAnnotation;

    public ReconciliationSupportColumn(String value, String property, SimilarityMeasure similarityMeasure, double threshold, Annotation columnAnnotation) {
        this.value = value;
        this.property = property;
        this.similarityMeasure = similarityMeasure;
        this.threshold = threshold;
        this.columnAnnotation = columnAnnotation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public SimilarityMeasure getSimilarityMeasure() {
        return similarityMeasure;
    }

    public void setSimilarityMeasure(SimilarityMeasure similarityMeasure) {
        this.similarityMeasure = similarityMeasure;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public Annotation getColumnAnnotation() {
        return columnAnnotation;
    }

    public void setColumnAnnotation(Annotation columnAnnotation) {
        this.columnAnnotation = columnAnnotation;
    }
}
