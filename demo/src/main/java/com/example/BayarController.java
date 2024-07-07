package com.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BayarController {

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

    @FXML
    private TextField nominalTextField;

    @FXML
    private Label resultLabel;

    private static final String CSV_FILE = "Bayar.csv";
    private static final String LOGIN_DATA_FILE = "LoginData.csv";

    private String lastLoggedInUser; // untuk menyimpan username terakhir yang login

    @FXML
    public void initialize() {
        berandaButton.setOnAction(this::handleBerandaButton);
        beritaButton.setOnAction(this::handleBeritaButton);
        bayarButton.setOnAction(this::handleBayarButton);
        historyButton.setOnAction(this::handleHistoryButton);
        akunButton.setOnAction(this::handleAkunButton);
        klaimButton.setOnAction(this::handleKlaimButton);
        simpanDataButton.setOnAction(this::handleSimpanDataButton);
    }

    private void handleBerandaButton(ActionEvent event) {
        switchScene(event, "Dashboard(P).fxml");
    }

    private void handleBeritaButton(ActionEvent event) {
        switchScene(event, "Berita(P).fxml");
    }

    // private void handleKirimButton(ActionEvent event) {
    //     // Kirim pesan notifikasi ke NotificationManager
        

    //     // Tampilkan pesan di slide saat ini (opsional)
    //     // notifikasiLabel.setText("Terdapat notifikasi masuk.");
    // }

    private void handleBayarButton(ActionEvent event) {
        try {
            String nominalText = nominalTextField.getText();
            if (!nominalText.isEmpty()) {
                double nominal = Double.parseDouble(nominalText);
                double result = nominal / 12;
                resultLabel.setText("Rp. " + result + " x 24 bulan");
                simpanData("Bayar", nominal); // Simpan data aksi "Bayar"
            } else {
                resultLabel.setText("Nominal tidak boleh kosong.");
            }
        } catch (NumberFormatException e) {
            resultLabel.setText("Masukkan nominal yang valid.");
        }
        NotificationManager notificationManager = NotificationManager.getInstance();
        notificationManager.setMessage("Terdapat notifikasi masuk.");
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
            simpanData("Investasi", 0); // 0 menandakan nominal tidak relevan untuk investasi
        }
        if (asuransiCheckBox.isSelected()) {
            String nominalText = nominalTextField.getText();
            if (!nominalText.isEmpty()) {
                try {
                    double nominal = Double.parseDouble(nominalText);
                    simpanData("Asuransi", nominal);
                } catch (NumberFormatException e) {
                    resultLabel.setText("Masukkan nominal yang valid.");
                }
            } else {
                resultLabel.setText("Nominal tidak boleh kosong.");
            }
        }
        // Tambahkan logika tambahan jika diperlukan
    }

    private void simpanData(String jenis, double nominal) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(new Date());

        try (Writer writer = new FileWriter(CSV_FILE, true)) {
            // Simpan status "Bayar" dengan jenis dan nominal
            writer.write(String.format("%s,%s,%.2f,%s%n", jenis, "Bayar", nominal, timestamp));
            System.out.println("Data pembayaran berhasil disimpan ke file " + CSV_FILE);

            // Update file LoginData.csv dengan status "Bayar" untuk username terakhir yang login
            if (lastLoggedInUser != null) {
                updateLoginDataFile(lastLoggedInUser, timestamp, "Bayar");
            } else {
                System.err.println("Username terakhir tidak tersedia untuk menyimpan data login.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Gagal menyimpan data pembayaran ke file " + CSV_FILE + ": " + e.getMessage());
        }
    }

    // Method untuk memperbarui file LoginData.csv dengan status "Bayar"
    private void updateLoginDataFile(String username, String timestamp, String status) {
    try {
        // Baca semua data dari file
        List<String> lines = Files.readAllLines(Paths.get(LOGIN_DATA_FILE));

        // Temukan baris terakhir untuk username yang cocok
        int lastIndex = -1;
        for (int i = lines.size() - 1; i >= 0; i--) {
            String line = lines.get(i);
            if (line.startsWith(username)) {
                lastIndex = i;
                break;
            }
        }

        // Jika ditemukan entri yang sesuai
        if (lastIndex != -1) {
            // Ambil baris terakhir untuk username yang cocok
            String lastLine = lines.get(lastIndex);
            String[] parts = lastLine.split(",");
            if (parts.length == 4) { // Pastikan format sesuai (username,password,waktu,status)
                // Dapatkan waktu login terakhir
                String lastLoginTime = parts[2];

                // Buat format waktu sesuai dengan yang dibutuhkan
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date lastDate = sdf.parse(lastLoginTime);
                Date currentDate = new Date();

                // Bandingkan untuk memilih waktu login yang paling mendekati waktu sekarang
                if (currentDate.getTime() - lastDate.getTime() <= 1000) {
                    // Ganti status menjadi "Bayar"
                    lines.set(lastIndex, String.format("%s,%s,%s,%s", username, parts[1], lastLoginTime, status));
                }
            }
        }

        // Tulis kembali semua data ke file
        Files.write(Paths.get(LOGIN_DATA_FILE), lines);
        System.out.println("Data login berhasil diperbarui ke file " + LOGIN_DATA_FILE);
    } catch (IOException e) {
        e.printStackTrace();
        System.err.println("Gagal memperbarui data login ke file " + LOGIN_DATA_FILE + ": " + e.getMessage());
    } catch (ParseException e) {
        e.printStackTrace();
        System.err.println("Gagal memformat waktu login dari file " + LOGIN_DATA_FILE + ": " + e.getMessage());
    }
}

    // Setter untuk lastLoggedInUser
    public void setLastLoggedInUser(String lastLoggedInUser) {
        this.lastLoggedInUser = lastLoggedInUser;
    }
}
