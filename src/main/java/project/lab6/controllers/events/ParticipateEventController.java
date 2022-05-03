package project.lab6.controllers.events;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import project.lab6.controllers.Controller;
import project.lab6.controllers.friends.FriendsController;
import project.lab6.controllers.utils.EventButton;
import project.lab6.factory.Factory;
import project.lab6.service.ServiceEvents;
import project.lab6.utils.Constants;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ParticipateEventController extends Controller {
    private final ServiceEvents serviceEvents;
    private final Long idLoggedUser;
    ObservableList<EventButton> modelEvents = FXCollections.observableArrayList();

    public ParticipateEventController(ServiceEvents service, Long id) {
        this.serviceEvents = service;
        this.idLoggedUser = id;

    }

    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TableColumn<EventButton, String> name;
    @FXML
    private TableColumn<EventButton, LocalDate> date;
    @FXML
    private TableColumn<EventButton, String> description;
    @FXML
    private TableColumn<EventButton, Button> button;
    @FXML
    private TableView<EventButton> eventsTableView;


    public void initialize() throws IOException {
        comboBox.setItems(FXCollections.observableArrayList("My events", "Events"));
        comboBox.getSelectionModel().selectedItemProperty().addListener((x) -> initializeCombo(x.toString()));
        name.setCellValueFactory((new PropertyValueFactory<EventButton, String>("name")));
        description.setCellValueFactory((new PropertyValueFactory<EventButton, String>("description")));
        date.setCellValueFactory((new PropertyValueFactory<EventButton, LocalDate>("date")));
        comboBox.getSelectionModel().selectedItemProperty().addListener(
                (x, y, z) -> initializeCombo(z.toString())
        );
        eventsTableView.getStylesheets().add(FriendsController.class.getClassLoader().getResource("project/lab6/css/tableViewNoHorizontalScroll.css").toExternalForm());
        if ((long) serviceEvents.getEventsForNotification(idLoggedUser).size() > 0) {
            List<EventButton> message = serviceEvents.getEventsForNotification(idLoggedUser).stream()
                    .map(event -> new EventButton(event.getName(),
                            event.getLocation(), event.getDate(),
                            event.getDescription(), createAddButton(event.getIdEvent()))).toList();
            message.forEach(x -> {
                Notifications.create()
                        .title("You have upcoming events:")
                        .text(x.toString())
                        .showInformation();
            });


        } else
            Notifications.create()
                    .title("Events")
                    .text("You don't have any upcoming events")
                    .showInformation();
    }


    public void openCreateEvent(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = Factory.getInstance().getLoader(new CreateEventController(serviceEvents, idLoggedUser));
        Stage mainStage = new Stage();
        Scene scene = new Scene(loader.load(), 600, 500);
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();

    }

    public void initializeCombo(String status) {
        if (Objects.equals(status, "My events")) {
            button.setCellValueFactory(new PropertyValueFactory<EventButton, Button>("button"));
            modelEvents.setAll(getEventsListForUser());
            eventsTableView.setItems(modelEvents);
        }
        if (Objects.equals(status, "Events")) {
            button.setCellValueFactory(new PropertyValueFactory<EventButton, Button>("button"));
            modelEvents.setAll(getAllEvents());
            eventsTableView.setItems(modelEvents);
        }
    }

    public List<EventButton> getEventsListForUser() {
        return serviceEvents.getEventsDTOForUser(idLoggedUser).stream()
                .map(event -> new EventButton(event.getName(),
                        event.getLocation(), event.getDate(),
                        event.getDescription(), createTurnOffButton(event.getIdEvent()))).toList();
    }

    public List<EventButton> getAllEvents() {
        return serviceEvents.getEventsDTOExceptForUser(idLoggedUser).stream()
                .map(event -> new EventButton(event.getName(),
                        event.getLocation(), event.getDate(),
                        event.getDescription(), createAddButton(event.getIdEvent()))).toList();
    }

    public Button createAddButton(Long idEvent) {
        Button button = new Button();
        button.setText("Join");
        button.setOnAction(event -> {
            serviceEvents.addUserToEvent(idEvent, idLoggedUser);
            modelEvents.setAll(getAllEvents());
        });
        return button;
    }

    public Button createTurnOffButton(Long eventId) {
        Button createTurnOff = new Button();
        if (serviceEvents.notificationOff(eventId, idLoggedUser)) {
            createTurnOff.setText("Notification on");
            createTurnOff.setOnAction(event -> {
                serviceEvents.notificationButton(eventId, idLoggedUser, true);
                modelEvents.setAll(getEventsListForUser());
            });
        } else {
            createTurnOff.setText("Notification off");
            createTurnOff.setOnAction(event -> {
                serviceEvents.notificationButton(eventId, idLoggedUser, false);
                modelEvents.setAll(getEventsListForUser());
            });

        }
        return createTurnOff;
    }

    @Override
    public String getViewPath() {
        return Constants.View.PARTICIPATE_EVENT;
    }
}
