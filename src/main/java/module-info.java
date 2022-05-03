module project.lab6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports project.lab6;
    exports project.lab6.controllers;
    opens project.lab6.controllers to javafx.fxml;
    exports project.lab6.factory;
    opens project.lab6.factory to javafx.fxml;
    exports project.lab6.service;
    exports project.lab6.repository.repointerface;
    exports project.lab6.domain;
    exports project.lab6.domain.chat;
    exports project.lab6.domain.validators;
    exports project.lab6.domain.dtos;
    exports project.lab6.repository.database;
    exports project.lab6.utils;
    exports project.lab6.utils.observer;
    exports project.lab6.controllers.friends;
    opens project.lab6.controllers.friends to javafx.fxml;
    exports project.lab6.controllers.messages;
    opens project.lab6.controllers.messages to javafx.fxml;
    exports project.lab6.controllers.utils;
    opens project.lab6.controllers.utils to javafx.fxml;
    exports project.lab6.controllers.login;
    opens project.lab6.controllers.login to javafx.fxml;
    exports project.lab6.controllers.events;
    opens project.lab6.controllers.events to javafx.fxml;
    requires org.apache.pdfbox;
    requires org.controlsfx.controls;
}