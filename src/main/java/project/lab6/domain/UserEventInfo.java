package project.lab6.domain;

public class UserEventInfo extends Entity<TupleWithIdEventUser> {
    private boolean notification;


    public UserEventInfo(Long idEvent, Long idUser, boolean notification) {
        setId(new TupleWithIdEventUser(idEvent, idUser));
        this.notification = notification;
    }

    /**
     * @return the idEvent of the UserEventInfo
     */
    public Long getIdEvent() {
        return getId().getIdEvent();
    }

    /**
     * @return the idUser of the UserEventInfo
     */
    public Long getIdUser() {
        return getId().getIdUser();
    }

    /**
     * @return the boolean Notification of the UserEventInfo
     */
    public boolean isNotification() {
        return notification;
    }


}
