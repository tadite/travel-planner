package edu.nc.travelplanner.model.action;

public class PickResult {
    private String key;
    private Object value;
    private Class resultClass = String.class;

    public PickResult() {
    }

    public PickResult(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public PickResult(String key, Object value, Class resultClass) {
        this.key = key;
        this.value = value;
        this.resultClass = resultClass;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Class getResultClass() {
        return resultClass;
    }

    public void setResultClass(Class resultClass) {
        this.resultClass = resultClass;
    }
}
