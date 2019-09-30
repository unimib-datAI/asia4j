package it.unimib.disco.asia.model;

public class PropertyValueString extends PropertyValue<String> {

    public PropertyValueString(String property, String valueString) {
        this.property = property;
        this.value = valueString;
    }
}