package hospitalmanagementsystem;

import hospitalmanagementsystem.Data;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author WINDOWS 10
 */
public class CheckOutPatientController implements Initializable {

    @FXML
    private Label checkout_patientID;

    @FXML
    private Label checkout_name;

    @FXML
    private Label checkout_doctor;

    @FXML
    private DatePicker checkout_date;

    @FXML
    private ImageView checkout_imageView;

    @FXML
    private DatePicker checkout_checkout;

    @FXML
    private Label checkout_totalDays;

    @FXML
    private Label checkout_totalPrice;

    @FXML
    private Button checkout_payBtn;

    @FXML
    private Button checkout_countBtn;

    private Image image;

    private AlertMessage alert = new AlertMessage();

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    public void countBtn() {
        long countDays = 0;
        if (checkout_date.getValue() != null || checkout_checkout.getValue() != null) {
            countDays
                    = ChronoUnit.DAYS.between(checkout_date.getValue(), checkout_checkout.getValue());
        } else {
            alert.errorMessage("Something went wrong.");
        }
        // THE PRICE PER A DAY
        double price = 20.5;

        double totalprice = (price * countDays);

        checkout_totalDays.setText(String.valueOf(countDays));
        checkout_totalPrice.setText(String.valueOf(totalprice));

    }

    public void payBtn() {
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        if (checkout_checkout.getValue() == null
                || checkout_totalDays.getText().isEmpty()
                || checkout_totalPrice.getText().isEmpty()) {
            alert.errorMessage("Invalid");
        } else {
            if (alert.confirmationMessage("Are you sure you want to pay?")) {
                String sql = "INSERT INTO payment "
                        + "(patient_id, doctor, total_days, total_price, date, date_checkout, date_pay) "
                        + "VALUES(?,?,?,?,?,?,?)";

                connect = Database.connectDB();

                try {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, checkout_patientID.getText());
                    prepare.setString(2, checkout_doctor.getText());
                    prepare.setString(3, checkout_totalDays.getText());
                    prepare.setString(4, checkout_totalPrice.getText());
                    prepare.setString(5, String.valueOf(checkout_date.getValue()));
                    prepare.setString(6, String.valueOf(checkout_checkout.getValue()));
                    prepare.setString(7, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    String updateData = "UPDATE patient SET status_pay = ? WHERE patient_id = "
                            + checkout_patientID.getText();

                    prepare = connect.prepareStatement(updateData);
                    prepare.setString(1, "Paid");
                    prepare.executeUpdate();

                    alert.successMessage("Successful!");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert.errorMessage("Cancelled.");
            }

        }

    }

    public void displayFields() {

        checkout_patientID.setText(String.valueOf(Data.temp_PatientID));
        checkout_name.setText(Data.temp_name);
        checkout_doctor.setText(Data.temp_doctorID);
        checkout_date.setValue(LocalDate.parse(Data.temp_date));
        checkout_date.setDisable(true);

        image = new Image("File:" + Data.temp_path, 116, 132, false, true);
        checkout_imageView.setImage(image);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayFields();
    }

}
