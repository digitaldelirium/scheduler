package edu.wgu.scheduler.models;

import com.sun.istack.internal.NotNull;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.*;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 00:48.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class CountryProperty extends SimpleObjectProperty<CountryProperty.Country> implements ICountry {

    private int countryId;
    private SimpleStringProperty country;
    private String createBy;
    private ZonedDateTime createDate;
    private ObjectProperty<ZonedDateTime> lastUpdate;
    private SimpleStringProperty lastUpdatedBy;

    /**
     * The constructor of {@code ObjectProperty}
     */
    public CountryProperty(int countryId, String country, String createBy, Date createDate, Timestamp lastUpdate, String lastUpdatedBy) {
        this.countryId = countryId;
        this.country.set(country);
        this.createBy = createBy;
        this.createDate = ZonedDateTime.ofInstant(createDate.toInstant(), ZoneId.systemDefault());
        this.lastUpdate.setValue(ZonedDateTime.ofInstant(lastUpdate.toInstant(), ZoneId.systemDefault()));
        this.lastUpdatedBy.set(lastUpdatedBy);
    }

    public int getCountryId() {
        return countryId;
    }

    public String getCountry() {
        return country.getValueSafe();
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public String getCreatedBy() {
        return createBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createDate;
    }

    public String getLastUpdateBy() {
        return null;
    }

    public void setLastUpdateBy(String lastUpdateBy) {

    }

    /**
     * Created by Ian Cornett - icornet@wgu.edu on 6/25/17.
     * Part of Project: Java-8-SE-Programmer-II
     * <p>
     * Student ID: 000292065
     */
    public static class Country implements ICountry {
        @NotNull
        private final ZonedDateTime createdDate;
        private int countryId;
        @NotNull
        private StringProperty country;
        private StringProperty createdBy;
        private ObjectProperty<ZonedDateTime> lastUpdate;
        private StringProperty lastUpdateBy;

        public Country(String country) {
            this(country, null);
        }

        public Country(String country, String createdBy) {
            this.country.setValue(country);
            this.createdBy.setValue(createdBy);
            this.lastUpdateBy.setValue(createdBy);
            this.createdDate = ZonedDateTime.now(ZoneId.systemDefault());
        }

        public Country(int countryId, String country, ZonedDateTime createdDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy) {
            this.createdDate = createdDate;
            this.countryId = countryId;
            this.country.set(country);
            this.createdBy.set(createdBy);
            this.lastUpdate.set(ZonedDateTime.ofInstant(lastUpdate.toInstant(), ZoneId.systemDefault()));
            this.lastUpdateBy.set(lastUpdateBy);
        }

        @Override
        public int getCountryId() {
            return countryId;
        }

        @Override
        public String getCountry() {
            return country.getValue();
        }

        @Override
        public void setCountry(String country) {
            this.country.setValue(country);
        }

        @Override
        public String getCreatedBy() {
            return createdBy.getValueSafe();
        }

        @Override
        public ZonedDateTime getCreatedDate() {
            return createdDate;
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
            return "Country{" +
                    "createdDate=" + createdDate +
                    ", countryId=" + countryId +
                    ", country=" + country +
                    ", createdBy=" + createdBy +
                    ", lastUpdate=" + lastUpdate +
                    ", lastUpdateBy=" + lastUpdateBy +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Country country1 = (Country) o;

            if (getCountryId() != country1.getCountryId()) return false;
            return getCountry().equals(country1.getCountry());
        }

        @Override
        public int hashCode() {
            return getCountryId();
        }
    }
}
