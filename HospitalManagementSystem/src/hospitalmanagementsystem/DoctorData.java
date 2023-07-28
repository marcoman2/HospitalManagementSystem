/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalmanagementsystem;

import java.sql.Date;

/**
 *
 * @author WINDOWS 10
 */
public class DoctorData {

    private Integer id;
    private String doctorID;
    private String password;
    private String fullName;
    private String email;
    private String gender;
    private Long mobileNumber;
    private String specialized;
    private String address;
    private String image;
    private Date date;
    private Date dateModify;
    private Date dateDelete;
    private String status;

    public DoctorData(Integer id, String doctorID, String password, String fullName,
            String email, String gender, Long mobileNumber, String specialized, String address,
            String image, Date date, Date dateModify, Date dateDelete, String status) {
        this.id = id;
        this.doctorID = doctorID;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.gender = gender;
        this.mobileNumber = mobileNumber;
        this.specialized = specialized;
        this.address = address;
        this.image = image;
        this.date = date;
        this.dateModify = dateModify;
        this.dateDelete = dateDelete;
        this.status = status;
    }

    public DoctorData(String doctorID, String fullName, String specialized, String status) {
        this.doctorID = doctorID;
        this.fullName = fullName;
        this.specialized = specialized;
        this.status = status;
    }

    public DoctorData(Integer id, String doctorID, String fullName, String specialized, String email, String image) {
        this.id = id;
        this.doctorID = doctorID;
        this.fullName = fullName;
        this.specialized = specialized;
        this.email = email;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public String getSpecialized() {
        return specialized;
    }

    public String getAddress() {
        return address;
    }

    public String getImage() {
        return image;
    }

    public Date getDate() {
        return date;
    }

    public Date getDateModify() {
        return dateModify;
    }

    public Date getDateDelete() {
        return dateDelete;
    }

    public String getStatus() {
        return status;
    }

}
