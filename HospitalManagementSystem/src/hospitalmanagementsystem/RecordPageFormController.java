/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalmanagementsystem;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author WINDOWS 10
 */
public class RecordPageFormController implements Initializable {

    @FXML
    private AnchorPane recordpage_mainForm;

    @FXML
    private TextField recordpage_search;

    @FXML
    private TableView<PatientsData> recordpage_tableView;

    @FXML
    private TableColumn<PatientsData, String> recordpage_col_patientID;

    @FXML
    private TableColumn<PatientsData, String> recordpage_col_name;

    @FXML
    private TableColumn<PatientsData, String> recordpage_col_gender;

    @FXML
    private TableColumn<PatientsData, String> recordpage_col_mobileNumber;

    @FXML
    private TableColumn<PatientsData, String> recordpage_col_address;

    @FXML
    private TableColumn<PatientsData, String> recordpage_col_dateCreated;

    @FXML
    private TableColumn<PatientsData, String> recordpage_col_dateModiftied;

    @FXML
    private TableColumn<PatientsData, String> recordpage_col_dateDeleted;

    @FXML
    private TableColumn<PatientsData, String> recordpage_col_action;

//    DATABASE TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    AlertMessage alert = new AlertMessage();

    public ObservableList<PatientsData> getPatientRecordData() {

        ObservableList<PatientsData> listData = FXCollections.observableArrayList();
// RESTART RECORD PAGEFORM FXML IF YOU DIDNT SEE THE RECORDPAGEFORMCONTROLLER CLASS
        String selectData = "SELECT * FROM patient WHERE date_delete IS NULL AND doctor = '"
                + Data.doctor_id + "'";
        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            PatientsData pData;
//            PatientsData(Integer id, Integer patientID, String fullName, 
//            Long mobileNumber, String address, Date date
//            , Date dateModify, Date dateDelete)
            while (result.next()) {
                pData = new PatientsData(result.getInt("id"), result.getInt("patient_id"),
                        result.getString("full_name"), result.getString("gender"), result.getLong("mobile_number"),
                        result.getString("address"), result.getString("status"), result.getDate("date"),
                        result.getDate("date_modify"), result.getDate("date_delete"));
                listData.add(pData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<PatientsData> patientRecordData;

    public void displayPatientsData() {
        patientRecordData = getPatientRecordData();

        recordpage_col_patientID.setCellValueFactory(new PropertyValueFactory<>("patientID"));
        recordpage_col_name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        recordpage_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        recordpage_col_mobileNumber.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        recordpage_col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        recordpage_col_dateCreated.setCellValueFactory(new PropertyValueFactory<>("date"));
        recordpage_col_dateModiftied.setCellValueFactory(new PropertyValueFactory<>("dateModify"));
        recordpage_col_dateDeleted.setCellValueFactory(new PropertyValueFactory<>("dateDelete"));

        recordpage_tableView.setItems(patientRecordData);

    }

    public void actionButtons() {

        connect = Database.connectDB();
        patientRecordData = getPatientRecordData();

        Callback<TableColumn<PatientsData, String>, TableCell<PatientsData, String>> cellFactory = (TableColumn<PatientsData, String> param) -> {
            final TableCell<PatientsData, String> cell = new TableCell<PatientsData, String>() {
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Button editButton = new Button("Edit");
                        Button removeButton = new Button("Delete");

                        editButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #a413a1, #64308e);\n"
                                + "    -fx-cursor: hand;\n"
                                + "    -fx-text-fill: #fff;\n"
                                + "    -fx-font-size: 14px;\n"
                                + "    -fx-font-family: Arial;");

                        removeButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #a413a1, #64308e);\n"
                                + "    -fx-cursor: hand;\n"
                                + "    -fx-text-fill: #fff;\n"
                                + "    -fx-font-size: 14px;\n"
                                + "    -fx-font-family: Arial;");

                        editButton.setOnAction((ActionEvent event) -> {
                            try {

                                PatientsData pData = recordpage_tableView.getSelectionModel().getSelectedItem();
                                int num = recordpage_tableView.getSelectionModel().getSelectedIndex();

                                if ((num - 1) < -1) {
                                    alert.errorMessage("Please select item first");
                                    return;
                                }

                                Data.temp_PatientID = pData.getPatientID();
                                Data.temp_name = pData.getFullName();
                                Data.temp_gender = pData.getGender();
                                Data.temp_number = pData.getMobileNumber();
                                Data.temp_address = pData.getAddress();
                                Data.temp_status = pData.getStatus();
                                // NOW LETS CREATE FXML FOR EDIT PATIENT FORM
                                Parent root = FXMLLoader.load(getClass().getResource("EditPatientForm.fxml"));
                                Stage stage = new Stage();

                                stage.setScene(new Scene(root));
                                stage.show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        removeButton.setOnAction((ActionEvent event) -> {
                            PatientsData pData = recordpage_tableView.getSelectionModel().getSelectedItem();
                            int num = recordpage_tableView.getSelectionModel().getSelectedIndex();

                            if ((num - 1) < -1) {
                                alert.errorMessage("Please select item first");
                                return;
                            }

                            String deleteData = "UPDATE patient SET date_delete = ? WHERE patient_id = "
                                    + pData.getPatientID();

                            try {
                                if (alert.confirmationMessage("Are you sure you want to delete Patient ID: " + pData.getPatientID() + "?")) {
                                    prepare = connect.prepareStatement(deleteData);
                                    Date date = new Date();
                                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                                    prepare.setString(1, String.valueOf(sqlDate));
                                    prepare.executeUpdate();

                                    alert.successMessage("Deleted Successfully!");

                                    displayPatientsData();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        HBox manageBtn = new HBox(editButton, removeButton);
                        manageBtn.setAlignment(Pos.CENTER);
                        manageBtn.setSpacing(5);
                        setGraphic(manageBtn);
                        setText(null);
                    }
                }
            };
            return cell;
        };

        recordpage_col_action.setCellFactory(cellFactory);
        recordpage_tableView.setItems(patientRecordData);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TO DISPLAY THE PATIENT'S DATA ONCE THE DOCTOR CLICKED THE RECORD BUTTON
        displayPatientsData();

        actionButtons();

    }

}
