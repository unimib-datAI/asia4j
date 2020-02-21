package it.unimib.disco.asia.model.request;

import java.util.Objects;

public class CustomEventMatchCondition {

    private String propertyID;
    private String operator;
    private String value;
    private boolean column;

    public CustomEventMatchCondition(String propertyID, String operator, String value, boolean column) {
        this.propertyID = propertyID;
        this.operator = operator;
        this.value = value;
        this.column = column;
    }

    public CustomEventMatchCondition() {
    }

    public String getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(String propertyID) {
        this.propertyID = propertyID;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isColumn() {
        return column;
    }

    public void setColumn(boolean column) {
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomEventMatchCondition that = (CustomEventMatchCondition) o;
        return propertyID.equals(that.propertyID) &&
                operator.equals(that.operator) &&
                value.equals(that.value) &&
                column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(propertyID, operator, value, column);
    }
}
