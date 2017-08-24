package edu.wgu.scheduler.models;

import com.sun.istack.internal.NotNull;
import javafx.beans.property.StringProperty;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 6/25/17.
 * Part of Project: Java-8-SE-Programmer-II
 * <p>
 * Student ID: 000292065
 */
class IncrementTypes {
    private int incrementTypeId;
    @NotNull
    private StringProperty incrementTypeDescription;

    public IncrementTypes(String incrementTypeDescription) {
        this.incrementTypeDescription.setValue(incrementTypeDescription);
    }

    public String getIncrementTypeDescription() {
        return incrementTypeDescription.getValue();
    }

    public void setIncrementTypeDescription(String incrementTypeDescription) {
        this.incrementTypeDescription.setValue(incrementTypeDescription);
    }
}
