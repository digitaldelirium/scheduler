package edu.wgu.scheduler.models;

import javafx.beans.property.*;

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

    /***
     *
     * @param title
     * @param description
     * @param location
     * @param contact
     * @param url
     * @param customerName
     * @param start
     * @param end
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     */
    public AppointmentView(String title, String description, String location, String contact, String url, String customerName, Timestamp start, Timestamp end, Timestamp createDate, String createdBy, Timestamp lastUpdate) {
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.location = new SimpleStringProperty(location);
        this.contact = new SimpleStringProperty(contact);
        this.customerName = new SimpleStringProperty(customerName);
        this.start = new SimpleObjectProperty<>(ZonedDateTime.ofInstant(start.toInstant(), ZoneId.of("UTC")));
        this.end = new SimpleObjectProperty<>(ZonedDateTime.ofInstant(end.toInstant(), ZoneId.of("UTC")));
        this.lastUpdated = new SimpleObjectProperty<>(ZonedDateTime.ofInstant(lastUpdate.toInstant(), ZoneId.of("UTC")));
        this.url = new SimpleStringProperty(url);
        this.createdDate = ZonedDateTime.ofInstant(createDate.toInstant(), ZoneId.of("UTC"));
        this.createdBy = createdBy;
    }

    public String getTitle() {
        return title.getValue();
    }

    public ReadOnlyStringProperty titleProperty() {
        return title;
    }

    public String getDescription() {
        return description.getValue();
    }

    public ReadOnlyStringProperty descriptionProperty() {
        return description;
    }

    public String getLocation() {
        return location.getValue();
    }

    public ReadOnlyStringProperty locationProperty() {
        return location;
    }

    public String getContact() {
        return contact.getValue();
    }

    public ReadOnlyStringProperty contactProperty() {
        return contact;
    }

    public String getUrl() {
        return url.getValueSafe();
    }

    public ReadOnlyStringProperty urlProperty() {
        return url;
    }

    public String getCustomerName() {
        return customerName.getValue();
    }

    public ReadOnlyStringProperty customerNameProperty() {
        return customerName;
    }

    public ZonedDateTime getStart() {
        return ZonedDateTime.ofInstant(start.getValue().toInstant(), ZoneId.of("UTC"));
    }

    public ReadOnlyProperty<ZonedDateTime> startProperty() {
        return start;
    }

    public ZonedDateTime getEnd() {
        return ZonedDateTime.ofInstant(end.getValue().toInstant(), ZoneId.of("UTC"));
    }

    public ReadOnlyProperty<ZonedDateTime> endProperty() {
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

    public ReadOnlyProperty<ZonedDateTime> lastUpdatedProperty() {
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
