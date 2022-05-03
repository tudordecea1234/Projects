package project.lab6.controllers.events;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import project.lab6.controllers.AlertMessage;
import project.lab6.controllers.Controller;
import project.lab6.service.ServiceEvents;
import project.lab6.utils.Constants;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class CreateEventController extends Controller {
    private final Long idLoggedUser;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField locationTextField;
    @FXML
    private TextField dateTextField;
    @FXML
    private TextField descriptionTextField;
    private final ServiceEvents serviceEvents;


    public CreateEventController(ServiceEvents service, Long id) {
        this.serviceEvents = service;
        this.idLoggedUser = id;
    }

    public void createEvent(ActionEvent actionEvent) throws IOException {
        if (Objects.equals(dateTextField.textProperty().getValue(), "") ||
                Objects.equals(locationTextField.textProperty().getValue(), "") ||
                Objects.equals(descriptionTextField.textProperty().getValue(), "") ||
                Objects.equals(nameTextField.textProperty().getValue(), ""))
            AlertMessage.showErrorMessage("You must complete all of the required information!");
        else {
            LocalDate date = LocalDate.parse(dateTextField.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            boolean result = serviceEvents.createEvent(nameTextField.getText(),
                    locationTextField.getText(), date,
                    descriptionTextField.getText(),
                    this.idLoggedUser);
            if (result) {
                getStage().close();
                AlertMessage.showInfoMessage("The event was created successfully!");

            } else {
                AlertMessage.showErrorMessage("The event could not be created");
            }
        }

    }

    @Override
    public String getViewPath() {
        return Constants.View.CREATE_NEW_EVENT;
    }
}