package project.lab6.controllers.utils;

import javafx.scene.control.Button;

import java.time.LocalDate;

public class EventButton {
    private String name;
    private String location;

    @Override
    public String toString() {
        return name + ", taking place in " + location + ", starts on " + date + '\n';
    }

    private LocalDate date;
    private String description;
    private Button button;

    public EventButton(String name, String location,
                       LocalDate date, String description,
                       Button button) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.description = description;
        this.button = button;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
