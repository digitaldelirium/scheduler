package edu.wgu.scheduler.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/16/2017 at 15:09.
 * Part of Project: Java-8-SE-Programmer-II
 * <p>
 * Student ID: 000292065
 */
public class User {
    private final ZonedDateTime createdDate;
    private int userId;
    private BooleanProperty active = new SimpleBooleanProperty();
    private String createdBy;
    private SimpleStringProperty lastUpdatedBy = new SimpleStringProperty();
    private SimpleStringProperty password = new SimpleStringProperty();
    private String username;

    public User(String username){
        this(username, null);
    }

    public User(String username, String password) {
        this(username, password, null);
    }

    public User(String username, String password, String createdBy) {
        this.username = username;
        this.password.setValue(password);
        this.createdDate = ZonedDateTime.now(ZoneId.of("UTC"));
        this.createdBy = createdBy;
        this.lastUpdatedBy.setValue(createdBy);
        this.active.set(true);
    }

    public int getUserId() {
        return userId;
    }

    public boolean isActive() {
        return active.getValue();
    }

    public void setActive(boolean active) {
        this.active.set(active);
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy.getValueSafe();
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy.set(lastUpdatedBy);
    }

    public String getPassword() {
        return this.password.getValueSafe();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    @Override
    public String toString() {
        return new StringBuilder("User:\t").append(this.username).append("\tPassHash:\t").append(Base64.getEncoder().encode(this.password.getValueSafe().getBytes())).toString();
    }

    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return getUserId() == user.getUserId();
    }

    @Override
    public int hashCode() {
        return getUserId();
    }
}
