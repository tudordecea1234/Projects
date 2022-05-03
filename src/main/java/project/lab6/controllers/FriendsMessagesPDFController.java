package project.lab6.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import project.lab6.service.ServiceFriends;
import project.lab6.service.ServiceMessages;
import project.lab6.utils.Constants;
import project.lab6.utils.PDFDocument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class FriendsMessagesPDFController extends Controller {
    private final Long idLoggedUser;
    private final ServiceMessages serviceMessages;
    private final ServiceFriends serviceFriends;
    @FXML
    private TextField dateFrom;
    @FXML
    private TextField dateUntil;

    public FriendsMessagesPDFController(Long idLoggedUser, ServiceMessages serviceMessages, ServiceFriends serviceFriends) {
        this.idLoggedUser = idLoggedUser;
        this.serviceFriends = serviceFriends;
        this.serviceMessages = serviceMessages;

    }

    public void generatePdf(ActionEvent actionEvent) {
        String date1 = dateFrom.textProperty().getValue();
        String date2 = dateUntil.textProperty().getValue();
        if (Objects.equals(date1, "") || Objects.equals(date2, ""))
            AlertMessage.showErrorMessage("You must complete both fields!");

        assert date1 != null;
        LocalDate Local1 = LocalDate.parse(date1, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        assert date2 != null;
        LocalDate Local2 = LocalDate.parse(date2, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        PDFDocument document = new PDFDocument();
        List<String> messages = serviceMessages.
                getAllMessagesDTOForUser(idLoggedUser, Local1, Local2)
                .stream()
                .map(x -> x.getUserFromInfo().getUser().getFirstName() + " "
                        + x.getUserFromInfo().getUser().getLastName() +
                        ": '" + x.getText() + "' on " +
                        x.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy,HH:mm")).toString())
                .toList();
        List<String> friends = serviceFriends.getFriendsBetweenDates(idLoggedUser, Local1, Local2)
                .stream().map(friend -> friend.getUser().getFirstName() + " "
                        + friend.getUser().getLastName() + " " + friend.getDate().toString())
                .toList();

        document.createFriendsMessagesPDF("Raport_" + serviceFriends.findOne(idLoggedUser).getFirstName() + "_" +
                        serviceFriends.findOne(idLoggedUser).getLastName() + "_" + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString() +
                        ".pdf",
                "The period of time is:" + Local1.toString() + " and " + Local2.toString(), messages, friends);
        getStage().close();
        AlertMessage.showInfoMessage("The PDF was created successfully!");


    }

    @Override
    public String getViewPath() {
        return Constants.View.RAPORT_PDF;
    }
}
