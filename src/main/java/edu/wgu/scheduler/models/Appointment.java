package edu.wgu.scheduler.models;

import com.sun.istack.internal.NotNull;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

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
public class Appointment implements IAppointment {
    @NotNull
    private final LocalDate createDate;
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
    private ObjectProperty<Timestamp> lateUpdate;
    private StringProperty lastUpdateBy;
    @NotNull
    private StringProperty location;
    @NotNull
    private ObjectProperty<ZonedDateTime> start;
    @NotNull
    private StringProperty title;
    private StringProperty url;

    /**
     * Use this method for getting list of existing appointments
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
    public Appointment(LocalDate createDate, int appointmentId, String contact, String createdBy, int customerId, String description, String end, Timestamp lastUpdate, String lastUpdateBy, String location, String start, String title, String url) {
        this.createDate = createDate;
        this.appointmentId = appointmentId;
        this.contact.set(contact);
        this.createdBy = createdBy;
        this.customerId.set(customerId);
        this.description.set(description);
        this.end.setValue(ZonedDateTime.from(ZonedDateTime.parse(end)));
        this.lateUpdate.set(lastUpdate);
        this.lastUpdateBy.set(lastUpdateBy);
        this.location.set(location);
        this.start.set(ZonedDateTime.from(ZonedDateTime.parse(start)));
        this.title.set(title);
        this.url.set(url);
    }

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
        this(createdBy, customerId, description, end, location, start, title, null);
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
     */
    public Appointment(String createdBy, int customerId, String description, ZonedDateTime end, String location, ZonedDateTime start, String title, String url) {
        this.createdBy = createdBy;
        this.createDate = LocalDate.now(ZoneId.of("UTC"));
        this.customerId.setValue(customerId);
        this.description.setValue(description);
        this.end.setValue(ZonedDateTime.ofInstant(end.toInstant(), ZoneId.of("UTC")));
        this.lastUpdateBy.setValue(createdBy);
        this.location.setValue(location);
        this.start.setValue(ZonedDateTime.ofInstant(start.toInstant(), ZoneId.of("UTC")));
        this.title.setValue(title);
        this.url.setValue(url);
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

    public StringProperty contact() {
        return contact;
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
    public int getCustomerId() {
        return customerId.getValue();
    }

    @Override
    public void setCustomerId(int customerId) {
        this.customerId.setValue(customerId);
    }

    public IntegerProperty customerId() {
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

    public StringProperty description() {
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

    public ObjectProperty<ZonedDateTime> end() {
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

    public StringProperty lastUpdatedBy() {
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

    public StringProperty location() {
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

    public ObjectProperty<ZonedDateTime> start() {
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

    public StringProperty title() {
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

    public StringProperty url() {
        return url;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        return null;
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
