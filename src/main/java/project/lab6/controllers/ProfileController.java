package project.lab6.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import project.lab6.domain.User;
import project.lab6.factory.Factory;
import project.lab6.service.ServiceFriends;
import project.lab6.service.ServiceMessages;
import project.lab6.utils.Constants;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController extends Controller implements Initializable {
    @FXML
    Label labelHello;
    @FXML
    Label labelFirstName;
    @FXML
    Label labelLastName;
    @FXML
    Label labelEmail;

    private final Long idLoggedUser;
    private final ServiceFriends serviceFriends;
    private final ServiceMessages serviceMessages;

    public ProfileController(Long idLoggeduser, ServiceFriends serviceFriends, ServiceMessages serviceMessages) {
        this.idLoggedUser = idLoggeduser;
        this.serviceFriends = serviceFriends;
        this.serviceMessages = serviceMessages;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = serviceFriends.getUserWithFriends(idLoggedUser);
        labelHello.setText(String.format("Hello %s! Welcome back!", user.getFirstName()));
        labelFirstName.setText(String.format("First name: %s", user.getFirstName()));
        labelLastName.setText(String.format("Last name: %s", user.getLastName()));
        labelEmail.setText(String.format("Email: %s", user.getEmail()));
    }

    public void openPDF() throws IOException {
        FXMLLoader loader = Factory.getInstance().getLoader(new FriendsMessagesPDFController(idLoggedUser, serviceMessages, serviceFriends));
        Stage mainStage = new Stage();
        Scene scene = new Scene(loader.load(), 600, 500);
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }

    @Override
    public String getViewPath() {
        return Constants.View.PROFILE;
    }
}
