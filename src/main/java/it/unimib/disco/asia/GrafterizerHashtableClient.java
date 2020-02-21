package it.unimib.disco.asia;

import it.unimib.disco.asia.model.Annotation;
import it.unimib.disco.asia.model.request.*;

import java.util.Hashtable;
import java.util.List;

public class GrafterizerHashtableClient extends GrafterizerClient {

    private static Hashtable<ASIARequest, String> ht = new Hashtable<>();

    GrafterizerHashtableClient(String endpoint) {
        super(endpoint);
    }

    public String reconcile(Annotation annotation) {
        ASIARequest req = new ReconciliationRequest(annotation);
        return ht.computeIfAbsent(req, k -> super.reconcile(annotation));
    }

    public String extendFromConciliator(String id, String property, String conciliator) {
        ASIARequest req = new ExtensionRequest(id, property, conciliator);
        return ht.computeIfAbsent(req, k -> super.extendFromConciliator(id, property, conciliator));
    }

    public String extendSameAs(String id, String source, String target) {
        ASIARequest req = new GeoExactMatchRequest(id, source, target);
        return ht.computeIfAbsent(req, k -> super.extendSameAs(id, source, target));
    }

    public String extendKeywordsCategories(String keyword) {
        ASIARequest req = new KeywordClusteringRequest(keyword);
        return ht.computeIfAbsent(req, k -> super.extendKeywordsCategories(keyword));
    }

    public String extendWeather(String id, String date, String aggregator, String weatherParam, String offset) {
        ASIARequest req = new WeatherRequest(id, date, aggregator, weatherParam, offset);
        return ht.computeIfAbsent(req, k -> super.extendWeather(id, date, aggregator, weatherParam, offset));
    }

    public String extendCustomEventID(List<CustomEventMatchCondition> filters)  {
        CustomEventMatchRequest req = new CustomEventMatchRequest();
        req.setFilters(filters);
        req.createkey();
        return ht.computeIfAbsent(req, k -> super.extendCustomEventID(filters));
    }

    public String extendCustomEvent(String id, String propIds) {
        ASIARequest req = new CustomEventSelectRequest(id, propIds);
        return ht.computeIfAbsent(req, k -> super.extendCustomEvent(id, propIds));
    }

    public String extendMediaAttention(int offset, String features, String categories,
                                 String startDate, String endDate, String aggregator){

        ASIARequest req = new MediaAttentionClientRequest(offset,features,categories,startDate,endDate,aggregator);
        return ht.computeIfAbsent(req, k-> super.extendMediaAttention(offset,features,categories,startDate,endDate,aggregator));
    }
}
