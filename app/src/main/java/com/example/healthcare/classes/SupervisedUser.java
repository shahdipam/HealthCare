package com.example.healthcare.classes;

public class SupervisedUser extends User {
    int nutritionID;

    public SupervisedUser(String name, int age, int bmi) {
        super(name, age, bmi);
    }

    public SupervisedUser() {
        super();
    }
}
