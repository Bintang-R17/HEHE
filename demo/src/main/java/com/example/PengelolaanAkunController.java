package com.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PengelolaanAkunController {

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

    @FXML
    private TextField usernameField;

    @FXML
    private TextField riwayatLoginField;

    @FXML
    private TextField tanggalPenerimaanField;

    @FXML
    private TextField jenisField;

    @FXML
    private TextField nominalField;

    @FXML
    private Button buatGrafikButton;

    @FXML
    private Button simpanDataButton;

    @FXML
    private Button simpanEditButton;

    @FXML
    private Button editDataButton;

    @FXML
    private StackedBarChart<String, Number> stackedBarChart;

    @FXML
    private LineChart<String, Number> lineChart;

    private ObservableList<DataAkun> dataAkunList = FXCollections.observableArrayList();

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

    @FXML
private void editData() {
    DataAkun selectedData = tableView.getSelectionModel().getSelectedItem();
    if (selectedData != null) {
        usernameField.setText(selectedData.getUsername());
        riwayatLoginField.setText(selectedData.getRiwayatLogin());
        tanggalPenerimaanField.setText(selectedData.getTanggalPenerimaan().format(dateFormatter));
        jenisField.setText(selectedData.getJenis());
        nominalField.setText(String.valueOf(selectedData.getNominal()));
    } else {
        System.out.println("Pilih data yang ingin diedit terlebih dahulu.");
    }
}


    @FXML
private void simpanEditData() {
    DataAkun selectedData = tableView.getSelectionModel().getSelectedItem();
    if (selectedData != null) {
        String username = usernameField.getText();
        String riwayatLogin = riwayatLoginField.getText();
        String tanggalPenerimaanText = tanggalPenerimaanField.getText();
        String jenis = jenisField.getText();
        String nominalText = nominalField.getText();

        if (username.isEmpty() || riwayatLogin.isEmpty() || tanggalPenerimaanText.isEmpty() || jenis.isEmpty() || nominalText.isEmpty()) {
            System.out.println("Semua field harus diisi");
            return;
        }

        LocalDate tanggalPenerimaan;
        try {
            tanggalPenerimaan = LocalDate.parse(tanggalPenerimaanText, dateFormatter);
        } catch (DateTimeParseException e) {
            System.out.println("Format tanggal tidak valid. Gunakan format 'dd MMMM yyyy', misalnya '19 Mei 2023'.");
            return;
        }

        double nominal;
        try {
            nominal = Double.parseDouble(nominalText);
        } catch (NumberFormatException e) {
            System.out.println("Nominal harus berupa angka.");
            return;
        }

        // Buat objek DataAkun dari input yang diedit
        DataAkun editedData = new DataAkun(username, riwayatLogin, tanggalPenerimaan, jenis, nominal);

        // Perbarui dataAkunList dengan data yang diedit
        int index = dataAkunList.indexOf(selectedData);
        if (index != -1) {
            dataAkunList.set(index, editedData);
        } else {
            System.out.println("Error: Data yang ingin diedit tidak ditemukan dalam daftar.");
            return;
        }

        // Kosongkan field input setelah berhasil disimpan
        kosongkanTextField();

        // Simpan perubahan ke file CSV dan perbarui grafik
        simpanKeCSV();
        loadLineChartData();
    } else {
        System.out.println("Pilih data yang ingin diedit terlebih dahulu.");
    }
}



    @FXML
    public void initialize() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        riwayatLoginColumn.setCellValueFactory(new PropertyValueFactory<>("riwayatLogin"));
        tanggalPenerimaanColumn.setCellValueFactory(new PropertyValueFactory<>("tanggalPenerimaan"));
        jenisColumn.setCellValueFactory(new PropertyValueFactory<>("jenis"));
        nominalColumn.setCellValueFactory(new PropertyValueFactory<>("nominal"));

        lineChart.setTitle("Perubahan Nominal Berdasarkan Tanggal");
        loadLineChartData();

        loadDataFromCSV();
        tableView.setItems(dataAkunList);
        editDataButton.setOnAction(event -> editData());

        berandaButton.setOnAction(event -> switchScene(event, "Dashboard(Adm).fxml"));
        notifikasiButton.setOnAction(event -> switchScene(event, "Notifikasi(Adm).fxml"));
        pengelolaanAkunButton.setOnAction(event -> switchScene(event, "PengelolaanAkun(Adm).fxml"));
        kirimButton.setOnAction(event -> switchScene(event, "KirimProduk(Adm).fxml"));
        akunButton.setOnAction(event -> switchScene(event, "Akun(Adm).fxml"));

        buatGrafikButton.setOnAction(event -> buatGrafik());
    }

    private void loadLineChartData() {
        lineChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Perubahan Nominal");

        for (DataAkun data : dataAkunList) {
            series.getData().add(new XYChart.Data<>(data.getTanggalPenerimaan().format(dateFormatter), data.getNominal()));
        }

        lineChart.getData().add(series);
    }

    private void loadDataFromCSV() {
        dataAkunList.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("dataAkun.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 5) {
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
                    }
                } else {
                    System.out.println("Invalid data format in CSV: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading data from CSV: " + e.getMessage());
        }
    }

    @FXML
    private void tambahData() {
        String username = usernameField.getText();
        String riwayatLogin = riwayatLoginField.getText();
        String tanggalPenerimaanText = tanggalPenerimaanField.getText();
        String jenis = jenisField.getText();
        String nominalText = nominalField.getText();

        if (username.isEmpty() || riwayatLogin.isEmpty() || tanggalPenerimaanText.isEmpty() || jenis.isEmpty() || nominalText.isEmpty()) {
            System.out.println("Semua field harus diisi");
            return;
        }

        LocalDate tanggalPenerimaan;
        try {
            tanggalPenerimaan = LocalDate.parse(tanggalPenerimaanText, dateFormatter);
        } catch (DateTimeParseException e) {
            System.out.println("Format tanggal tidak valid. Gunakan format 'dd MMMM yyyy', misalnya '19 Mei 2023'.");
            return;
        }

        double nominal;
        try {
            nominal = Double.parseDouble(nominalText);
        } catch (NumberFormatException e) {
            System.out.println("Nominal harus berupa angka.");
            return;
        }

        DataAkun newData = new DataAkun(username, riwayatLogin, tanggalPenerimaan, jenis, nominal);
        dataAkunList.add(newData);

        kosongkanTextField();
        simpanKeCSV();
        loadLineChartData();
    }

    @FXML
    private void buatGrafik() {
        loadLineChartData();
    }

    @FXML
    private void simpanData() {
        simpanKeCSV();
    }

    private void simpanKeCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dataAkun.csv"))) {
            for (DataAkun data : dataAkunList) {
                writer.write(String.format("%s;%s;%s;%s;%s\n",
                        data.getUsername(),
                        data.getRiwayatLogin(),
                        data.getTanggalPenerimaan().format(dateFormatter),
                        data.getJenis(),
                        data.getNominal()));
            }
        } catch (IOException e) {
            System.out.println("Error saving data to CSV: " + e.getMessage());
        }
    }

    @FXML
    private void hapusData() {
        DataAkun selectedData = tableView.getSelectionModel().getSelectedItem();
        if (selectedData != null) {
            dataAkunList.remove(selectedData);
            simpanKeCSV();
            loadLineChartData();
        } else {
            System.out.println("Pilih data yang ingin dihapus terlebih dahulu.");
        }
    }

    private void kosongkanTextField() {
        usernameField.clear();
        riwayatLoginField.clear();
        tanggalPenerimaanField.clear();
        jenisField.clear();
        nominalField.clear();
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
