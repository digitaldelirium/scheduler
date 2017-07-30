package edu.wgu.scheduler.models;

import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/29/2017 at 20:45.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class AddressProperty extends SimpleObjectProperty<Address> {

    public AddressProperty(int cityId, String address) {
        super(new Address(cityId, address));
        init();
    }

    public AddressProperty(int cityId, String address, String address2) {
        super(new Address(cityId, address, address2));
        init();
    }

    public AddressProperty(int cityId, String address, String address2, String postalCode) {
        super(new Address(cityId, address, address2, postalCode));
        init();
    }

    public AddressProperty(int cityId, String address, String address2, String postalCode, String phone) {
        super(new Address(cityId, address, address2, postalCode, phone));
        init();
    }

    public AddressProperty(int cityId, String address, String address2, String postalCode, String phone, String createdBy) {
        super(new Address(cityId, address, address2, postalCode, phone, createdBy));
        init();
    }

    public AddressProperty(Address address) {
        super(address);
        init();
    }

    public String getAddress() {
        return getValue().getAddress();
    }

    public void setAddress(String address) {
        getValue().setAddress(address);
    }

    public String getAddress2() {
        return getValue().getAddress2();
    }

    public void setAddress2(String address2) {
        getValue().setAddress2(address2);
    }

    public String getPostalCode() {
        return getValue().getPostalCode();
    }

    public void setPostalCode(String postalCode) {
        getValue().setPostalCode(postalCode);
    }

    public String getPhone() {
        return getValue().getPhone();
    }

    public void setPhone(String phone) {
        getValue().setPhone(phone);
    }

    public String getLastUpdateBy() {
        return getValue().getLastUpdateBy();
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        getValue().setLastUpdateBy(lastUpdateBy);
    }

    public LocalDate getCreatedDate() {
        return getValue().getCreatedDate();
    }

    public String getCreatedBy() {
        return getValue().getCreatedBy();
    }

    private void init() {
        if (get() == null) {
            return;
        }

        get().addressProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().address2Property().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().phoneProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().postalCodeProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().lastUpdateByProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
    }

    @Override
    public void set(Address address){
        super.set(address);
        init();
    }

    @Override
    public void setValue(Address v) {
        super.setValue(v);
        init();
    }
}
