package com.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class KirimProdukKontroller {

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
    private LineChart<String, Number> lineChart;

    @FXML
    private TableView<DataAkun> tableView;

    @FXML
    private TableColumn<DataAkun, String> usernameColumn;

    @FXML
    private TableColumn<DataAkun, String> riwayatLoginColumn;

    @FXML
    private TableColumn<DataAkun, LocalDate> tanggalPenerimaanColumn;

    @FXML
    private TableColumn<DataAkun, String> jenisColumn;

    @FXML
    private TableColumn<DataAkun, Double> nominalColumn;

    private ObservableList<DataAkun> dataAkunList = FXCollections.observableArrayList();
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    private String csvFilePath = "dataAkun.csv";

    @FXML
    public void initialize() {
        // Initialize table columns
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        riwayatLoginColumn.setCellValueFactory(new PropertyValueFactory<>("riwayatLogin"));
        tanggalPenerimaanColumn.setCellValueFactory(new PropertyValueFactory<>("tanggalPenerimaan"));
        jenisColumn.setCellValueFactory(new PropertyValueFactory<>("jenis"));
        nominalColumn.setCellValueFactory(new PropertyValueFactory<>("nominal"));

        // Load data from CSV on application start
        loadDataFromCSV();

        // Set table data
        tableView.setItems(dataAkunList);

        // Initialize button actions
        berandaButton.setOnAction(event -> switchScene(event, "Dashboard(Adm).fxml"));
        notifikasiButton.setOnAction(event -> switchScene(event, "Notifikasi(Adm).fxml"));
        pengelolaanAkunButton.setOnAction(event -> switchScene(event, "PengelolaanAkun(Adm).fxml"));
        kirimButton.setOnAction(event -> switchScene(event, "KirimProduk(Adm).fxml"));
        akunButton.setOnAction(event -> switchScene(event, "Akun(Adm).fxml"));

        simpanDataButton.setOnAction(event -> handleSimpanDataButton(event));
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

    @FXML
    private void handleSimpanDataButton(javafx.event.ActionEvent event) {
        if (investasiCheckBox.isSelected()) {
            simpanData("Investasi");
        }
        if (asuransiCheckBox.isSelected()) {
            simpanData("Asuransi");
        }
        // Additional logic if needed
    }

    private void simpanData(String jenis) {
        // Replace with actual logic to get nominal value
        double nominal = 0.0;

        // Write to CSV file
        try (FileWriter writer = new FileWriter(csvFilePath, true)) {
            String data = String.format("%s;%s;%s;%s;%.2f%n",
                    "Username", "Riwayat Login", LocalDate.now().format(dateFormatter), jenis, nominal);
            writer.write(data);
            System.out.println("Data berhasil disimpan ke file " + csvFilePath);

            // Add new data to table view
            DataAkun newData = new DataAkun("Username", "Riwayat Login", LocalDate.now(), jenis, nominal);
            dataAkunList.add(newData);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Gagal menyimpan data ke file " + csvFilePath + ": " + e.getMessage());
        }
    }

    private void loadDataFromCSV() {
        dataAkunList.clear(); // Clear existing data before loading from CSV
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 5) { // Ensure all columns are present
                    try {
                        String username = parts[0].trim();
                        String riwayatLogin = parts[1].trim();
                        LocalDate tanggalPenerimaan = LocalDate.parse(parts[2].trim(), dateFormatter);
                        String jenis = parts[3].trim();
                        double nominal = Double.parseDouble(parts[4].trim());
                        DataAkun data = new DataAkun(username, riwayatLogin, tanggalPenerimaan, jenis, nominal);
                        dataAkunList.add(data);
                    } catch (NumberFormatException | DateTimeParseException e) {
                        System.out.println("Error parsing data: " + e.getMessage());
                        // Handle parsing errors here
                    }
                } else {
                    System.out.println("Invalid data format in CSV: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading data from CSV: " + e.getMessage());
        }
    }
}
