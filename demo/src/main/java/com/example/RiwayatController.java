package com.example;

    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Scene;
    import javafx.scene.control.Button;
    import javafx.scene.control.Label;
    import javafx.scene.control.TableColumn;
    import javafx.scene.control.TableView;
    import javafx.scene.control.cell.PropertyValueFactory;
    import javafx.stage.Stage;

    import java.io.File;
    import java.io.FileNotFoundException;
    import java.io.IOException;
    import java.util.LinkedList;
    import java.util.Scanner;

    public class RiwayatController {

        @FXML
        private TableView<RiwayatLogin> tableView;
    
        @FXML
        private TableColumn<RiwayatLogin, String> tcUsername;
    
        @FXML
        private TableColumn<RiwayatLogin, String> tcNoHP;
    
        @FXML
        private TableColumn<RiwayatLogin, String> tcWaktuLogin;
    
        @FXML
        private TableColumn<RiwayatLogin, String> tcStatus; // tambahkan kolom status
    
        
    
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
    
        private static final String CSV_FILE = "LoginData.csv";
    
        private LinkedList<RiwayatLogin> loginDataList = new LinkedList<>();
    
        @FXML
        public void initialize() {
            // Set up table columns
            tcUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
            tcNoHP.setCellValueFactory(new PropertyValueFactory<>("nomorhp"));
            tcWaktuLogin.setCellValueFactory(new PropertyValueFactory<>("waktuLogin"));
            tcStatus.setCellValueFactory(new PropertyValueFactory<>("status")); // bind to status property
    
            // Load data into table
            loadLoginData();
    
            // Set up button actions
            berandaButton.setOnAction(this::handleBerandaButton);
            beritaButton.setOnAction(this::handleBeritaButton);
            bayarButton.setOnAction(this::handleBayarButton);
            historyButton.setOnAction(this::handleHistoryButton);
            akunButton.setOnAction(this::handleAkunButton);
            klaimButton.setOnAction(this::handleKlaimButton);
    
            // Set up delete button action
            
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
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(fxmlFile))));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
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

