package it.unimib.disco.asia.model;

public class PropertyValueId extends PropertyValue<PropertyValueId.Id> {

    static class Id {
        private String id;

        Id(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    public PropertyValueId(String propertyId, String id) {
        this.value = new Id(id);
        this.property = propertyId;
    }
}
