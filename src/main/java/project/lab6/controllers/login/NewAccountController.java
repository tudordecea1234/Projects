package project.lab6.controllers.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.lab6.controllers.AlertMessage;
import project.lab6.controllers.Controller;
import project.lab6.controllers.MainViewController;
import project.lab6.domain.User;
import project.lab6.domain.validators.ValidationException;
import project.lab6.factory.Factory;
import project.lab6.service.ServiceEvents;
import project.lab6.service.ServiceFriends;
import project.lab6.service.ServiceMessages;
import project.lab6.utils.Constants;

import java.io.IOException;

public class NewAccountController extends Controller {

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordTextField;

    private final ServiceFriends serviceFriends;
    private final ServiceMessages serviceMessages;
    private final ServiceEvents serviceEvents;

    public NewAccountController(ServiceFriends serviceFriends, ServiceMessages serviceMessages,ServiceEvents serviceEvents) {
        this.serviceFriends = serviceFriends;
        this.serviceMessages = serviceMessages;
        this.serviceEvents=serviceEvents;
    }


    private void closeWindow() {
        Stage stage = (Stage) emailTextField.getScene().getWindow();
        stage.close();
    }

    /**
     * Creates a new account for the user and logs it in
     *
     * @param actionEvent
     * @throws IOException
     */
    public void registerUser(ActionEvent actionEvent) throws IOException {
        try {
            boolean result = serviceFriends.addUser(emailTextField.getText(), firstNameTextField.getText(), lastNameTextField.getText(), passwordTextField.getText());
            if (result) {
                User loggedUser = serviceFriends.findUserByEmail(emailTextField.getText());
                FXMLLoader loader = Factory.getInstance().getLoader(new MainViewController(loggedUser.getId(), serviceFriends, serviceMessages,serviceEvents));
                Stage mainStage = new Stage();
                Scene scene = new Scene(loader.load(), 600, 500);
                mainStage.setScene(scene);
                mainStage.setResizable(false);
                mainStage.show();
                closeWindow();
            } else {
                AlertMessage.showErrorMessage("You already have an account with this email address!");
            }
        } catch (ValidationException validationException) {
            AlertMessage.showErrorMessage(validationException.getMessage());
        }
    }

    public void backToLogIn(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = Factory.getInstance().getLoader(new LoginController(serviceFriends, serviceMessages,serviceEvents));
        Stage loginStage = new Stage();
        Scene scene = new Scene(loader.load(), 600, 400);
        loginStage.setScene(scene);
        loginStage.setResizable(false);
        closeWindow();
        loginStage.show();
    }

    @Override
    public String getViewPath() {
        return Constants.View.CREATE_NEW_ACCOUNT;
    }
}
