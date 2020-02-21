package it.unimib.disco.asia;

import it.unimib.disco.asia.model.Annotation;
import it.unimib.disco.asia.model.request.CustomEventMatchCondition;

import java.util.List;

public interface ASIA4J {

    String reconcile(Annotation annotation);

    String extendFromConciliator(String id, String property, String conciliator);

    String extendWeather(String id, String date, String aggregator, String weatherParam, String offset);

    String extendSameAs(String id, String source, String target);

    String extendKeywordsCategories(String keyword);

    String extendCustomEventID(List<CustomEventMatchCondition> filters);

    String extendCustomEvent(String id, String propIds);

    String extendMediaAttention(int offset, String features, String categories,
                          String startDate, String endDate, String aggregator);
}
