package com.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class KlaimKontroller {
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
    private Button simpanDataButton;

    @FXML
    private CheckBox investasiCheckBox;

    @FXML
    private CheckBox asuransiCheckBox;

    private static final String CSV_FILE = "Jenis.csv";

    @FXML
    public void initialize() {
        berandaButton.setOnAction(event -> handleBerandaButton(event));
        beritaButton.setOnAction(event -> handleBeritaButton(event));
        bayarButton.setOnAction(event -> handleBayarButton(event));
        historyButton.setOnAction(event -> handleHistoryButton(event));
        akunButton.setOnAction(event -> handleAkunButton(event));
        klaimButton.setOnAction(event -> handleKlaimButton(event));
        simpanDataButton.setOnAction(event -> handleSimpanDataButton(event));
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

    private void handleSimpanDataButton(ActionEvent event) {
        if (investasiCheckBox.isSelected()) {
            simpanData("Investasi");
        }
        if (asuransiCheckBox.isSelected()) {
            simpanData("Asuransi");
        }
        // Tambahkan logika tambahan jika diperlukan
    }
    private void simpanData(String jenis) {
        double nominal = 0.0; // Ganti dengan nilai nominal yang sesuai dari TextField atau kontrol lainnya
        
        // Tulis ke file CSV
        try (Writer writer = new FileWriter(CSV_FILE, true)) {
            String data = String.format("%s,%.2f%n", jenis, nominal);
            writer.write(data);
            System.out.println("Data berhasil disimpan ke file " + CSV_FILE);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Gagal menyimpan data ke file " + CSV_FILE + ": " + e.getMessage());
        }
    }
}
