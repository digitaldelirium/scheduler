package edu.wgu.scheduler.models;

import javafx.beans.property.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 00:45.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class AppointmentViewProperty extends SimpleObjectProperty<AppointmentViewProperty.AppointmentView> implements IAppointmentView {

    public AppointmentViewProperty(String title, String description, String location, String contact, String url,
                                   String customerName, Timestamp start, Timestamp end, Timestamp createDate, String createdBy, Timestamp lastUpdate) {
        super(new AppointmentView(title, description, location, contact, url, customerName, start, end, createDate, createdBy, lastUpdate));
        init();
    }

    public AppointmentViewProperty(AppointmentView app) {
        super(app);
        init();
    }

    public String getTitle() {
        return getValue().getTitle();
    }

    public ReadOnlyStringProperty titleProperty() {
        return getValue().titleProperty();
    }

    public String getDescription() {
        return getValue().getDescription();
    }

    public ReadOnlyStringProperty descriptionProperty() {
        return getValue().descriptionProperty();
    }

    public String getLocation() {
        return getValue().getLocation();
    }

    public ReadOnlyStringProperty locationProperty() {
        return getValue().locationProperty();
    }

    public String getContact() {
        return getValue().getContact();
    }

    public ReadOnlyStringProperty contactProperty() {
        return getValue().contactProperty();
    }

    public String getUrl() {
        return getValue().getUrl();
    }

    public ReadOnlyStringProperty urlProperty() {
        return getValue().urlProperty();
    }

    public String getCustomerName() {
        return getValue().getCustomerName();
    }

    public ReadOnlyStringProperty customerNameProperty() {
        return getValue().customerNameProperty();
    }

    public ZonedDateTime getStart() {
        ZonedDateTime zdt = ZonedDateTime.ofInstant(getValue().getStart().toInstant(), ZoneId.of("UTC"));
        return zdt.withZoneSameInstant(ZoneId.systemDefault());
    }

    public ReadOnlyProperty<ZonedDateTime> startProperty() {
        return getValue().startProperty();
    }

    public ZonedDateTime getEnd() {
        ZonedDateTime zdt = ZonedDateTime.ofInstant(getValue().getEnd().toInstant(), ZoneId.of("UTC"));
        return zdt.withZoneSameInstant(ZoneId.systemDefault());
    }

    public ReadOnlyProperty<ZonedDateTime> endProperty() {
        return getValue().endProperty();
    }

    public LocalDate getCreateDate() {
        return getValue().getCreateDate();
    }

    public String getCreatedBy() {
        return getValue().getCreatedBy();
    }

    public ZonedDateTime getLastUpdate() {
        ZonedDateTime zdt = ZonedDateTime.ofInstant(getValue().getLastUpdate().toInstant(), ZoneId.of("UTC"));
        return zdt.withZoneSameInstant(ZoneId.systemDefault());
    }

    public ReadOnlyProperty<ZonedDateTime> lastUpdatedProperty() {
        return getValue().lastUpdatedProperty();
    }

    private void init() {
        if (get() == null) {
            return;
        }

        get().titleProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().descriptionProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().locationProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().contactProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().urlProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().customerNameProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().startProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().endProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
        get().lastUpdatedProperty().addListener((observable, oldValue, newValue) -> fireValueChangedEvent());
    }

    @Override
    public void set(AppointmentView newValue) {
        super.set(newValue);
    }

    @Override
    public String toString() {
        return getValue().toString();
    }

    @Override
    public void setValue(AppointmentView v) {
        super.setValue(v);
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return getValue().equals(obj);
    }

    /**
     * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 00:43.
     * Part of Project: scheduler
     * <p>
     * Student ID: 000292065
     */
    public static class AppointmentView implements IAppointmentView {
        private final ZonedDateTime createdDate;
        private final String createdBy;
        // Interface needs these components
        private ReadOnlyStringProperty title;
        private ReadOnlyStringProperty description;
        private ReadOnlyStringProperty location;
        private ReadOnlyStringProperty contact;
        private ReadOnlyStringProperty url;
        private ReadOnlyStringProperty customerName;
        private ReadOnlyObjectProperty<ZonedDateTime> start;
        private ReadOnlyObjectProperty<ZonedDateTime> end;
        private ReadOnlyProperty<ZonedDateTime> lastUpdated;

        public AppointmentView(String title, String description, String location, String contact, String url, String customerName, Timestamp start, Timestamp end, Timestamp createDate, String createdBy, Timestamp lastUpdate) {
            this.title = new SimpleStringProperty(title);
            this.description = new SimpleStringProperty(description);
            this.location = new SimpleStringProperty(location);
            this.contact = new SimpleStringProperty(contact);
            this.customerName = new SimpleStringProperty(customerName);
            this.start = new SimpleObjectProperty<>(ZonedDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault()));
            this.end = new SimpleObjectProperty<>(ZonedDateTime.ofInstant(end.toInstant(), ZoneId.systemDefault()));
            this.lastUpdated = new SimpleObjectProperty<>(ZonedDateTime.ofInstant(lastUpdate.toInstant(), ZoneId.systemDefault()));
            this.url = new SimpleStringProperty(url);
            this.createdDate = ZonedDateTime.ofInstant(createDate.toInstant(), ZoneId.systemDefault());
            this.createdBy = createdBy;

        }

        public String getTitle() {
            return title.getValue();
        }

        ReadOnlyStringProperty titleProperty() {
            return title;
        }

        public String getDescription() {
            return description.getValue();
        }

        ReadOnlyStringProperty descriptionProperty() {
            return description;
        }

        public String getLocation() {
            return location.getValue();
        }

        ReadOnlyStringProperty locationProperty() {
            return location;
        }

        public String getContact() {
            return contact.getValue();
        }

        ReadOnlyStringProperty contactProperty() {
            return contact;
        }

        public String getUrl() {
            return url.getValueSafe();
        }

        ReadOnlyStringProperty urlProperty() {
            return url;
        }

        public String getCustomerName() {
            return customerName.getValue();
        }

        ReadOnlyStringProperty customerNameProperty() {
            return customerName;
        }

        public ZonedDateTime getStart() {
            return ZonedDateTime.ofInstant(start.getValue().toInstant(), ZoneId.systemDefault());
        }

        ReadOnlyProperty<ZonedDateTime> startProperty() {
            return start;
        }

        public ZonedDateTime getEnd() {
            return ZonedDateTime.ofInstant(end.getValue().toInstant(), ZoneId.systemDefault());
        }

        ReadOnlyProperty<ZonedDateTime> endProperty() {
            return end;
        }

        public LocalDate getCreateDate() {
            return createdDate.toLocalDate();
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public ZonedDateTime getLastUpdate() {
            return ZonedDateTime.ofInstant(lastUpdated.getValue().toInstant(), ZoneId.systemDefault());
        }

        ReadOnlyProperty<ZonedDateTime> lastUpdatedProperty() {
            return lastUpdated;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("AppointmentView{");
            sb.append("createdDate=").append(createdDate);
            sb.append(", createdBy='").append(createdBy).append('\'');
            sb.append(", titleProperty=").append(title);
            sb.append(", descriptionProperty=").append(description);
            sb.append(", locationProperty=").append(location);
            sb.append(", contactProperty=").append(contact);
            sb.append(", urlProperty=").append(url);
            sb.append(", customerName=").append(customerName);
            sb.append(", startProperty=").append(start);
            sb.append(", endProperty=").append(end);
            sb.append(", lastUpdated=").append(lastUpdated);
            sb.append('}');
            return sb.toString();
        }
    }
}
