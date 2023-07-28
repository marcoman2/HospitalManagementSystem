/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalmanagementsystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 *
 * @author WINDOWS 10
 */
public class DoctorCardController implements Initializable {

    @FXML
    private Circle doctor_circle;

    @FXML
    private Label doctor_id;

    @FXML
    private Label doctor_fullName;

    @FXML
    private Label doctor_specialization;

    @FXML
    private Label doctor_email;

    private Image image;

    public void setData(DoctorData dData) {

        if (dData.getImage() != null) {
            image = new Image("File:" + dData.getImage(), 52, 52, false, true);
            doctor_circle.setFill(new ImagePattern(image));
        }

        doctor_id.setText(dData.getDoctorID());
        doctor_fullName.setText(dData.getFullName());
        doctor_specialization.setText(dData.getSpecialized());
        doctor_email.setText(dData.getEmail());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
