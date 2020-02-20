package it.unimib.disco.asia;

import it.unimib.disco.asia.model.request.CustomEventLogicCondition;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ASIA4J {

    String reconcile(String label, String type, double threshold, String conciliator);

    String extend(String id, String property, String conciliator);

    String extendWeather(String id, String date, String aggregator, String weatherParam, String offset);

    String geoExactMatch(String id, String source, String target);

    String keywordClustering(String keyword);

    String customEventMatcher(List<CustomEventLogicCondition> filters);

    String customEventSelect(String id, String propIds);
}
