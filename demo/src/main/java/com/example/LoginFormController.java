package com.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class LoginFormController {

    @FXML
    private Pane mainSlide;

    @FXML
    private Pane adminSlide;

    @FXML
    private TextField adminUsernameField;

    @FXML
    private TextField adminNomorHPField;

    @FXML
    private Button adminLoginButton;

    @FXML
    private Button mainAdminLoginButton;

    @FXML
    private Pane userSlide;

    @FXML
    private TextField userUsernameField;

    @FXML
    private TextField userNomorHPField;

    @FXML
    private Button userLoginButton;

    @FXML
    private Button mainUserLoginButton;

    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageView2;

    @FXML
    private ImageView imageView3;

    @FXML
    private ImageView imageView4;

    @FXML
    private Text adminErrorText;

    @FXML
    private Text userErrorText;

    private static final String CSV_FILE = "loginData.csv";

    private LinkedList<RiwayatLogin> loginDataList;

    @FXML
    public void initialize() {
        // Initialize button actions
        mainAdminLoginButton.setOnAction(event -> switchToAdminLogin());
        mainUserLoginButton.setOnAction(event -> switchToUserLogin());
        adminLoginButton.setOnAction(event -> handleAdminLogin());
        userLoginButton.setOnAction(event -> handleUserLogin());

        // Initialize login data list
        loginDataList = new LinkedList<>();

        // Load saved login data
        loadLoginData();

        // Load images
        loadImages();
    }

    private void loadImages() {
        try {
            imageView1.setImage(new Image(getClass().getResourceAsStream("/images/Illustration15.png")));
            imageView2.setImage(new Image(getClass().getResourceAsStream("/images/Illustration16.png")));
            imageView3.setImage(new Image(getClass().getResourceAsStream("/images/Illustration2.png")));
            imageView4.setImage(new Image(getClass().getResourceAsStream("/images/Illustration11.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void switchToAdminLogin() {
        mainSlide.setVisible(false);
        adminSlide.setVisible(true);
        userSlide.setVisible(false);
    }

    private void switchToUserLogin() {
        mainSlide.setVisible(false);
        adminSlide.setVisible(false);
        userSlide.setVisible(true);
    }

    private void handleAdminLogin() {
        String username = adminUsernameField.getText();
        String nomorHP = adminNomorHPField.getText();

        // Validate input
        if (username.isEmpty() || nomorHP.isEmpty()) {
            adminErrorText.setText("Tidak bisa masuk, harus input nama"); // Set error message
            return;
        } else {
            adminErrorText.setText(""); // Clear error message if valid
        }

        // Save admin login data with timestamp
        saveLoginData(new RiwayatLogin(username, nomorHP, getCurrentTimeStamp(), "login"));

        // Implement login logic here

        // After successful login, navigate to the dashboard
        switchScene("Dashboard(Adm).fxml");
    }

    private void handleUserLogin() {
        String username = userUsernameField.getText();
        String nomorHP = userNomorHPField.getText();

        // Validate input
        if (username.isEmpty() || nomorHP.isEmpty()) {
            userErrorText.setText("Tidak bisa masuk, harus input nama"); // Set error message
            return;
        } else {
            userErrorText.setText(""); // Clear error message if valid
        }

        // Save user login data with timestamp
        saveLoginData(new RiwayatLogin(username, nomorHP, getCurrentTimeStamp(), "login"));

        // Implement login logic here

        // After successful login, navigate to the dashboard
        switchScene("Dashboard(P).fxml");
    }

    private String getCurrentTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        return sdf.format(now);
    }

    private void switchScene(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) mainSlide.getScene().getWindow(); // Get the current stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading FXML: " + e.getMessage());
        }
    }

    private void saveLoginData(RiwayatLogin loginData) {
        // Add new login data to the list
        loginDataList.add(loginData);

        // Write data to CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE))) {
            for (RiwayatLogin data : loginDataList) {
                writer.write(data.toString());
                writer.newLine();
            }
            writer.flush();
            System.out.println("Data successfully saved to CSV file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadLoginData() {
        // Read data from CSV file
        try (Scanner scanner = new Scanner(new File(CSV_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",", 4);
                if (parts.length >= 4) {
                    loginDataList.add(new RiwayatLogin(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("CSV file not found, no login data loaded.");
        }

        // Load data into fields (optional, for demonstration purposes)
        for (RiwayatLogin data : loginDataList) {
            if (data.getUsername().equals("admin")) {
                adminUsernameField.setText(data.getUsername());
                adminNomorHPField.setText(data.getNomorhp());
            } else {
                userUsernameField.setText(data.getUsername());
                userNomorHPField.setText(data.getNomorhp());
            }
        }
    }
}
