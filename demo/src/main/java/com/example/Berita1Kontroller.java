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

public class Berita1Kontroller {

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
    private Button KeluargaHarmonisButton;

    @FXML 
    private Button Berita2Button;

    @FXML 
    private Button Berita3Button;

    @FXML 
    private Button Berita4Button;

    @FXML
    public void initialize() {
        berandaButton.setOnAction(event -> handleBerandaButton(event));
        beritaButton.setOnAction(event -> handleBeritaButton(event));
        bayarButton.setOnAction(event -> handleBayarButton(event));
        historyButton.setOnAction(event -> handleHistoryButton(event));
        akunButton.setOnAction(event -> handleAkunButton(event));
        klaimButton.setOnAction(event -> handleKlaimButton(event));
        KeluargaHarmonisButton.setOnAction(event -> handleBerita1Button(event));
        Berita2Button.setOnAction(event -> handleBerita2Button(event));
        Berita3Button.setOnAction(event -> handleBerita3Button(event));
        Berita4Button.setOnAction(event -> handleBerita4Button(event));
    }

    private void handleBerita1Button(ActionEvent event) {
        switchScene(event, "Berita1.fxml");
    }

    private void handleBerita2Button(ActionEvent event) {
        switchScene(event, "Berita2.fxml");
    }

    private void handleBerita3Button(ActionEvent event) {
        switchScene(event, "Berita3.fxml");
    }

    private void handleBerita4Button(ActionEvent event) {
        switchScene(event, "Berita4.fxml");
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