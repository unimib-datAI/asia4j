package it.unimib.disco.asia;

public interface ASIA4J {

    String reconcile(String label, String type, double threshold, String conciliator);

    String extend(String id, String property, String conciliator);

    String extendWeather(String id, String date, String aggregator, String weatherParam, String offset);

    String geoExactMatch(String id, String source, String target);

}
