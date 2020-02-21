package it.unimib.disco.asia;

import it.unimib.disco.asia.model.request.CustomEventMatchCondition;

import java.util.List;

public interface ASIA4J {

    String reconcile(String label, String type, double threshold, String conciliator);

    String extend(String id, String property, String conciliator);

    String extendWeather(String id, String date, String aggregator, String weatherParam, String offset);

    String geoExactMatch(String id, String source, String target);

    String keywordClustering(String keyword);

    String customEventMatcher(List<CustomEventMatchCondition> filters);

    String customEventSelect(String id, String propIds);
}
