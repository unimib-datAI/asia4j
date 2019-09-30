package it.unimib.disco.asia;

import it.unimib.disco.asia.model.Annotation;

public interface ASIA4J {

    String reconcile(Annotation annotation);

    String extend(String id, String property, String conciliator);

    String extendWeather(String id, String date, String aggregator, String weatherParam, String offset);

    String geoExactMatch(String id, String source, String target);

}
