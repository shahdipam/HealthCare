package com.example.healthcare.classes;

public class User {
    String name;
    int userID, age;
    float height, weight, BMI;
    String username, email, phone_number;

    int todays_steps;
    User[] friends;

    public User(String name, int age, int bmi) {
    this.name = name;
    this.age = age;
    this.BMI = bmi;
    }

    public User() {

    }

    public float cal_BMI(){

        float bmi = weight/(height*height);

        return bmi;
    }

    void stepIncrementer(){

    }

    void resetPassword(){

    }

    void resetSteps() {

    }

    void addFriends(){

    }

    void removeFriends(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getBMI() {
        return BMI;
    }

    public void setBMI(float BMI) {
        this.BMI = BMI;
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

    public int getTodays_steps() {
        return todays_steps;
    }

    public void setTodays_steps(int todays_steps) {
        this.todays_steps = todays_steps;
    }

    public User[] getFriends() {
        return friends;
    }

    public void setFriends(User[] friends) {
        this.friends = friends;
    }
}
