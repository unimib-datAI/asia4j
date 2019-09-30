package it.unimib.disco.asia.model.request;

import it.unimib.disco.asia.model.Annotation;

import java.util.Objects;

public class ReconciliationRequest extends ASIARequest {
    private Annotation annotation;

    public ReconciliationRequest(Annotation annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReconciliationRequest that = (ReconciliationRequest) o;
        return Objects.equals(annotation, that.annotation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotation);
    }
}
