package com.example;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class NotifikasiController {
    @FXML
    private Label titleLabel;

    @FXML
    private Button berandaButton;

    @FXML
    private Button notifikasiButton;

    @FXML
    private Button pengelolaanAkunButton;

    @FXML
    private Button kirimButton;

    @FXML
    private Button akunButton;

    @FXML
    private CheckBox investasiCheckBox;

    @FXML
    private CheckBox asuransiCheckBox;

    @FXML
    private Button simpanDataButton;

    @FXML
    private Label notifikasiLabel;

    public void setNotifikasiMessage(String message) {
        notifikasiLabel.setText(message);
    }

    @FXML
    public void initialize() {
        berandaButton.setOnAction(event -> handleBerandaAdmButton(event));
        notifikasiButton.setOnAction(event -> handleNotifikasiButton(event));
        pengelolaanAkunButton.setOnAction(event -> handlePengelolaanAkunButton(event));
        kirimButton.setOnAction(event -> handleKirimButton(event));
        akunButton.setOnAction(event -> handleAkunButton(event));
        NotificationManager notificationManager = NotificationManager.getInstance();
        String message = notificationManager.getMessage();
        if (message != null && !message.isEmpty()) {
            notifikasiLabel.setText(message);}

    }

    private void handleBerandaAdmButton(javafx.event.ActionEvent event) {
        switchScene(event, "Dashboard(Adm).fxml");
    }

    private void handleNotifikasiButton(javafx.event.ActionEvent event) {
        switchScene(event, "Notifikasi(Adm).fxml");
    }

    private void handlePengelolaanAkunButton(javafx.event.ActionEvent event) {
        switchScene(event, "PengelolaanAkun(Adm).fxml");
    }

    private void handleKirimButton(javafx.event.ActionEvent event) {
        switchScene(event, "Kirim(Adm).fxml");
    }

    private void handleAkunButton(javafx.event.ActionEvent event) {
        switchScene(event, "Akun(Adm).fxml");
    }

    private void switchScene(javafx.event.ActionEvent event, String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
