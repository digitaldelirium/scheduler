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
    private final ZonedDateTime createdDate;
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
        this.createdDate = ZonedDateTime.now(ZoneId.of("UTC"));
        this.lastUpdate = new SimpleObjectProperty<>(new Timestamp(ZonedDateTime.now(ZoneId.systemDefault()).toEpochSecond() * 1000));
    }

    public Address(int cityId, String address, String address2, String postalCode, String phone, String createdBy, Timestamp lastUpdate, Timestamp createdDate) {
        this.cityId = new SimpleIntegerProperty(cityId);
        this.address = new SimpleStringProperty(address);
        this.address2 = new SimpleStringProperty(address2);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.phone = new SimpleStringProperty(phone);
        this.createdBy = new SimpleStringProperty(createdBy);
        this.lastUpdateBy = new SimpleStringProperty(createdBy);
        this.createdDate = ZonedDateTime.ofInstant(createdDate.toInstant(), ZoneId.systemDefault());
        this.lastUpdate = new SimpleObjectProperty<>(lastUpdate);
    }

    @Override
    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    @Override
    public int getAddressId() {
        return addressId;
    }

    @Override
    public String getCreatedBy() {
        return createdBy.get();
    }

    public StringProperty createdByProperty() {
        return createdBy;
    }

    @Override
    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    @Override
    public String getAddress2() {
        return address2.get();
    }

    public StringProperty address2Property() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2.set(address2);
    }

    public int getCityId() {
        return cityId.get();
    }

    public SimpleIntegerProperty cityIdProperty() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId.set(cityId);
    }

    public Timestamp getLastUpdate() {
        return lastUpdate.get();
    }

    public ObjectProperty<Timestamp> lastUpdateProperty() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate.set(lastUpdate);
    }

    @Override
    public String getLastUpdateBy() {
        return lastUpdateBy.get();
    }

    public StringProperty lastUpdateByProperty() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy.set(lastUpdateBy);
    }

    @Override
    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    @Override
    public String getPostalCode() {
        return postalCode.get();
    }

    public StringProperty postalCodeProperty() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
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
