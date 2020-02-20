package it.unimib.disco.asia.model.request;

public class CustomEventLogicCondition {

    private String propertyID;
    private String operator;
    private String value;
    private boolean column;

    public CustomEventLogicCondition(String propertyID, String operator, String value, boolean column) {
        this.propertyID = propertyID;
        this.operator = operator;
        this.value = value;
        this.column = column;
    }

    public CustomEventLogicCondition() {
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
}
