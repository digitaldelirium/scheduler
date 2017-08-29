package edu.wgu.scheduler.models;

import javafx.beans.property.SimpleObjectProperty;

import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 00:48.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class CustomerProperty extends SimpleObjectProperty<Customer> implements ICustomer {
    public CustomerProperty(LocalDate createDate, int customerId, Byte active, int addressId, String createdBy, String customerName, String lastUpdateBy, Timestamp lastUpdate) {
        super(new Customer(createDate, customerId, active, addressId, createdBy, customerName, lastUpdateBy, lastUpdate));
    }

    public CustomerProperty(int addressId, String customerName, String createdBy) {
        super(new Customer(addressId, customerName, createdBy));
    }

    public CustomerProperty(int addressId, String customerName) {
        super(new Customer(addressId, customerName));
    }

    public CustomerProperty(Customer customer) {
        super(customer);
    }

    public int getCustomerId() {
        return getValue().getCustomerId();
    }

    public boolean isActive() {
        return getValue().isActive();
    }

    public void setActive(boolean active) {
        getValue().setActive(active);
    }

    public int getAddressId() {
        return getValue().getAddressId();
    }

    public LocalDate getCreateDate() {
        return getValue().getCreateDate();
    }

    public String getCreatedBy() {
        return getValue().getCreatedBy();
    }

    public String getCustomerName() {
        return getValue().getCustomerName();
    }

    public void setCustomerName(String customerName) {
        getValue().setCustomerName(customerName);
    }

    public String getLastUpdateBy() {
        return getValue().getLastUpdateBy();
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        getValue().setLastUpdateBy(lastUpdateBy);
    }

    @Override
    public int hashCode(){
        return getValue().hashCode();
    }

    @Override
    public boolean equals(Object o){
        return getValue().equals(o);
    }
}
