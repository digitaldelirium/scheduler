package edu.wgu.scheduler.models;

import javafx.beans.property.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 21:04.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class CustomerViewProperty extends SimpleObjectProperty<CustomerView> implements ICustomerView {

    public CustomerViewProperty(CustomerView customerView){
        super(customerView);
    }

    @Override
    public boolean isActive() {
        return getValue().isActive();
    }

    public BooleanProperty active(){
        return (BooleanProperty) getValue().active();
    }

    @Override
    public int getAddressId() {
        return getValue().getAddressId();
    }

    @Override
    public LocalDate getCreateDate() {
        return getValue().getCreateDate();
    }

    @Override
    public String getCreatedBy() {
        return getValue().getCreatedBy();
    }

    @Override
    public String getCustomerName() {
        return getValue().getCustomerName();
    }

    public StringProperty customerName(){
        return (StringProperty) getValue().customerName();
    }

    @Override
    public String getLastUpdateBy() {
        return getValue().getLastUpdateBy();
    }

    public StringProperty lastUpdateBy(){
        return (StringProperty) getValue().lastUpdateByProperty();
    }

    @Override
    public String getAddress() {
        return getValue().getAddress();
    }

    public StringProperty address(){
        return (StringProperty) getValue().address();
    }

    public String getAddress2() {
        return getValue().getAddress2();
    }

    public StringProperty address2(){
        return (StringProperty) getValue().address2();
    }

    @Override
    public String getCity() {
        return getValue().getCity();
    }

    public StringProperty city(){
        return (StringProperty) getValue().city();
    }

    @Override
    public String getCountry() {
        return getValue().getCountry();
    }

    public StringProperty country(){
        return (StringProperty) getValue().country();
    }

    @Override
    public String getPhone() {
        return getValue().getPhone();
    }

    public StringProperty phone(){
        return (StringProperty) getValue().phone();
    }

    @Override
    public String getPostalCode() {
        return getValue().getPostalCode();
    }

    public StringProperty postalCode(){
        return (StringProperty) getValue().postalCode();
    }

    @Override
    public ZonedDateTime getLastUpdate() {
        return getValue().getLastUpdate();
    }

    @Override
    public int getCustomerId() {
        return getValue().getCustomerId();
    }


}
