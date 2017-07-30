package edu.wgu.scheduler.models;

import com.sun.istack.internal.NotNull;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 6/25/17.
 * Part of Project: Java-8-SE-Programmer-II
 * <p>
 * Student ID: 000292065
 */
public class Customer {
    private final LocalDate createDate;
    private int customerId;
    @NotNull
    private ObjectProperty<Byte> active;
    private SimpleIntegerProperty addressId;
    private StringProperty createdBy;
    private StringProperty customerName;
    private StringProperty lastUpdateBy;
    private ObjectProperty<Timestamp> lastUpdate;

    public Customer(int addressId, String customerName) {
        this(addressId, customerName, null);
    }

    public Customer(int addressId, String customerName, String createdBy) {
        this.addressId.setValue(addressId);
        this.customerName.setValue(customerName);
        this.createDate = LocalDate.now(ZoneId.of("UTC"));
        this.active.setValue(Byte.valueOf("1"));
        this.lastUpdateBy.setValue(createdBy);
        this.lastUpdate.setValue(Timestamp.valueOf(LocalDateTime.now()));
    }

    public Customer(LocalDate createDate, int customerId, Byte active, int addressId, String createdBy, String customerName, String lastUpdateBy, Timestamp lastUpdate) {
        this.createDate = createDate;
        this.customerId = customerId;
        switch (active.intValue()) {
            case 0:
                this.active.setValue(Byte.parseByte("0"));
                break;
            default:
                this.active.setValue(Byte.parseByte("1"));
                break;
        }
        this.addressId = new SimpleIntegerProperty(addressId);
        this.createdBy.setValue(createdBy);
        this.customerName.setValue(customerName);
        this.lastUpdateBy.setValue(lastUpdateBy);
        this.lastUpdate.setValue(lastUpdate);
    }

    public int getCustomerId() {
        return customerId;
    }

    
    public boolean isActive() {
        Byte val = active.get();
        switch (val.intValue()){
            case 0:
                return false;
            default:
                return true;
        }
    }

    
    public void setActive(boolean active) {
        if(active){
            this.active.setValue(Byte.valueOf("1"));
        }
        else {
            this.active.setValue(Byte.valueOf("0"));
        }
        return;
    }

    
    public int getAddressId() {
        return addressId.get();
    }

    
    public LocalDate getCreateDate() {
        return createDate;
    }

    
    public String getCreatedBy() {
        return createdBy.getValueSafe();
    }

    
    public String getCustomerName() {
        return customerName.getValue();
    }

    
    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    
    public String getLastUpdateBy() {
        return lastUpdateBy.getValueSafe();
    }

    
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy.setValue(lastUpdateBy);
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
