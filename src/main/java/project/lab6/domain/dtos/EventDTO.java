package project.lab6.domain.dtos;

import project.lab6.utils.Lazy;

import java.time.LocalDate;
import java.util.List;

public class EventDTO {
    private final Long idEvent;
    private String name;
    private LocalDate date;
    private String location;
    private String description;

    private final Lazy<List<UserEventInfoDTO>> users;

    public Long getIdEvent() {
        return idEvent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public Lazy<List<UserEventInfoDTO>> getUsers() {
        return users;
    }

    public EventDTO(Long idEvent, String name, LocalDate date, String location, String description, Lazy<List<UserEventInfoDTO>> users) {
        this.idEvent = idEvent;
        this.name = name;
        this.date = date;
        this.location = location;
        this.description = description;
        this.users = users;
    }
}
