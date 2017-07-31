package edu.wgu.scheduler.models;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 20:52.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class CustomerView implements ICustomerView {

    private ReadOnlyStringWrapper customerName;
    private ReadOnlyStringWrapper address;
    private ReadOnlyStringWrapper address2;
    private ReadOnlyStringWrapper city;
    private ReadOnlyStringWrapper postalCode;
    private ReadOnlyStringWrapper country;
    private ReadOnlyStringWrapper phone;
    private ReadOnlyBooleanWrapper active;
    private final LocalDate createDate;
    private final String createdBy;
    private ReadOnlyStringWrapper lastUpdateBy;
    private ReadOnlyObjectWrapper<ZonedDateTime> lastUpdate;


    public CustomerView(String customerName, String address, String address2, String city, String postalCode, String country, String phone, boolean active, LocalDate createDate, String createdBy, String lastUpdateBy, ZonedDateTime lastUpdate ){
        this.customerName.setValue(customerName);
        this.address.setValue(address);
        this.address2.setValue(address2);
        this.city.setValue(city);
        this.postalCode.setValue(postalCode);
        this.country.setValue(country);
        this.phone.setValue(phone);
        this.active.setValue(active);
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdateBy.setValue(lastUpdateBy);
        this.lastUpdate.setValue(lastUpdate);
    }
    
    public boolean isActive() {
        return false;
    }

    public ReadOnlyStringProperty customerName(){
        return customerName.getReadOnlyProperty();
    }

    public int getAddressId() {
        return 0;
    }

    public LocalDate getCreateDate() {
        return null;
    }

    public String getCreatedBy() {
        return null;
    }

    public String getCustomerName() {
        return null;
    }

    public String getLastUpdateBy() {
        return null;
    }

    public String getAddress() {
        return null;
    }

    public ReadOnlyStringProperty address(){
        return address.getReadOnlyProperty();
    }

    public String getAddress2() {
        return address2.getValueSafe();
    }

    public ReadOnlyStringProperty address2(){
        return address2.getReadOnlyProperty();
    }

    public String getCity() {
        return city.getValue();
    }

    public ReadOnlyStringProperty city(){
        return city.getReadOnlyProperty();
    }

    public String getCountry() {
        return country.getValue();
    }

    public ReadOnlyStringProperty country(){
        return country.getReadOnlyProperty();
    }

    public String getPhone() {
        return phone.getValue();
    }

    public ReadOnlyStringProperty phone(){
        return phone.getReadOnlyProperty();
    }

    public String getPostalCode() {
        return postalCode.getValueSafe();
    }

    @Override
    public ZonedDateTime getLastUpdate() {
        ZonedDateTime zdt = ZonedDateTime.ofInstant(lastUpdate.getValue().toInstant(), ZoneId.of("UTC"));
        return zdt.withZoneSameInstant(ZoneId.systemDefault());
    }

    public ReadOnlyStringProperty postalCode(){
        return postalCode.getReadOnlyProperty();
    }


    public ReadOnlyBooleanProperty active() {
        return active.getReadOnlyProperty();
    }

    public ReadOnlyStringProperty lastUpdateByProperty() {
        return lastUpdateBy;
    }

    public ReadOnlyProperty<ZonedDateTime> lastUpdateProperty() {
        return lastUpdate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CustomerView{");
        sb.append("customerName=").append(customerName);
        sb.append(", address=").append(address);
        sb.append(", address2=").append(address2);
        sb.append(", city=").append(city);
        sb.append(", postalCode=").append(postalCode);
        sb.append(", country=").append(country);
        sb.append(", phone=").append(phone);
        sb.append(", active=").append(active);
        sb.append(", createDate=").append(createDate);
        sb.append(", createdBy='").append(createdBy).append('\'');
        sb.append(", lastUpdateBy=").append(lastUpdateBy);
        sb.append(", lastUpdate=").append(getLastUpdate());
        sb.append('}');
        return sb.toString();
    }
}
