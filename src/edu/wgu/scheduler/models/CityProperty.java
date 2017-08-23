package edu.wgu.scheduler.models;

import com.sun.istack.internal.NotNull;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 00:46.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class CityProperty extends SimpleObjectProperty<CityProperty.City> implements ICity {

    public CityProperty(String city, int countryId) {
        super(new City(city, countryId));
        init();
    }

    public CityProperty(String city, int countryId, String createdBy) {
        super(new City(city, countryId, createdBy));
        init();
    }

    public CityProperty(int cityId, String city, int countryId, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, ZonedDateTime createDate) {
        super(new City(cityId, city, countryId, createdBy, lastUpdate, lastUpdatedBy, createDate));
        init();
    }

    public CityProperty(City city) {
        super(city);
        init();
    }

    public int getCityId() {
        return getValue().getCityId();
    }

    public String getCity() {
        return getValue().getCity();
    }

    public void setCity(String city) {
        getValue().setCity(city);
    }

    public StringProperty cityProperty() {
        return getValue().cityProperty();
    }

    public int getCountryId() {
        return getValue().getCountryId();
    }

    public SimpleIntegerProperty countryIdProperty() {
        return getValue().countryIdProperty();
    }

    public ZonedDateTime getCreateDate() {
        return getValue().getCreateDate();
    }

    public String getCreatedBy() {
        return getValue().getCreatedBy();
    }

    public String getLastUpdateBy() {
        return getValue().getLastUpdateBy();
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        getValue().setLastUpdateBy(lastUpdateBy);
    }

    public String getLastUpdatedBy() {
        return getValue().getLastUpdateBy();
    }

    public StringProperty lastUpdateByProperty() {
        return getValue().lastUpdateByProperty();
    }

    public ZonedDateTime getLastUpdate() {
        return getValue().getLastUpdate();
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        getValue().setLastUpdate(lastUpdate);
    }

    public ObjectProperty<ZonedDateTime> lastUpdateProperty() {
        return getValue().lastUpdateProperty();
    }

    private void init() {
        if (get() == null) {
            return;
        }

        get().lastUpdateProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().lastUpdateByProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().countryIdProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().cityProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
    }

    /**
     * {@inheritDoc}
     *
     * @param newValue
     */
    @Override
    public void set(City newValue) {
        super.set(newValue);
        init();
    }

    /**
     * {@inheritDoc}
     *
     * @param v
     */
    @Override
    public void setValue(City v) {
        super.setValue(v);
        init();
    }

    /**
     * Created by Ian Cornett - icornet@wgu.edu on 6/25/17.
     * Part of Project: Java-8-SE-Programmer-II
     * <p>
     * Student ID: 000292065
     */
    public static class City implements ICity {
        @NotNull
        private final ZonedDateTime createDate;
        private int cityId;
        @NotNull
        private StringProperty city;
        @NotNull
        private SimpleIntegerProperty countryId;
        private String createdBy;
        private ObjectProperty<ZonedDateTime> lastUpdate;
        private StringProperty lastUpdateBy;

        public City(String city, int countryId) {
            this(city, countryId, null);
        }

        public City(String city, int countryId, String createdBy) {
            this.city.setValue(city);
            this.countryId.setValue(countryId);
            this.createdBy = createdBy;
            this.lastUpdateBy.setValue(createdBy);
            this.createDate = ZonedDateTime.now(ZoneId.of("UTC"));
        }

        public City(int cityId, String city, int countryId, String createdBy, Timestamp lastUpdate, String lastUpdateBy, ZonedDateTime createDate) {
            this.cityId = cityId;
            this.city.setValue(city);
            this.countryId.setValue(countryId);
            this.createdBy = createdBy;
            this.lastUpdate.setValue(ZonedDateTime.ofInstant(lastUpdate.toInstant(), ZoneId.systemDefault()));
            this.lastUpdateBy.setValue(lastUpdateBy);
            this.createDate = createDate;
        }

        @Override
        public int getCityId() {
            return cityId;
        }

        @Override
        public String getCity() {
            return city.getValue();
        }

        @Override
        public void setCity(String city) {
            this.city.setValue(city);
        }

        public StringProperty cityProperty() {
            return city;
        }

        @Override
        public int getCountryId() {
            return countryId.getValue();
        }

        public SimpleIntegerProperty countryIdProperty() {
            return countryId;
        }

        @Override
        public ZonedDateTime getCreateDate() {
            return createDate;
        }

        @Override
        public String getCreatedBy() {
            return createdBy;
        }

        @Override
        public String getLastUpdateBy() {
            return lastUpdateBy.getValueSafe();
        }

        @Override
        public void setLastUpdateBy(String lastUpdateBy) {
            this.lastUpdateBy.setValue(lastUpdateBy);
        }

        public StringProperty lastUpdateByProperty() {
            return lastUpdateBy;
        }

        @Override
        public ZonedDateTime getLastUpdate() {
            return lastUpdate.get();
        }

        @Override
        public void setLastUpdate(Timestamp lastUpdate) {
            this.lastUpdate.set(ZonedDateTime.ofInstant(lastUpdate.toInstant(), ZoneId.systemDefault()));
        }

        public ObjectProperty<ZonedDateTime> lastUpdateProperty() {
            return lastUpdate;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("City{");
            sb.append("createDate=").append(createDate);
            sb.append(", cityId=").append(cityId);
            sb.append(", city=").append(city);
            sb.append(", countryId=").append(countryId);
            sb.append(", createdBy='").append(createdBy).append('\'');
            sb.append(", lastUpdate=").append(lastUpdate);
            sb.append(", lastUpdateBy=").append(lastUpdateBy);
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            City city = (City) o;

            return getCityId() == city.getCityId();
        }

        @Override
        public int hashCode() {
            return getCityId();
        }
    }
}
