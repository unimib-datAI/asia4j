package it.unimib.disco.asia;

import it.unimib.disco.asia.model.request.*;

import java.util.Hashtable;

public class ASIAHashtableClient extends ASIAClient implements ASIA4J {

    private static Hashtable<ASIARequest, String> ht = new Hashtable<>();

    ASIAHashtableClient(String endpoint) {
        super(endpoint);
    }

    public String reconcile(String label, String type, double threshold, String conciliator) {
        ASIARequest req = new ReconciliationRequest(label, type, threshold, conciliator);
        return ht.computeIfAbsent(req, k -> super.reconcile(label, type, threshold, conciliator));
    }

    public String extend(String id, String property, String conciliator) {
        ASIARequest req = new ExtensionRequest(id, property, conciliator);
        return ht.computeIfAbsent(req, k -> super.extend(id, property, conciliator));
    }

    public String geoExactMatch(String id, String source, String target) {
        ASIARequest req = new GeoExactMatchRequest(id, source, target);
        return ht.computeIfAbsent(req, k -> super.geoExactMatch(id, source, target));
    }

    public String extendWeather(String id, String date, String aggregator, String weatherParam, String offset) {
        ASIARequest req = new WeatherRequest(id, date, aggregator, weatherParam, offset);
        return ht.computeIfAbsent(req, k -> super.extendWeather(id, date, aggregator, weatherParam, offset));
    }
}