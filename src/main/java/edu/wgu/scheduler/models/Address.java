package edu.wgu.scheduler.models;

import com.sun.istack.internal.NotNull;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 6/25/17.
 * Part of Project: Java-8-SE-Programmer-II
 * <p>
 * Student ID: 000292065
 */
public class Address {
    @NotNull
    private final LocalDate createdDate;
    private int addressId;
    @NotNull
    private StringProperty address;
    private StringProperty address2;
    @NotNull
    private SimpleIntegerProperty cityId;
    private StringProperty createdBy;
    private ObjectProperty<Timestamp> lastUpdate;
    private StringProperty lastUpdateBy;
    private StringProperty phone;
    private StringProperty postalCode;

    public Address(int cityId, String address) {
        this(cityId, address, null);
    }

    public Address(int cityId, String address, String address2) {
        this(cityId, address, address2, null);
    }

    public Address(int cityId, String address, String address2, String postalCode) {
        this(cityId, address, address2, postalCode, null);
    }

    public Address(int cityId, String address, String address2, String postalCode, String phone) {
        this(cityId, address, address2, postalCode, phone, null);
    }

    public Address(int cityId, String address, String address2, String postalCode, String phone, String createdBy) {
        this.cityId.setValue(cityId);
        this.address.setValue(address);
        this.address2.setValue(address2);
        this.postalCode.setValue(postalCode);
        this.phone.setValue(phone);
        this.createdBy.setValue(createdBy);
        this.lastUpdateBy.setValue(createdBy);
        this.createdDate = LocalDate.now(ZoneId.of("UTC"));
    }

    
    public String getPostalCode() {
        return postalCode.getValueSafe();
    }

    public StringProperty postalCodeProperty() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.setValue(postalCode);
    }

    
    public String getPhone() {
        return phone.getValueSafe();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.setValue(phone);
    }

    
    public String getLastUpdateBy() {
        return lastUpdateBy.getValueSafe();
    }

    public StringProperty lastUpdateByProperty() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy.setValue(lastUpdateBy);
    }

    
    public LocalDate getCreatedDate() {
        return createdDate;
    }
    
    public String getCreatedBy() {
        return createdBy.getValueSafe();
    }

    
    public String getAddress() {
        return address.getValue();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.setValue(address);
    }

    
    public String getAddress2() {
        return address2.getValueSafe();
    }

    public StringProperty address2Property() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2.setValue(address2);
    }

    
    public int getAddressId() {
        return addressId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        return null;
    }

    @Override
    public int hashCode() {

        return 0;
    }

    @Override
    public boolean equals(Object object) {

        return false;
    }
}
