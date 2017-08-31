package edu.wgu.scheduler.models;

import javafx.beans.property.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static edu.wgu.scheduler.MainApp.dataSource;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 21:04.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class CustomerViewProperty extends SimpleObjectProperty<CustomerViewProperty.CustomerView> implements ICustomerView {

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

    public ReadOnlyStringProperty postalCode(){
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


    /**
     * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 20:52.
     * Part of Project: scheduler
     * <p>
     * Student ID: 000292065
     */
    public static class CustomerView implements ICustomerView {

        private ReadOnlyStringProperty customerName;
        private ReadOnlyStringProperty address;
        private ReadOnlyStringProperty address2;
        private ReadOnlyStringProperty city;
        private ReadOnlyStringProperty postalCode;
        private ReadOnlyStringProperty country;
        private ReadOnlyStringProperty phone;
        private ReadOnlyBooleanProperty active;
        private final ZonedDateTime createDate;
        private final String createdBy;
        private ReadOnlyStringProperty lastUpdateBy;
        private ReadOnlyObjectProperty<ZonedDateTime> lastUpdate;


        public CustomerView(String customerName, String address, String address2, String city, String postalCode, String country, String phone, boolean active, Timestamp createDate, String createdBy, String lastUpdateBy, Timestamp lastUpdate){
            this.customerName = new SimpleStringProperty(customerName);
            this.address = new SimpleStringProperty(address);
            this.address2 = new SimpleStringProperty(address2);
            this.city = new SimpleStringProperty(city);
            this.postalCode = new SimpleStringProperty(postalCode);
            this.country = new SimpleStringProperty(country);
            this.phone = new SimpleStringProperty(phone);
            this.active = new SimpleBooleanProperty(active);
            this.createDate = createDate.toInstant().atZone(ZoneId.systemDefault());
            this.createdBy = createdBy;
            this.lastUpdateBy = new SimpleStringProperty(lastUpdateBy);
            this.lastUpdate = new SimpleObjectProperty<>(lastUpdate.toInstant().atZone(ZoneId.systemDefault()));
        }

        public boolean isActive() {
            return active.get();
        }

        ReadOnlyStringProperty customerName(){
            return customerName;
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
            return address;
        }

        public String getAddress2() {
            return address2.getValueSafe();
        }

        ReadOnlyStringProperty address2(){
            return address2;
        }

        public String getCity() {
            return city.getValue();
        }

        ReadOnlyStringProperty city(){
            return city;
        }

        public String getCountry() {
            return country.getValue();
        }

        ReadOnlyStringProperty country(){
            return country;
        }

        public String getPhone() {
            return phone.getValue();
        }

        ReadOnlyStringProperty phone(){
            return phone;
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
            return postalCode;
        }


        public ReadOnlyBooleanProperty active() {
            return active;
        }

        public ReadOnlyStringProperty lastUpdateByProperty() {
            return lastUpdateBy;
        }

        public ReadOnlyObjectProperty<ZonedDateTime> lastUpdateProperty() {
            return lastUpdate;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("CustomerView{\n");
            sb.append("\tcustomerName=").append(customerName.getValueSafe());
            sb.append("\n\t address=").append(address.getValueSafe());
            sb.append("\n\t address2=").append(address2.getValueSafe());
            sb.append("\n\t city=").append(city.getValueSafe());
            sb.append("\n\t postalCode=").append(postalCode.getValueSafe());
            sb.append("\n\t country=").append(country.getValueSafe());
            sb.append("\n\t phone=").append(phone.getValueSafe());
            sb.append("\n\t active=").append(active.getValue());
            sb.append("\n\t createDate=").append(createDate);
            sb.append("\n\t createdBy='").append(createdBy).append('\'');
            sb.append("\n\t lastUpdateBy=").append(lastUpdateBy.getValueSafe());
            sb.append("\n\t lastUpdate=").append(getLastUpdate());
            sb.append("\n}");
            return sb.toString();
        }
    }
}
