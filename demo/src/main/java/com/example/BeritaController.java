package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class BeritaController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        berandaButton.setOnAction(event -> handleBerandaButton(event));
        beritaButton.setOnAction(event -> handleBeritaButton(event));
        bayarButton.setOnAction(event -> handleBayarButton(event));
        historyButton.setOnAction(event -> handleHistoryButton(event));
        akunButton.setOnAction(event -> handleAkunButton(event));
        klaimButton.setOnAction(event -> handleKlaimButton(event));
        
        // Inisialisasi event handler untuk tombol-tombol lainnya
        KeluargaHarmonisButton.setOnAction(event -> handleKeluargaHarmonisButton(event));
        Berita2Button.setOnAction(event -> handleBerita2Button(event));
        Berita3Button.setOnAction(event -> handleBerita3Button(event));
        Berita4Button.setOnAction(event -> handleBerita4Button(event));
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

    private void handleKeluargaHarmonisButton(ActionEvent event) {
        switchScene(event, "Berita1.fxml"); // Sesuaikan dengan nama file FXML yang sesuai
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

    private void switchScene(ActionEvent event, String fxmlFile) {
        try {
            Pane root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(root);
            Stage stage = (Stage) berandaButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
