package edu.wgu.scheduler.models;

import javafx.beans.property.*;
import javafx.beans.property.ReadOnlyStringProperty;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/30/2017 at 00:43.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class AppointmentView implements IAppointmentView {
    private final Date createdDate;
    private final String createdBy;
    // Interface needs these components
    private ReadOnlyStringWrapper title;
    private ReadOnlyStringWrapper description;
    private ReadOnlyStringWrapper location;
    private ReadOnlyStringWrapper contact;
    private ReadOnlyStringWrapper url;
    private ReadOnlyStringWrapper customerName;
    private ReadOnlyObjectWrapper<Timestamp> start;
    private ReadOnlyObjectWrapper<Timestamp> end;
    private ReadOnlyProperty<Timestamp> lastUpdated;

    public AppointmentView(String title, String description, String location, String contact, String url, String customerName, Timestamp start, Timestamp end, Date createDate, String createdBy, Timestamp lastUpdate) {
        this.title.setValue(title);
        this.description.setValue(description);
        this.location.setValue(location);
        this.contact.setValue(contact);
        this.customerName.setValue(customerName);
        this.start.setValue(start);
        this.end.setValue(end);
        this.createdDate = createDate;
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
        return ZonedDateTime.ofInstant(start.getValue().toInstant(), ZoneId.of("UTC"));
    }

    ReadOnlyProperty<Timestamp> startProperty() {
        return start;
    }

    public ZonedDateTime getEnd() {
        return ZonedDateTime.ofInstant(end.getValue().toInstant(), ZoneId.of("UTC"));
    }

    ReadOnlyProperty<Timestamp> endProperty() {
        return end;
    }

    public LocalDate getCreateDate() {
        return createdDate.toLocalDate();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ZonedDateTime getLastUpdate() {
        return ZonedDateTime.ofInstant(lastUpdated.getValue().toInstant(), ZoneId.of("UTC"));
    }

    ReadOnlyProperty<Timestamp> lastUpdatedProperty() {
        return lastUpdated;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        ZonedDateTime startTime = ZonedDateTime.ofInstant(getStart().toInstant(), ZoneId.of("UTC"));
        ZonedDateTime endTime = ZonedDateTime.ofInstant(getEnd().toInstant(), ZoneId.of("UTC"));
        ZonedDateTime lastUpdatedTime = ZonedDateTime.ofInstant(getLastUpdate().toInstant(), ZoneId.of("UTC"));
        builder.append(String.format("Appointment Title:\t%s\n", getTitle()));
        builder.append(String.format("Description:\t%s\n", getDescription()));
        builder.append(String.format("Location:\t%s\n", getLocation()));
        builder.append(String.format("Start:\t%s\n", startTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()));
        builder.append(String.format("End:\t%s\n", endTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()));
        builder.append(String.format("URL:\t%s\n", getUrl()));
        builder.append(String.format("Contact:\t%s\n", getContact()));
        builder.append(String.format("Customer Name:\t%s\n", getCustomerName()));
        builder.append(String.format("Appointment Created:\t%s\n", getCreateDate().toString()));
        builder.append(String.format("Created By:\t%s\n", getCreatedBy()));
        builder.append(String.format("Last Updated:\t%s\n", lastUpdatedTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()));
        return builder.toString();
    }
}
