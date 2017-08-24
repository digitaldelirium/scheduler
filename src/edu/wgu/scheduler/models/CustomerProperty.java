package edu.wgu.scheduler.models;

import com.sun.istack.internal.NotNull;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 00:48.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class CustomerProperty extends SimpleObjectProperty<CustomerProperty.Customer> implements ICustomer {
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

    /**
     * Created by Ian Cornett - icornet@wgu.edu on 6/25/17.
     * Part of Project: Java-8-SE-Programmer-II
     * <p>
     * Student ID: 000292065
     */
    public static class Customer implements ICustomer {
        private final LocalDate createDate;
        private int customerId;
        @NotNull
        private ObjectProperty<Byte> active;
        private SimpleIntegerProperty addressId;
        private final String createdBy;
        private StringProperty customerName;
        private StringProperty lastUpdateBy;
        private ObjectProperty<ZonedDateTime> lastUpdate;

        public Customer(int addressId, String customerName) {
            this(addressId, customerName, null);
        }

        public Customer(int addressId, String customerName, String createdBy) {
            this.addressId.setValue(addressId);
            this.customerName.setValue(customerName);
            this.createDate = LocalDate.now(ZoneId.of("UTC"));
            this.active.setValue(Byte.valueOf("1"));
            this.lastUpdateBy.setValue(createdBy);
            this.lastUpdate.setValue(ZonedDateTime.now(ZoneId.of("UTC")));
            this.createdBy = createdBy;
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
            Byte val = active.get();
            switch (val.intValue()){
                case 0:
                    return false;
                default:
                    return true;
            }
        }


        @Override
        public void setActive(boolean active) {
            if(active){
                this.active.setValue(Byte.valueOf("1"));
            } else {
                this.active.setValue(Byte.valueOf("0"));
            }
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
            sb.append(", customerId=").append(customerId);
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
}
