package it.unimib.disco.asia.model;

import java.util.List;

public class SingleColumnReconciliation extends Reconciliation {

    public SingleColumnReconciliation(String value, double threshold, List<String> inferredTypes) {
        super(value, threshold, inferredTypes);
    }

}
