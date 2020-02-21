package it.unimib.disco.asia;

import it.unimib.disco.asia.model.request.*;

import java.util.Hashtable;
import java.util.List;

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

    public String keywordClustering(String keyword) {
        ASIARequest req = new KeywordClusteringRequest(keyword);
        return ht.computeIfAbsent(req, k -> super.keywordClustering(keyword));
    }

    public String extendWeather(String id, String date, String aggregator, String weatherParam, String offset) {
        ASIARequest req = new WeatherRequest(id, date, aggregator, weatherParam, offset);
        return ht.computeIfAbsent(req, k -> super.extendWeather(id, date, aggregator, weatherParam, offset));
    }



    public String customEventMatcher(List<CustomEventMatchCondition> filters)  {
        CustomEventMatchRequest req = new CustomEventMatchRequest();
        req.setFilters(filters);
        req.createkey();
        return ht.computeIfAbsent(req, k -> super.customEventMatcher(filters));
    }

    public String customEventSelect(String id, String propIds) {
        ASIARequest req = new CustomEventSelectRequest(id, propIds);
        return ht.computeIfAbsent(req, k -> super.customEventSelect(id, propIds));
    }

    public String mediaAttention(int offset, String features, String categories,
                                 String startDate, String endDate, String aggregator){

        ASIARequest req = new MediaAttentionClientRequest(offset,features,categories,startDate,endDate,aggregator);
        return ht.computeIfAbsent(req, k-> super.mediaAttention(offset,features,categories,startDate,endDate,aggregator));
    }



    }
