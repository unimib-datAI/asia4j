package it.unimib.disco.asia.model.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomEventMatchRequest extends ASIARequest {
    private List<String> key;
    private List<CustomEventMatchCondition> filters;

    public CustomEventMatchRequest() {
    }

    public CustomEventMatchRequest(List<String> key, List<CustomEventMatchCondition> filters) {
        this.key = key;
        this.filters = filters;
    }

    public List<String> getKey() {
        return key;
    }

    public void setKey(List<String> key) {
        this.key = key;
    }

    public List<CustomEventMatchCondition> getFilters() {
        return filters;
    }

    public void setFilters(List<CustomEventMatchCondition> filters) {
        this.filters = filters;
    }

    public void createkey(){
        if (Objects.nonNull(this.filters)){
            if (Objects.isNull(this.key)) this.key = new ArrayList<>();
            for (CustomEventMatchCondition condition: this.filters) {
                if (condition.isColumn()) this.key.add(condition.getValue());

            }
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomEventMatchRequest that = (CustomEventMatchRequest) o;
        return key.equals(that.key) &&
                filters.equals(that.filters) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, filters);
    }
}
