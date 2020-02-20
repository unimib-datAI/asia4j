package it.unimib.disco.asia.model.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomEventLogicRequest extends ASIARequest {
    private List<String> key;
    private List<CustomEventLogicCondition> filters;

    public CustomEventLogicRequest() {
    }

    public CustomEventLogicRequest(List<String> key, List<CustomEventLogicCondition> filters) {
        this.key = key;
        this.filters = filters;
    }

    public List<String> getKey() {
        return key;
    }

    public void setKey(List<String> key) {
        this.key = key;
    }

    public List<CustomEventLogicCondition> getFilters() {
        return filters;
    }

    public void setFilters(List<CustomEventLogicCondition> filters) {
        this.filters = filters;
    }

    public void createkey(){
        if (Objects.nonNull(this.filters)){
            if (Objects.isNull(this.key)) this.key = new ArrayList<>();
            for (CustomEventLogicCondition condition: this.filters) {
                if (condition.isColumn()) this.key.add(condition.getValue());

            }
        }
    }


    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
