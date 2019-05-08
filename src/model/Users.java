package model;

import javafx.beans.property.SimpleStringProperty;

public class Users {
    private final SimpleStringProperty username = new SimpleStringProperty();
    private final SimpleStringProperty email = new SimpleStringProperty();
    private final SimpleStringProperty firstName = new SimpleStringProperty();
    private final SimpleStringProperty lastName = new SimpleStringProperty();
    private final SimpleStringProperty phone = new SimpleStringProperty();
    private final SimpleStringProperty address = new SimpleStringProperty();
    private final SimpleStringProperty is_manager = new SimpleStringProperty();

    public Users(String[] user_Info){
        username.set(user_Info[0]);
        email.set(user_Info[1]);
        firstName.set(user_Info[2]);
        lastName.set(user_Info[3]);
        phone.set(user_Info[4]);
        address.set(user_Info[5]);
        is_manager.set(user_Info[6]);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
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

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getIs_manager() {
        return is_manager.get();
    }

    public SimpleStringProperty is_managerProperty() {
        return is_manager;
    }

    public void setIs_manager(String is_manager) {
        this.is_manager.set(is_manager);
    }
}
