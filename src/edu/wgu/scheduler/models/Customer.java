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
public class Customer implements ICustomer {
    private final LocalDate createDate;
    private int customerId;
    @NotNull
    private SimpleBooleanProperty active = new SimpleBooleanProperty();
    private SimpleIntegerProperty addressId = new SimpleIntegerProperty();
    private final String createdBy;
    private StringProperty customerName = new SimpleStringProperty();
    private StringProperty lastUpdateBy = new SimpleStringProperty();
    private ObjectProperty<ZonedDateTime> lastUpdate = new SimpleObjectProperty<>();

    public Customer(int addressId, String customerName) {
        this(addressId, customerName, null);
    }

    public Customer(int addressId, String customerName, String createdBy) {
        this.addressId.setValue(addressId);
        this.customerName.setValue(customerName);
        this.createDate = LocalDate.now(ZoneId.of("UTC"));
        this.active.setValue(Boolean.TRUE);
        this.lastUpdateBy.setValue(createdBy);
        this.lastUpdate.setValue(ZonedDateTime.now(ZoneId.of("UTC")));
        this.createdBy = createdBy;
    }

    public Customer(LocalDate createDate, int customerId, Byte active, int addressId, String createdBy, String customerName, String lastUpdateBy, Timestamp lastUpdate) {
        this.createDate = createDate;
        this.customerId = customerId;
        switch (active.intValue()) {
            case 0:
                this.active.setValue(Boolean.FALSE);
                break;
            default:
                this.active.setValue(Boolean.TRUE);
                break;
        }
        this.addressId = new SimpleIntegerProperty(addressId);
        this.createdBy = createdBy;
        this.customerName.setValue(customerName);
        this.lastUpdateBy.setValue(lastUpdateBy);
        this.lastUpdate.setValue(ZonedDateTime.ofInstant(lastUpdate.toInstant(), ZoneId.systemDefault()));
    }

    @Override
    public int getCustomerId() {
        return customerId;
    }


    @Override
    public boolean isActive() {
        return active.getValue();
    }


    @Override
    public void setActive(boolean active) {
        this.active.setValue(active);
        return;
    }


    @Override
    public int getAddressId() {
        return addressId.get();
    }


    @Override
    public LocalDate getCreateDate() {
        return createDate;
    }


    @Override
    public String getCreatedBy() {
        return createdBy;
    }


    @Override
    public String getCustomerName() {
        return customerName.getValue();
    }


    @Override
    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }


    @Override
    public String getLastUpdateBy() {
        return lastUpdateBy.getValueSafe();
    }


    @Override
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy.setValue(lastUpdateBy);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Customer{");
        sb.append("createDate=").append(createDate);
        sb.append(", customerIdProperty=").append(customerId);
        sb.append(", active=").append(active);
        sb.append(", addressId=").append(addressId);
        sb.append(", createdBy='").append(createdBy).append('\'');
        sb.append(", customerName=").append(customerName);
        sb.append(", lastUpdateBy=").append(lastUpdateBy);
        sb.append(", lastUpdate=").append(lastUpdate);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return getCustomerId() == customer.getCustomerId();
    }

    @Override
    public int hashCode() {
        return getCustomerId();
    }
}
