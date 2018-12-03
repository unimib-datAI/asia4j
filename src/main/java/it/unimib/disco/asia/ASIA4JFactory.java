package it.unimib.disco.asia;

public final class ASIA4JFactory {

    public static ASIA4J build(String endpoint) {
        return new ASIAClient(endpoint);
    }
}
