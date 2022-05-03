package project.lab6.service;

import project.lab6.domain.Event1;
import project.lab6.domain.TupleWithIdEventUser;
import project.lab6.domain.User;
import project.lab6.domain.UserEventInfo;
import project.lab6.domain.dtos.EventDTO;
import project.lab6.domain.dtos.UserEventInfoDTO;
import project.lab6.domain.validators.Validator;
import project.lab6.repository.repointerface.Repository;
import project.lab6.utils.Lazy;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ServiceEvents {
    private final Repository<Long, Event1> repoEvents;
    private final Repository<TupleWithIdEventUser, UserEventInfo> repoUserEventInfo;
    private final Repository<Long, User> repoUsers;
    private final Validator<UserEventInfo> validatorUserInfo;
    private final Validator<Event1> eventValidator;

    public ServiceEvents(Repository<Long, Event1> repoEvents, Repository<TupleWithIdEventUser,
            UserEventInfo> repoUserEventInfo, Repository<Long, User> repoUsers,
                         Validator<UserEventInfo> validatorUserInfo,
                         Validator<Event1> eventValidator) {
        this.repoEvents = repoEvents;
        this.repoUserEventInfo = repoUserEventInfo;
        this.repoUsers = repoUsers;
        this.validatorUserInfo = validatorUserInfo;
        this.eventValidator = eventValidator;
    }

    /**
     * Returns a list of UserEventInfoDTOs for a given idEvent
     *
     * @param idEvent id of the event
     */
    private List<UserEventInfoDTO> getUsersInfoForEvent(Long idEvent) {
        return repoUserEventInfo.findAll().stream()
                .filter(userInfo -> userInfo.getIdEvent().equals(idEvent))
                .map(this::getUserEventInfoDTO)
                .toList();
    }

    /**
     * Returns a UserEventInfoDTO object for a given UserEventInfo object
     *
     * @param userInfo the UserEventInfo object
     */
    private UserEventInfoDTO getUserEventInfoDTO(UserEventInfo userInfo) {
        User user = repoUsers.findOne(userInfo.getIdUser());
        return new UserEventInfoDTO(user, userInfo.getIdEvent(), userInfo.isNotification());
    }

    /**
     * Returns an EventDTO object for a given idEvent
     *
     * @param idEvent the id of the event
     */
    public EventDTO getEventDTO(Long idEvent) {
        Event1 event = repoEvents.findOne(idEvent);
        Lazy<List<UserEventInfoDTO>> userEventInfo = new Lazy<>(() -> getUsersInfoForEvent(idEvent));
        return new EventDTO(idEvent, event.getName(), event.getDate(), event.getLocation(), event.getDescription(), userEventInfo);
    }

    /**
     * Returns a list of EventsDTOs for a given idLoggedUser as participant
     *
     * @param idLoggedUser the id of the user
     */
    public List<EventDTO> getEventsDTOForUser(Long idLoggedUser) {
        return repoEvents.findAll().stream()
                .map(event -> getEventDTO(event.getId()))
                .filter(eventDTO -> eventDTO.getUsers().get().stream()
                        .anyMatch(user -> user.getUser().getId().equals(idLoggedUser)))
                .toList();
    }

    /**
     * Returns  of EventsDTOs for all users except the given user
     *
     * @param idLoggedUser the id of the user
     */
    public List<EventDTO> getEventsDTOExceptForUser(Long idLoggedUser) {
        return repoEvents.findAll().stream()
                .map(event -> getEventDTO(event.getId()))
                .filter(eventDTO -> eventDTO.getUsers().get().stream()
                        .noneMatch(user -> user.getUser().getId().equals(idLoggedUser)))
                .toList();
    }

    /**
     * Creates an event
     *
     * @param name         of the event
     * @param location     of the event
     * @param date         of the event
     * @param description  of the event
     * @param idLoggedUser to add as a participant
     */
    public boolean createEvent(String name, String location, LocalDate date, String description, Long idLoggedUser) {
        Event1 event = new Event1(name, date, location, description);
        eventValidator.validate(event);
        event = repoEvents.save(event);
        UserEventInfo user = new UserEventInfo(event.getId(), idLoggedUser, true);
        if (repoUserEventInfo.save(user) == null)
            throw new ServiceException("You could not be added to the event list!");
        return event != null;
    }

    /**
     * Adds a user to an event
     *
     * @param idEvent      id of the event
     * @param idLoggedUser id of the user
     */
    public void addUserToEvent(Long idEvent, Long idLoggedUser) {
        Event1 event = repoEvents.findOne(idEvent);
        if (event == null)
            throw new ServiceException("The event doesn't exist!");
        User user = repoUsers.findOne(idLoggedUser);
        if (user == null)
            throw new ServiceException("The id doesn't belong to a user!");
        UserEventInfo userInfo = new UserEventInfo(idEvent, idLoggedUser, true);
        validatorUserInfo.validate(userInfo);
        if (repoUserEventInfo.save(userInfo) == null)
            throw new ServiceException("The user could not be added to the event list!");
    }

    /**
     * Returns true if the notification for an upcoming event is off for a specific user
     *
     * @param idEvent id of the event
     * @param idUser  id of the user
     */
    public boolean notificationOff(Long idEvent, Long idUser) {
        UserEventInfo userEventInfo = repoUserEventInfo.findOne(new TupleWithIdEventUser(idEvent, idUser));
        return !userEventInfo.isNotification();

    }

    /**
     * Turns the notification option on and off for a specified user based on the boolean choice
     *
     * @param idEvent
     * @param idUser
     * @param choice
     */
    public void notificationButton(Long idEvent, Long idUser, boolean choice) {
        UserEventInfo user = new UserEventInfo(idEvent, idUser, choice);
        repoUserEventInfo.update(user);

    }

    /**
     * Returns a list of EventDTOs for a specified user wher notifications are turned on
     *
     * @param idLoggedUser
     * @return
     */
    public List<EventDTO> getEventsForNotification(Long idLoggedUser) {
        List<UserEventInfo> usersInfo = repoUserEventInfo.findAll();
        return usersInfo.stream()
                .filter(user -> user.isNotification() && Objects.equals(user.getIdUser(), idLoggedUser))
                .map(event -> getEventDTO(event.getIdEvent()))
                .filter(eventInfo -> eventInfo.getDate().getMonthValue() - LocalDate.now().getMonthValue() == 0 &&
                        eventInfo.getDate().getYear() - LocalDate.now().getYear() == 0 &&
                        (eventInfo.getDate().getDayOfMonth() - LocalDate.now().getDayOfMonth() < 3))
                .toList();
    }
}
