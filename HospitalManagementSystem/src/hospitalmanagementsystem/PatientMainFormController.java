/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalmanagementsystem;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author WINDOWS 10
 */
public class PatientMainFormController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private Circle top_profile;

    @FXML
    private Label top_username;

    @FXML
    private Label date_time;

    @FXML
    private Label current_form;

    @FXML
    private Button logout_btn;

    @FXML
    private Label nav_adminID;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button doctors_btn;

    @FXML
    private Button appointments_btn;

    @FXML
    private Button profile_btn;

    @FXML
    private AnchorPane home_form;

    @FXML
    private TableView<PatientsData> home_patient_tableView;

    @FXML
    private TableColumn<PatientsData, String> home_patient_col_description;

    @FXML
    private TableColumn<PatientsData, String> home_patient_col_diagnosis;

    @FXML
    private TableColumn<PatientsData, String> home_patient_col_treatment;

    @FXML
    private TableColumn<PatientsData, String> home_patient_col_dateIn;

    @FXML
    private TableColumn<PatientsData, String> home_patient_col_dateDischarge;

    @FXML
    private Circle home_doctor_circle;

    @FXML
    private Label home_doctor_name;

    @FXML
    private Label home_doctor_specialization;

    @FXML
    private Label home_doctor_email;

    @FXML
    private Label home_doctor_mobileNumber;

    @FXML
    private TableView<AppointmentData> home_appointment_tableView;

    @FXML
    private TableColumn<AppointmentData, String> home_appointment_col_appointmenID;

    @FXML
    private TableColumn<AppointmentData, String> home_appointment_col_description;

    @FXML
    private TableColumn<AppointmentData, String> home_appointment_col_diagnosis;

    @FXML
    private TableColumn<AppointmentData, String> home_appointment_col_treatment;

    @FXML
    private TableColumn<AppointmentData, String> home_appointment_col_doctor;

    @FXML
    private TableColumn<AppointmentData, String> home_appointment_col_schedule;

    @FXML
    private AnchorPane doctors_form;

    @FXML
    private ScrollPane doctors_scrollPane;

    @FXML
    private GridPane doctors_gridPane;

    @FXML
    private AnchorPane appointments_form;

    @FXML
    private Label appointment_ad_name;

    @FXML
    private Label appointment_ad_mobileNumber;

    @FXML
    private Label appointment_ad_gender;

    @FXML
    private Label appointment_ad_address;

    @FXML
    private Label appointment_ad_description;

    @FXML
    private Label appointment_ad_doctorName;

    @FXML
    private Label appointment_ad_specialization;

    @FXML
    private Label appointment_ad_schedule;

    @FXML
    private Button appointment_d_confirmBtn;

    @FXML
    private TextArea appointment_d_description;

    @FXML
    private ComboBox<String> appointment_d_doctor;

    @FXML
    private DatePicker appointment_d_schedule;

    @FXML
    private Button appointment_d_clearBtn;

    @FXML
    private AnchorPane profile_form;

    @FXML
    private Circle profile_circle;

    @FXML
    private Button profile_importBtn;

    @FXML
    private Label profile_label_patientID;

    @FXML
    private Label profile_label_name;

    @FXML
    private Label profile_label_mobileNumber;

    @FXML
    private Label profile_label_dateCreated;

    @FXML
    private TextField profile_patientID;

    @FXML
    private TextField profile_name;

    @FXML
    private TextField profile_mobileNumber;

    @FXML
    private ComboBox<String> profile_status;

    @FXML
    private Button profile_updateBtn;

    @FXML
    private TextField profile_password;

    @FXML
    private TextArea profile_address;

    private AlertMessage alert = new AlertMessage();

    private Image image;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    public ObservableList<PatientsData> homePatientGetData() {

        ObservableList<PatientsData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM patient WHERE date_delete IS NULL AND patient_id = " + Data.patient_id;
        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            PatientsData pData;
            while (result.next()) {
//                PatientsData(Integer id, Integer patientID, String description
//            , String diagnosis, String treatment, Date date)
                pData = new PatientsData(result.getInt("id"),
                        result.getInt("patient_id"),
                        result.getString("description"),
                        result.getString("diagnosis"),
                        result.getString("treatment"), result.getDate("date"));

                listData.add(pData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public ObservableList<PatientsData> homePatientListData;

    public void homePatientDisplayData() {
        homePatientListData = homePatientGetData();

        home_patient_col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        home_patient_col_diagnosis.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
        home_patient_col_treatment.setCellValueFactory(new PropertyValueFactory<>("treatment"));
        home_patient_col_dateIn.setCellValueFactory(new PropertyValueFactory<>("date"));

        home_patient_tableView.setItems(homePatientListData);
    }

    public ObservableList<AppointmentData> homeAppointmentGetData() {

        ObservableList<AppointmentData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM appointment WHERE date_delete IS NULL AND patient_id = "
                + Data.patient_id;

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            AppointmentData aData;
            while (result.next()) {
//                AppointmentData(Integer appointmentID, String description,
//            String diagnosis, String treatment, String doctorID, Date schedule)
                aData = new AppointmentData(result.getInt("appointment_id"),
                        result.getString("description"),
                        result.getString("diagnosis"), result.getString("treatment"),
                        result.getString("doctor"), result.getDate("schedule"));

                listData.add(aData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public ObservableList<AppointmentData> homeAppointmentListData;

    public void homeAppointmentDisplayData() {
        homeAppointmentListData = homeAppointmentGetData();

        home_appointment_col_appointmenID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        home_appointment_col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        home_appointment_col_diagnosis.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
        home_appointment_col_treatment.setCellValueFactory(new PropertyValueFactory<>("treatment"));
        home_appointment_col_doctor.setCellValueFactory(new PropertyValueFactory<>("doctor"));
        home_appointment_col_schedule.setCellValueFactory(new PropertyValueFactory<>("schedule"));

        home_appointment_tableView.setItems(homeAppointmentListData);
    }

    public void homeDoctorInfoDisplay() {

        String sql = "SELECT * FROM patient WHERE patient_id = " + Data.patient_id;

        connect = Database.connectDB();

        String tempDoctorID = "";
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                tempDoctorID = result.getString("doctor");
            }

            String checkDoctor = "SELECT * FROM doctor WHERE doctor_id = '"
                    + tempDoctorID + "'";

            statement = connect.createStatement();
            result = statement.executeQuery(checkDoctor);

            if (result.next()) {
                home_doctor_name.setText(result.getString("full_name"));
                home_doctor_specialization.setText(result.getString("specialized"));
                home_doctor_email.setText(result.getString("email"));
                home_doctor_mobileNumber.setText(result.getString("mobile_number"));

                String path = result.getString("image");

                if (path != null) {
                    path = path.replace("\\", "\\\\");

                    image = new Image("File:" + path, 138, 82, false, true);
                    home_doctor_circle.setFill(new ImagePattern(image));
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private ObservableList<DoctorData> doctorList = FXCollections.observableArrayList();

    public ObservableList<DoctorData> doctorGetData() {

        String sql = "SELECT * FROM doctor WHERE status = 'Active'";

        connect = Database.connectDB();

        ObservableList<DoctorData> listData = FXCollections.observableArrayList();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            DoctorData dData;

            while (result.next()) {
//                DoctorData(Integer id, String doctorID, String fullName, String specialized, String email)
                dData = new DoctorData(result.getInt("id"),
                        result.getString("doctor_id"),
                        result.getString("full_name"),
                        result.getString("specialized"),
                        result.getString("email"),
                        result.getString("image"));

                listData.add(dData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public void doctorShowCard() {
        doctorList.clear();
        doctorList.addAll(doctorGetData());

        doctors_gridPane.getChildren().clear();
        doctors_gridPane.getColumnConstraints().clear();
        doctors_gridPane.getRowConstraints().clear();

        int row = 0, column = 0;

        for (int q = 0; q < doctorList.size(); q++) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("DoctorCard.fxml"));
                StackPane stack = loader.load();

                DoctorCardController dController = loader.getController();
                dController.setData(doctorList.get(q));

                if (column == 3) {
                    column = 0;
                    row++;
                }

                doctors_gridPane.add(stack, column++, row);

                GridPane.setMargin(stack, new Insets(15));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void appointmentAppointmentInfoDisplay() {

        String sql = "SELECT * FROM patient WHERE patient_id = " + Data.patient_id;

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                appointment_ad_name.setText(result.getString("full_name"));
                appointment_ad_mobileNumber.setText(result.getString("mobile_number"));
                appointment_ad_gender.setText(result.getString("gender"));
                appointment_ad_address.setText(result.getString("address"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void appointmentConfirmBtn() {

        if (appointment_d_description.getText().isEmpty()
                || appointment_d_schedule.getValue() == null
                || appointment_d_doctor.getSelectionModel().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
        } else {

            appointment_ad_description.setText(appointment_d_description.getText());
            appointment_ad_doctorName.setText(appointment_d_doctor.getSelectionModel().getSelectedItem());

            String sql = "SELECT * FROM doctor WHERE doctor_id = '"
                    + appointment_d_doctor.getSelectionModel().getSelectedItem() + "'";

            connect = Database.connectDB();
            String tempSpecialized = "";
            try {
                prepare = connect.prepareStatement(sql);
                result = prepare.executeQuery();

                if (result.next()) {
                    tempSpecialized = result.getString("specialized");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            appointment_ad_specialization.setText(tempSpecialized);
            appointment_ad_schedule.setText(String.valueOf(appointment_d_schedule.getValue()));
        }

    }

    public void appointmentDoctor() {
        String sql = "SELECT * FROM doctor WHERE delete_date IS NULL";
        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();
            while (result.next()) {
                listData.add(result.getString("doctor_id"));
            }

            appointment_d_doctor.setItems(listData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void appointmentClearBtn() {
        appointment_d_doctor.getSelectionModel().clearSelection();
        appointment_d_description.clear();
        appointment_d_schedule.setValue(null);

        appointment_ad_description.setText("");
        appointment_ad_doctorName.setText("");
        appointment_ad_specialization.setText("");
        appointment_ad_schedule.setText("");
    }

    public void appointmentBookBtn() {
        connect = Database.connectDB();

        if (appointment_ad_description.getText().isEmpty()
                || appointment_d_doctor.getSelectionModel().isEmpty()
                || appointment_ad_specialization.getText().isEmpty()
                || appointment_ad_schedule.getText().isEmpty()) {
            alert.errorMessage("Invalid");
        } else {
            String selectData = "SELECT MAX(appointment_id) FROM appointment";

            int tempAppID = 0;

            try {
                statement = connect.createStatement();
                result = statement.executeQuery(selectData);

                if (result.next()) {
                    tempAppID = result.getInt("MAX(appointment_id)") + 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            String insertData = "INSERT INTO appointment (appointment_id, patient_id, name, gender"
                    + ", description, mobile_number, address, date"
                    + ", doctor, specialized, schedule, status) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            try {
                if (alert.confirmationMessage("Are you sure you want to book?")) {
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, String.valueOf(tempAppID));
                    prepare.setString(2, String.valueOf(Data.patient_id));
                    prepare.setString(3, appointment_ad_name.getText());
                    prepare.setString(4, appointment_ad_gender.getText());
                    prepare.setString(5, appointment_ad_description.getText());
                    prepare.setString(6, appointment_ad_mobileNumber.getText());
                    prepare.setString(7, appointment_ad_address.getText());
                    prepare.setString(8, String.valueOf(appointment_d_schedule.getValue()));
                    prepare.setString(9, appointment_d_doctor.getSelectionModel().getSelectedItem());
                    prepare.setString(10, appointment_ad_specialization.getText());
                    prepare.setString(11, appointment_ad_schedule.getText());
                    prepare.setString(12, "Active");

                    prepare.executeUpdate();

                    alert.successMessage("Successful !");

                    appointmentClearBtn();
                } else {
                    alert.errorMessage("Cancelled.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void profileDisplayFields() {

        String sql = "SELECT * FROM patient WHERE patient_id = " + Data.patient_id;
        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                profile_patientID.setText(result.getString("patient_id"));
                profile_name.setText(result.getString("full_name"));
                profile_mobileNumber.setText(result.getString("mobile_number"));
                profile_status.getSelectionModel().select(result.getString("gender"));
                profile_password.setText(result.getString("password"));
                profile_address.setText(result.getString("address"));

                if (result.getString("image") != null) {
                    String imagePath = "File:" + result.getString("image");
                    image = new Image(imagePath, 137, 95, false, true);
                    profile_circle.setFill(new ImagePattern(image));
                }
                profileDisplayLabels();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void profileDisplayLabels() {
        String sql = "SELECT * FROM patient WHERE patient_id = " + Data.patient_id;
        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                profile_label_patientID.setText(result.getString("patient_id"));
                profile_label_name.setText(result.getString("full_name"));
                profile_label_mobileNumber.setText(result.getString("mobile_number"));
                profile_label_dateCreated.setText(result.getString("date"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void profileDisplayImages() {

        String sql = "SELECT * FROM patient WHERE patient_id = " + Data.patient_id;
        connect = Database.connectDB();

        String tempPath1 = "";
        String tempPath2 = "";

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                tempPath1 = "File:" + result.getString("image");
                tempPath2 = "File:" + result.getString("image");

                if (result.getString("image") != null || "".equals(result.getString("image"))) {
                    image = new Image(tempPath1, 137, 95, false, true);
                    profile_circle.setFill(new ImagePattern(image));
                    image = new Image(tempPath2, 1012, 22, false, true);
                    top_profile.setFill(new ImagePattern(image));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void profileUpdateBtn() {
        connect = Database.connectDB();

        if (profile_patientID.getText().isEmpty()
                || profile_name.getText().isEmpty()
                || profile_mobileNumber.getText().isEmpty()
                || profile_status.getSelectionModel().isEmpty()
                || profile_password.getText().isEmpty()
                || profile_address.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
        } else {
            if (alert.confirmationMessage("Are you sure you want to Update your Profile?")) {
                if (Data.path == null || "".equals(Data.path)) {
                    String updateData = "UPDATE patient SET "
                            + "full_name = '" + profile_name.getText() + "', mobile_number = '"
                            + profile_mobileNumber.getText() + "', gender = '"
                            + profile_status.getSelectionModel().getSelectedItem() + "', password = '"
                            + profile_password.getText() + "', address = '"
                            + profile_address.getText() + "' WHERE patient_id = " + Data.patient_id;

                    try {
                        prepare = connect.prepareStatement(updateData);
                        prepare.executeUpdate();

                        alert.successMessage("Updated Successfully");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    String path = Data.path;
                    path = path.replace("\\", "\\\\");

                    Path transfer = Paths.get(path);

                    Path copy = Paths.get("C:\\Users\\WINDOWS 10\\Documents\\NetBeansProjects\\HospitalManagementSystem\\src\\Directory\\"
                            + Data.patient_id + ".jpg");

                    String copyPath = copy.toAbsolutePath().toString();
                    copyPath = copyPath.replace("\\", "\\\\");

                    String updateData = "UPDATE patient SET "
                            + "full_name = '" + profile_name.getText() + "', mobile_number = '"
                            + profile_mobileNumber.getText() + "', gender = '"
                            + profile_status.getSelectionModel().getSelectedItem() + "', password = '"
                            + profile_password.getText() + "', address = '"
                            + profile_address.getText() + "', image = '"
                            + copyPath + "' WHERE patient_id = " + Data.patient_id;

                    try {
                        prepare = connect.prepareStatement(updateData);
                        prepare.executeUpdate();

                        Files.copy(transfer, copy, StandardCopyOption.REPLACE_EXISTING);

                        alert.successMessage("Updated successfully!");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                alert.errorMessage("Cancelled.");
            }

        }
        profileDisplayImages();
    }

    public void profileImportBtn() {

        FileChooser open = new FileChooser();
        open.getExtensionFilters().add(new ExtensionFilter("Open Image", "*jpg", "*jpeg", "*png"));

        File file = open.showOpenDialog(profile_importBtn.getScene().getWindow());

        if (file != null) {
            Data.path = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 137, 95, false, true);
            profile_circle.setFill(new ImagePattern(image));
        }

    }

    public void profileGenderList() {

        List<String> listG = new ArrayList<>();

        for (String data : Data.gender) {
            listG.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listG);
        profile_status.setItems(listData);

    }

    @FXML
    void logoutBtn(ActionEvent event) {

        try {
            if (alert.confirmationMessage("Are you sure you want to logout?")) {
                Parent root = FXMLLoader.load(getClass().getResource("PatientPage.fxml"));
                Stage stage = new Stage();

                stage.setScene(new Scene(root));
                stage.show();

                logout_btn.getScene().getWindow().hide();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void profileInsertImage(ActionEvent event) {

    }

    @FXML
    void switchForm(ActionEvent event) {

        if (event.getSource() == dashboard_btn) {
            home_form.setVisible(true);
            doctors_form.setVisible(false);
            appointments_form.setVisible(false);
            profile_form.setVisible(false);
        } else if (event.getSource() == doctors_btn) {
            home_form.setVisible(false);
            doctors_form.setVisible(true);
            appointments_form.setVisible(false);
            profile_form.setVisible(false);
        } else if (event.getSource() == appointments_btn) {
            home_form.setVisible(false);
            doctors_form.setVisible(false);
            appointments_form.setVisible(true);
            profile_form.setVisible(false);
        } else if (event.getSource() == profile_btn) {
            home_form.setVisible(false);
            doctors_form.setVisible(false);
            appointments_form.setVisible(false);
            profile_form.setVisible(true);
        }

    }

    public void displayPatientID(){
        nav_adminID.setText(String.valueOf(Data.patient_id));
    }
    
    public void displayPatient(){
        String sql = "SELECT * FROM patient WHERE patient_id = " + Data.patient_id;
        connect = Database.connectDB();
        
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            if(result.next()){
                top_username.setText(result.getString("full_name"));
            }
        }catch(Exception e){e.printStackTrace();}
    }
    
    public void runTime() {

        new Thread() {

            public void run() {
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
                while (true) {
                    try {

                        Thread.sleep(1000); // 1000 ms = 1s

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Platform.runLater(() -> {
                        date_time.setText(format.format(new Date()));
                    });
                }
            }
        }.start();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        runTime();
        displayPatientID();
        displayPatient();

        homePatientDisplayData();
        homeAppointmentDisplayData();
        homeDoctorInfoDisplay();

        doctorShowCard();

        appointmentAppointmentInfoDisplay();
        appointmentDoctor();

        profileDisplayFields();
        profileGenderList();
        profileDisplayImages();
    }

}
