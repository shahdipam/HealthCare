package com.example.healthcare.classes;

import java.io.Serializable;

public class Admin implements Serializable {

    String name;
    int adminID;
    String username, email, phone_number, password;
    String qualification;
    BMI_Diet_plan[] BMISchemes;
    SupervisedUser[] userList;
    String descrption;

    void setSchemes(){

    }

    SupervisedUser viewClient(){
        return new SupervisedUser();
    }

    BMI_Diet_plan createBMIScemes(){

        return new BMI_Diet_plan();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public BMI_Diet_plan[] getBMISchemes() {
        return BMISchemes;
    }

    public void setBMISchemes(BMI_Diet_plan[] BMISchemes) {
        this.BMISchemes = BMISchemes;
    }

    public SupervisedUser[] getUserList() {
        return userList;
    }

    public void setUserList(SupervisedUser[] userList) {
        this.userList = userList;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
