package it.unimib.disco.asia;

import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.Objects;

public abstract class ASIA4JFactory {

    private static class HashKey {
        private String endpoint;
        private Class<? extends ASIA4J> client;

        HashKey(String endpoint, Class<? extends ASIA4J> client) {
            this.endpoint = endpoint;
            this.client = client;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HashKey hashKey = (HashKey) o;
            return endpoint.equals(hashKey.endpoint) &&
                    client.equals(hashKey.client);
        }

        @Override
        public int hashCode() {
            return Objects.hash(endpoint, client);
        }
    }

    private static Hashtable<HashKey, ASIA4J> ht = new Hashtable<>();

    public static ASIA4J getClient(String endpoint) {
        return getClient(endpoint, ASIAClient.class);
    }

    public static ASIA4J getClient() {
        return getClient(ASIAClient.class);
    }

    public static ASIA4J getClient(Class<? extends ASIA4J> clazz) {
        if (System.getProperty("asiaEndpoint") != null) {
            return getClient(System.getProperty("asiaEndpoint"), clazz);
        } else if (System.getenv("asiaEndpoint") != null) {
            return getClient(System.getenv("asiaEndpoint"), clazz);
        }

        throw new RuntimeException("The asiaEndpoint parameter is not provided");
    }

    public static ASIA4J getClient(String endpoint, Class<? extends ASIA4J> clazz) {
        HashKey k = new HashKey(endpoint, clazz);
        ASIA4J o = ht.get(k);
        if (o == null) {
            try {
                o = clazz.getDeclaredConstructor(String.class).newInstance(endpoint);
                ht.put(k, o);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        return o;
    }
}
