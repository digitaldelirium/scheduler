package edu.wgu.scheduler.models;

import com.sun.istack.internal.NotNull;
import javafx.beans.property.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 6/25/17.
 * Part of Project: Java-8-SE-Programmer-II
 * <p>
 * Student ID: 000292065
 */
public class Appointment implements IAppointment {
    @NotNull
    private final ZonedDateTime createDate;
    private int appointmentId;
    private StringProperty contact;
    @NotNull
    private final String createdBy;
    @NotNull
    private SimpleIntegerProperty customerId;
    @NotNull
    private StringProperty description;
    @NotNull
    private ObjectProperty<ZonedDateTime> end;
    private ObjectProperty<Timestamp> lastUpdate;
    private StringProperty lastUpdateBy;
    @NotNull
    private StringProperty location;
    @NotNull
    private ObjectProperty<ZonedDateTime> start;
    @NotNull
    private StringProperty title;
    private StringProperty url;


    /**
     * @param createdBy
     * @param customerId
     * @param description
     * @param end
     * @param location
     * @param start
     * @param title
     */
    public Appointment(String createdBy, int customerId, String description, ZonedDateTime end, String location, ZonedDateTime start, String title) {
        this(createdBy, customerId, description, end, location, start, title, null, null);
    }

    /**
     * @param createdBy
     * @param customerId
     * @param description
     * @param end
     * @param location
     * @param start
     * @param title
     * @param url
     * @param contact
     */
    public Appointment(String createdBy, int customerId, String description, ZonedDateTime end, String location, ZonedDateTime start, String title, String url, String contact) {
        this.createdBy = createdBy;
        this.createDate = ZonedDateTime.now(ZoneId.of("UTC"));
        this.customerId = new SimpleIntegerProperty(customerId);
        this.description = new SimpleStringProperty(description);
        this.end = new SimpleObjectProperty<>(ZonedDateTime.ofInstant(end.toInstant(), ZoneId.of("UTC")));
        this.lastUpdateBy = new SimpleStringProperty(createdBy);
        this.location = new SimpleStringProperty(location);
        this.start = new SimpleObjectProperty<>(ZonedDateTime.ofInstant(start.toInstant(), ZoneId.of("UTC")));
        this.title = new SimpleStringProperty(title);
        this.url = new SimpleStringProperty(url);
        this.contact = new SimpleStringProperty(contact);
    }

    /***
     *
     * @param createDate
     * @param appointmentId
     * @param contact
     * @param createdBy
     * @param customerId
     * @param description
     * @param end
     * @param lastUpdate
     * @param lastUpdateBy
     * @param location
     * @param start
     * @param title
     * @param url
     */
    public Appointment(Timestamp createDate, int appointmentId, String contact, String createdBy, int customerId, String description, Timestamp end, Timestamp lastUpdate, String lastUpdateBy, String location, Timestamp start, String title, String url) {
        this.createdBy = createdBy;
        this.createDate = ZonedDateTime.ofInstant(createDate.toInstant(), ZoneId.of("UTC"));
        this.customerId = new SimpleIntegerProperty(customerId);
        this.description = new SimpleStringProperty(description);
        this.end = new SimpleObjectProperty<>(ZonedDateTime.ofInstant(end.toInstant(), ZoneId.of("UTC")));
        this.lastUpdateBy = new SimpleStringProperty(createdBy);
        this.location = new SimpleStringProperty(location);
        this.start = new SimpleObjectProperty<>(ZonedDateTime.ofInstant(start.toInstant(), ZoneId.of("UTC")));
        this.title = new SimpleStringProperty(title);
        this.url = new SimpleStringProperty(url);
        this.appointmentId = appointmentId;
        this.contact = new SimpleStringProperty(contact);
    }

    @Override
    public int getAppointmentId() {
        return appointmentId;
    }


    @Override
    public String getContact() {
        return contact.getValueSafe();
    }

    @Override
    public void setContact(String contact) {
        this.contact.setValue(contact);
    }

    public StringProperty contactProperty() {
        return contact;
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
    public int getCustomerId() {
        return customerId.getValue();
    }

    @Override
    public void setCustomerId(int customerId) {
        this.customerId.setValue(customerId);
    }

    public IntegerProperty customerIdProperty() {
        return customerId;
    }

    @Override
    public String getDescription() {
        return description.getValue();
    }

    @Override
    public void setDescription(String description) {
        this.description.setValue(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    @Override
    public ZonedDateTime getEnd() {
        return end.getValue();
    }

    @Override
    public void setEnd(ZonedDateTime end) {
        this.end.setValue(end);
    }

    public ObjectProperty<ZonedDateTime> endProperty() {
        return end;
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
    public ZonedDateTime getLastUpdate() {
        return ZonedDateTime.ofInstant(this.lastUpdate.getValue().toInstant(), ZoneId.systemDefault());
    }

    @Override
    public void setLastUpdate(ZonedDateTime lastUpdated) {
        this.lastUpdate.setValue(new Timestamp(lastUpdated.toInstant().getEpochSecond()));
    }

    public StringProperty lastUpdatedByProperty() {
        return lastUpdateBy;
    }

    @Override
    public String getLocation() {
        return location.getValue();
    }

    @Override
    public void setLocation(String location) {
        this.location.setValue(location);
    }

    public StringProperty locationProperty() {
        return location;
    }

    @Override
    public ZonedDateTime getStart() {
        return start.getValue();
    }

    @Override
    public void setStart(ZonedDateTime start) {
        this.start.setValue(start);
    }

    public ObjectProperty<ZonedDateTime> startProperty() {
        return start;
    }

    @Override
    public String getTitle() {
        return title.getValue();
    }

    @Override
    public void setTitle(String title) {
        this.title.setValue(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    @Override
    public String getUrl() {
        return url.getValueSafe();
    }

    @Override
    public void setUrl(String url) {
        this.url.setValue(url);
    }

    public StringProperty urlProperty() {
        return url;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        return null;
    }


    public ObjectProperty<Timestamp> lastUpdateProperty() {
        return lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Appointment that = (Appointment) o;

        return getAppointmentId() == that.getAppointmentId();
    }

    @Override
    public int hashCode() {
        return getAppointmentId();
    }


}
