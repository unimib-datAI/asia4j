package it.unimib.disco.asia.model;

public class PropertyValueNumber extends PropertyValue<Double> {

    public PropertyValueNumber(String property, double valueNumber) {
        this.property = property;
        this.value = valueNumber;
    }

}
