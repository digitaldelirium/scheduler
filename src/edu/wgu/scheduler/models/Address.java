package edu.wgu.scheduler.models;

import com.sun.istack.internal.NotNull;
import javafx.beans.property.*;

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
public class Address implements IAddress {
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
        this.cityId = new SimpleIntegerProperty(cityId);
        this.address = new SimpleStringProperty(address);
        this.address2 = new SimpleStringProperty(address2);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.phone = new SimpleStringProperty(phone);
        this.createdBy = new SimpleStringProperty(createdBy);
        this.lastUpdateBy = new SimpleStringProperty(createdBy);
        this.createdDate = LocalDate.now(ZoneId.of("UTC"));
        this.lastUpdate = new SimpleObjectProperty<>(new Timestamp(ZonedDateTime.now(ZoneId.systemDefault()).toEpochSecond()));
    }

    public Address(int cityId, String address, String address2, String postalCode, String phone, String createdBy, Timestamp lastUpdate, LocalDate createdDate) {
        this.cityId = new SimpleIntegerProperty(cityId);
        this.address = new SimpleStringProperty(address);
        this.address2 = new SimpleStringProperty(address2);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.phone = new SimpleStringProperty(phone);
        this.createdBy = new SimpleStringProperty(createdBy);
        this.lastUpdateBy = new SimpleStringProperty(createdBy);
        this.createdDate = createdDate;
        this.lastUpdate = new SimpleObjectProperty<>(lastUpdate);
    }


    @Override
    public String getPostalCode() {
        return postalCode.getValueSafe();
    }

    public StringProperty postalCodeProperty() {
        return postalCode;
    }

    @Override
    public void setPostalCode(String postalCode) {
        this.postalCode = new SimpleStringProperty(postalCode);
    }


    @Override
    public String getPhone() {
        return phone.getValueSafe();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = new SimpleStringProperty(phone);
    }


    @Override
    public String getLastUpdateBy() {
        return lastUpdateBy.getValueSafe();
    }

    public StringProperty lastUpdateByProperty() {
        return lastUpdateBy;
    }

    @Override
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = new SimpleStringProperty(lastUpdateBy);
    }


    @Override
    public LocalDate getCreatedDate() {
        return createdDate;
    }

    @Override
    public String getCreatedBy() {
        return createdBy.getValueSafe();
    }


    @Override
    public String getAddress() {
        return address.getValue();
    }

    public StringProperty addressProperty() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = new SimpleStringProperty(address);
    }


    @Override
    public String getAddress2() {
        return address2.getValueSafe();
    }

    public StringProperty address2Property() {
        return address2;
    }

    @Override
    public void setAddress2(String address2) {
        this.address2 = new SimpleStringProperty(address2);
    }


    @Override
    public int getAddressId() {
        return addressId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (getAddressId() != address.getAddressId()) return false;
        return cityId.equals(address.cityId);
    }

    @Override
    public int hashCode() {
        return getAddressId();
    }
}
