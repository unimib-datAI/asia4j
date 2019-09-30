package it.unimib.disco.asia.model;

import java.util.Objects;

public class Annotation {
    private String type;
    private Reconciliation reconciliation;
    private String conciliator;

    public Annotation(String type, Reconciliation reconciliation, String conciliator) {
        this.type = type;
        this.reconciliation = reconciliation;
        this.conciliator = conciliator;
    }

    public Annotation(Reconciliation reconciliation, String conciliator) {
        this(null, reconciliation, conciliator);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Reconciliation getReconciliation() {
        return reconciliation;
    }

    public void setReconciliation(Reconciliation reconciliation) {
        this.reconciliation = reconciliation;
    }

    public String getConciliator() {
        return conciliator;
    }

    public void setConciliator(String conciliator) {
        this.conciliator = conciliator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Annotation that = (Annotation) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(reconciliation, that.reconciliation) &&
                Objects.equals(conciliator, that.conciliator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, reconciliation, conciliator);
    }
}
