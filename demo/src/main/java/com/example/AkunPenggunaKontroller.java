package com.example;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AkunPenggunaKontroller {
    @FXML
    private Label beritaLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Button berandaButton;

    @FXML
    private Button beritaButton;

    @FXML
    private Button bayarButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button akunButton;

    @FXML
    private Button klaimButton;

    @FXML
    public void initialize() {
        berandaButton.setOnAction(event -> handleBerandaButton(event));
        beritaButton.setOnAction(event -> handleBeritaButton(event));
        bayarButton.setOnAction(event -> handleBayarButton(event));
        historyButton.setOnAction(event -> handleHistoryButton(event));
        akunButton.setOnAction(event -> handleAkunButton(event));
        klaimButton.setOnAction(event -> handleKlaimButton(event));
    }

    private void handleBerandaButton(ActionEvent event) {
        switchScene(event, "Dashboard(P).fxml");
    }

    private void handleBeritaButton(ActionEvent event) {
        switchScene(event, "Berita(P).fxml");
    }

    private void handleBayarButton(ActionEvent event) {
        switchScene(event, "Bayar(P).fxml");
    }

    private void handleHistoryButton(ActionEvent event) {
        switchScene(event, "Riwayat(P).fxml");
    }

    private void handleAkunButton(ActionEvent event) {
        switchScene(event, "Akun(P).fxml");
    }

    private void handleKlaimButton(ActionEvent event) {
        switchScene(event, "Klaim(P).fxml");
    }

    private void switchScene(ActionEvent event, String fxmlFile) {
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
