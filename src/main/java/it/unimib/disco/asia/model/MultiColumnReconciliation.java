package it.unimib.disco.asia.model;

import java.util.List;
import java.util.Objects;

public class MultiColumnReconciliation extends Reconciliation {
    private List<ReconciliationSupportColumn> supportColumns;

    public MultiColumnReconciliation(String value, double threshold, List<String> inferredTypes, List<ReconciliationSupportColumn> supportColumns) {
        super(value, threshold, inferredTypes);
        this.supportColumns = supportColumns;
    }

    public List<ReconciliationSupportColumn> getSupportColumns() {
        return supportColumns;
    }

    public void setSupportColumns(List<ReconciliationSupportColumn> supportColumns) {
        this.supportColumns = supportColumns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MultiColumnReconciliation that = (MultiColumnReconciliation) o;
        return Objects.equals(supportColumns, that.supportColumns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), supportColumns);
    }
}
