package project.lab6.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import project.lab6.service.ServiceFriends;
import project.lab6.service.ServiceMessages;
import project.lab6.utils.Constants;
import project.lab6.utils.PDFDocument;
import project.lab6.utils.observer.ObservableChatDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GeneratePDFController extends Controller {
    private Long idLoggedUser;
    private ServiceMessages serviceMessages;
    private ObservableChatDTO observableChatDTO;
    private ServiceFriends serviceFriends;
    @FXML
    private TextField dateFrom;
    @FXML
    private TextField dateUntil;

    public GeneratePDFController(Long idLoggedUser, ServiceMessages serviceMessages, ServiceFriends serviceFriends, ObservableChatDTO observableChatDTO) {
        this.idLoggedUser = idLoggedUser;
        this.serviceFriends = serviceFriends;
        this.serviceMessages = serviceMessages;
        this.observableChatDTO = observableChatDTO;

    }

    public void generatePdf(ActionEvent actionEvent) {
        String date1 = dateFrom.textProperty().getValue();
        String date2 = dateUntil.textProperty().getValue();
        if (date1 == null || date2 == null)
            AlertMessage.showErrorMessage("You must complete both fields!");

        assert date1 != null;
        LocalDate Local1 = LocalDate.parse(date1, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        assert date2 != null;
        LocalDate Local2 = LocalDate.parse(date2, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        List<String> messages = serviceMessages.
                getMessagesDTOForUser(observableChatDTO.getChat().getIdChat(), idLoggedUser, Local1, Local2)
                .stream()
                .map(x -> x.getUserFromInfo().getUser().getFirstName() + " "
                        + x.getUserFromInfo().getUser().getLastName() +
                        ": '" + x.getText() + "' on " +
                        x.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy,HH:mm")))
                .toList();
        PDFDocument document = new PDFDocument();
        document.create("Friend_chat_raport_" +
                        serviceFriends.findOne(idLoggedUser).getFirstName() + "_" +
                        serviceFriends.findOne(idLoggedUser).getLastName() + "_" +
                        LocalDate.now() + ".pdf",
                "The period of time is: " + Local1.toString()
                        + " and " + Local2.toString(), messages);
        AlertMessage.showInfoMessage("The PDF was created successfully!");
        getStage().close();
    }

    @Override
    public String getViewPath() {
        return Constants.View.GENERATE_PDF;
    }
}
