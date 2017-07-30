package edu.wgu.scheduler.models;

import com.sun.istack.internal.NotNull;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 6/25/17.
 * Part of Project: Java-8-SE-Programmer-II
 * <p>
 * Student ID: 000292065
 */
public class Appointment {
    @NotNull
    private final ZonedDateTime createDate;
    private int appointmentId;
    private StringProperty contact;
    @NotNull
    private StringProperty createdBy;
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
     * @param lateUpdate
     * @param lastUpdateBy
     * @param location
     * @param start
     * @param title
     * @param url
     */
    public Appointment(ZonedDateTime createDate, int appointmentId, String contact, String createdBy, int customerId, String description, String end, Timestamp lateUpdate, String lastUpdateBy, String location, String start, String title, String url) {
        this.createDate = createDate;
        this.appointmentId = appointmentId;
        this.contact.set(contact);
        this.createdBy.set(createdBy);
        this.customerId.set(customerId);
        this.description.set(description);
        this.end.setValue(ZonedDateTime.from(ZonedDateTime.parse(end)));
        this.lateUpdate.set(lateUpdate);
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
        this.createdBy.setValue(createdBy);
        this.createDate = ZonedDateTime.now(ZoneId.of("UTC"));
        this.customerId.setValue(customerId);
        this.description.setValue(description);
        this.end.setValue(ZonedDateTime.ofInstant(end.toInstant(), ZoneId.of("UTC")));
        this.lastUpdateBy.setValue(createdBy);
        this.location.setValue(location);
        this.start.setValue(ZonedDateTime.ofInstant(start.toInstant(), ZoneId.of("UTC")));
        this.title.setValue(title);
        this.url.setValue(url);
    }

    
    public int getAppointmentId() {
        return appointmentId;
    }

    
    public String getContact() {
        return contact.getValueSafe();
    }

    
    public void setContact(String contact) {
        this.contact.setValue(contact);
    }

    
    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    
    public String getCreatedBy() {
        return createdBy.getValueSafe();
    }

    
    public int getCustomerId() {
        return customerId.getValue();
    }

    
    public void setCustomerId(int customerId) {
        this.customerId.setValue(customerId);
    }

    
    public String getDescription() {
        return description.getValue();
    }

    
    public void setDescription(String description) {
        this.description.setValue(description);
    }

    
    public ZonedDateTime getEnd() {
        return end.getValue();
    }

    
    public void setEnd(ZonedDateTime end) {
        this.end.setValue(end);
    }

    
    public String getLastUpdateBy() {
        return lastUpdateBy.getValueSafe();
    }

    
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy.setValue(lastUpdateBy);
    }

    
    public String getLocation() {
        return location.getValue();
    }

    
    public void setLocation(String location) {
        this.location.setValue(location);
    }

    
    public ZonedDateTime getStart() {
        return start.getValue();
    }

    
    public void setStart(ZonedDateTime start) {
        this.start.setValue(start);
    }

    
    public String getTitle() {
        return title.getValue();
    }

    
    public void setTitle(String title) {
        this.title.setValue(title);
    }

    
    public String getUrl() {
        return url.getValueSafe();
    }

    
    public void setUrl(String url) {
        this.url.setValue(url);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        return null;
    }

    @Override
    public int hashCode() {

        return 0;
    }

    @Override
    public boolean equals(Object object) {

        return false;
    }
}
