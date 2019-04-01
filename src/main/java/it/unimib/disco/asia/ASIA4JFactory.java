package it.unimib.disco.asia;

import java.util.Hashtable;

public abstract class ASIA4JFactory {

    private static Hashtable<String, ASIA4J> ht = new Hashtable<>();

    public static ASIA4J getClient(String endpoint) {

        ASIA4J o = ht.get(endpoint);
        if (o == null) {
            o = new ASIAClient(endpoint);
            ht.put(endpoint, o);
        }

        return o;
    }

    public static ASIA4J getClient() {
        if (System.getProperty("asiaEndpoint") != null) {
            return ASIA4JFactory.getClient(System.getProperty("asiaEndpoint"));
        } else if (System.getenv("asiaEndpoint") != null) {
            return ASIA4JFactory.getClient(System.getenv("asiaEndpoint"));
        }

        throw new RuntimeException("The asiaEndpoint parameter is not provided");
    }
}
