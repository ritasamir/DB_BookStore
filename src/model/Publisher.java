package model;

import javafx.beans.property.SimpleStringProperty;

public class Publisher {
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty address = new SimpleStringProperty();
    private final SimpleStringProperty phone = new SimpleStringProperty();

    public Publisher(String pubName,String pubAdd,String pubPhone){
        name.set(pubName);
        address.set(pubAdd);
        phone.set(pubPhone);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }
}
