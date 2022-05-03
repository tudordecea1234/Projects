package project.lab6.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Event1 extends Entity<Long> {
    private String name;
    private LocalDate date;
    private String location;
    private String description;

    public Event1(Long id, String name, LocalDate date, String location, String description) {
        this(name, date, location, description);
        setId(id);
    }

    public Event1(String name, LocalDate date, String location, String description) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event1 event = (Event1) o;
        return name.equals(event.name) && date.equals(event.date) && location.equals(event.location) && description.equals(event.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, location, description);
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
