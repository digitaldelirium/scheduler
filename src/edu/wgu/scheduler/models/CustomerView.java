package edu.wgu.scheduler.models;

import javafx.beans.property.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static edu.wgu.scheduler.MainApp.dataSource;

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
    private final ZonedDateTime createDate;
    private final String createdBy;
    private ReadOnlyStringWrapper lastUpdateBy;
    private ReadOnlyObjectWrapper<ZonedDateTime> lastUpdate;


    public CustomerView(String customerName, String address, String address2, String city, String postalCode, String country, String phone, boolean active, Date createDate, String createdBy, String lastUpdateBy, ZonedDateTime lastUpdate ){
        this.customerName.setValue(customerName);
        this.address.setValue(address);
        this.address2.setValue(address2);
        this.city.setValue(city);
        this.postalCode.setValue(postalCode);
        this.country.setValue(country);
        this.phone.setValue(phone);
        this.active.setValue(active);
        this.createDate = ZonedDateTime.ofInstant(createDate.toInstant(), ZoneId.systemDefault());
        this.createdBy = createdBy;
        this.lastUpdateBy.setValue(lastUpdateBy);
        this.lastUpdate.setValue(lastUpdate);
    }

    public boolean isActive() {
        return active.get();
    }

    ReadOnlyStringProperty customerName(){
        return customerName.getReadOnlyProperty();
    }

    public int getAddressId() {
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT addressId FROM address\n" +
                    "INNER JOIN city on address.cityId = city.cityId\n" +
                    "INNER JOIN country on city.countryId = country.countryId\n" +
                    "WHERE address = ?\n" +
                    "AND address2 = ?\n" +
                    "AND city = ?\n" +
                    "AND country = ?;");
            statement.setString(1, this.address.toString());
            statement.setString(2, this.address2.toString());
            statement.setString(3, this.city.toString());
            statement.setString(4, this.country.toString());

            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int getCustomerId() {
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT customerId FROM customer\n" +
                            "INNER JOIN address ON customer.addressId = address.addressId\n" +
                            "INNER JOIN city ON address.cityId = city.cityId\n" +
                            "INNER JOIN country ON city.countryId = country.countryId\n" +
                            "WHERE customerName = ?\n" +
                            "      AND address = ?\n" +
                            "      AND address2 = ?\n" +
                            "      AND city = ?\n" +
                            "      AND postalCode = ?\n" +
                            "      AND country = ?");
            statement.setString(1, this.getCustomerName());
            statement.setString(2, this.getAddress());
            statement.setString(3, this.getAddress2());
            statement.setString(4, this.getCity());
            statement.setString(5, this.getPostalCode());
            statement.setString(6, this.getCountry());

            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public LocalDate getCreateDate() {
        return this.createDate.toLocalDate();
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public String getCustomerName() {
        return customerName().getName();
    }

    public String getLastUpdateBy() {
        return this.lastUpdateBy.getValueSafe();
    }

    public String getAddress() {
        return this.address.getValue();
    }

    ReadOnlyStringProperty address(){
        return address.getReadOnlyProperty();
    }

    public String getAddress2() {
        return address2.getValueSafe();
    }

    ReadOnlyStringProperty address2(){
        return address2.getReadOnlyProperty();
    }

    public String getCity() {
        return city.getValue();
    }

    ReadOnlyStringProperty city(){
        return city.getReadOnlyProperty();
    }

    public String getCountry() {
        return country.getValue();
    }

    ReadOnlyStringProperty country(){
        return country.getReadOnlyProperty();
    }

    public String getPhone() {
        return phone.getValue();
    }

    ReadOnlyStringProperty phone(){
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
        final StringBuilder sb = new StringBuilder("CustomerView{\n");
        sb.append("\tcustomerName=").append(customerName);
        sb.append("\n\t address=").append(address);
        sb.append("\n\t address2=").append(address2);
        sb.append("\n\t city=").append(city);
        sb.append("\n\t postalCode=").append(postalCode);
        sb.append("\n\t country=").append(country);
        sb.append("\n\t phone=").append(phone);
        sb.append("\n\t active=").append(active);
        sb.append("\n\t createDate=").append(createDate);
        sb.append("\n\t createdBy='").append(createdBy).append('\'');
        sb.append("\n\t lastUpdateBy=").append(lastUpdateBy);
        sb.append("\n\t lastUpdate=").append(getLastUpdate());
        sb.append("\n}");
        return sb.toString();
    }
}