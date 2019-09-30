package it.unimib.disco.asia.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class PropertyValue<T> {
    @JsonProperty("p")
    String property;
    @JsonProperty("v")
    T value;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
