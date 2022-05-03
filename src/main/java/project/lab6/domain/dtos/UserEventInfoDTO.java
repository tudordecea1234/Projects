package project.lab6.domain.dtos;

import project.lab6.domain.User;

public class UserEventInfoDTO {
    public final User user;
    public final Long idEvent;
    public boolean notification;

    public UserEventInfoDTO(User user, Long idEvent, boolean notification) {
        this.user = user;
        this.idEvent = idEvent;
        this.notification = notification;
    }
    public User getUser() {
        return user;
    }

}