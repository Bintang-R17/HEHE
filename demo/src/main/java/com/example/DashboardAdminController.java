package com.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class DashboardAdminController {

    @FXML
    private Label titleLabel;

    @FXML
    private Button notifikasiButton;
    
        @FXML
        private TableColumn<RiwayatLogin, String> tcUsername;
    
        @FXML
        private TableColumn<RiwayatLogin, String> tcNoHP;
    
        @FXML
        private TableColumn<RiwayatLogin, String> tcWaktuLogin;

        @FXML
        private TableColumn<RiwayatLogin, String> tcStatus;
    @FXML
    private Button pengelolaanAkunButton;

    @FXML
        private Button hapusButton;
    
        @FXML
        private Button hapusSemuaButton;
        
    @FXML
    private Button kirimButton;

    @FXML
    private Button akunButton;

    @FXML
    private TableView<RiwayatLogin> tableView;

    @FXML
    private CheckBox investasiCheckBox;

    @FXML
    private CheckBox asuransiCheckBox;

    @FXML
    private Button simpanDataButton;

    private static final String CSV_FILE = "loginData.csv";
    private LinkedList<RiwayatLogin> loginDataList = new LinkedList<>();

    @FXML
    public void initialize() {
        tcUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
            tcNoHP.setCellValueFactory(new PropertyValueFactory<>("nomorhp"));
            tcWaktuLogin.setCellValueFactory(new PropertyValueFactory<>("waktuLogin"));
            tcStatus.setCellValueFactory(new PropertyValueFactory<>("status")); // bind to status property
        notifikasiButton.setOnAction(event -> handleNotifikasiButton(event));
        pengelolaanAkunButton.setOnAction(event -> handlePengelolaanAkunButton(event));
        kirimButton.setOnAction(event -> handleKirimButton(event));
        akunButton.setOnAction(event -> handleAkunButton(event));
        hapusButton.setOnAction(this::handleHapusButton);
    
            // Set up delete all button action
            hapusSemuaButton.setOnAction(this::handleHapusSemuaButton);

        loadLoginData(); // Load data from CSV when initializing
    }

    private void handleNotifikasiButton(javafx.event.ActionEvent event) {
        switchScene(event, "Notifikasi(Adm).fxml");
    }

    private void handlePengelolaanAkunButton(javafx.event.ActionEvent event) {
        switchScene(event, "PengelolaanAkun(Adm).fxml");
    }

    private void handleKirimButton(javafx.event.ActionEvent event) {
        switchScene(event, "KirimProduk(Adm).fxml");
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

    private void handleHapusButton(ActionEvent event) {
            RiwayatLogin selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                tableView.getItems().remove(selected);
                loginDataList.remove(selected); // Remove from LinkedList
                updateCSVFile(); // Update CSV setelah menghapus item
            }
        }
    
        private void handleHapusSemuaButton(ActionEvent event) {
            tableView.getItems().clear();
            loginDataList.clear(); // Clear LinkedList
            updateCSVFile(); // Update CSV setelah menghapus semua item
        }
    
        private void updateCSVFile() {
            try (PrintWriter writer = new PrintWriter(new File(CSV_FILE))) {
                for (RiwayatLogin login : loginDataList) {
                    writer.println(login.getUsername() + "," + login.getNomorhp() + "," + login.getWaktuLogin() + "," + login.getStatus());
                }
            } catch (FileNotFoundException e) {
                System.out.println("File CSV tidak ditemukan, tidak dapat memperbarui data.");
            }
        }
    
        private void loadLoginData() {
            loginDataList.clear(); // Clear existing data
    
            try (Scanner scanner = new Scanner(new File(CSV_FILE))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",", 4); // ubah menjadi 4 bagian untuk menangani status
                    if (parts.length >= 4) {
                        String username = parts[0];
                        String nohp = parts[1];
                        String waktuLogin = parts[2];
                        String status = parts[3];
    
                        RiwayatLogin login = new RiwayatLogin(username, nohp, waktuLogin, status);
                        loginDataList.add(login); // Add to LinkedList
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("File CSV tidak ditemukan, tidak ada data login yang dimuat.");
            }
    
            ObservableList<RiwayatLogin> data = FXCollections.observableArrayList(loginDataList);
            tableView.setItems(data);
        }
        
}
