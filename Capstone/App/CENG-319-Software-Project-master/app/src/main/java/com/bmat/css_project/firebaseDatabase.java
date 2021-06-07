package com.bmat.css_project;

import com.bmat.css_project.ui.Model.Employee;
import com.bmat.css_project.ui.Model.Lock;
import com.bmat.css_project.ui.Model.Owner;
import com.bmat.css_project.ui.Model.Temperature;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class firebaseDatabase {
    private DatabaseReference mDatabase;
    public Lock lock;
    public Owner owner;
    public Employee employee;
    public Temperature temperature;

    public void createCompany(String name, String email, String companyName, String phone) {
        owner = new Owner();
        lock = new Lock();
        owner.setName(name);
        owner.setEmail(email);
        owner.setPhone(phone);
        owner.setCompany(companyName);
        lock.setAstate("open");
        lock.setState("closed");
        boolean admin = true;

        String adminCheck = Boolean.toString(admin);
        String userID = email + "-" + companyName + "=" + adminCheck;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(userID).build();
        assert user != null;
        user.updateProfile(profileUpdates);

        createEmployee(name, email, phone, admin, companyName);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(companyName).child("owner").setValue(owner);
        mDatabase.child(companyName).child("sensors").child("lock").setValue(lock);
    }

    public void createEmployee(String name, String email, String phone, Boolean admin, String companyName) {
        employee = new Employee();
        employee.setName(name);
        employee.setEmail(email);
        employee.setPhone(phone);
        employee.setAdmin(admin);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(companyName).child("employee").push().setValue(employee);
    }

    public void createTemp(String userID, String temp, String date) {
        int index = userID.indexOf("-");
        int end = userID.indexOf("=");
        String companyName = userID.substring(index + 1, end);
        String email = userID.substring(0, index);

        temperature = new Temperature();
        temperature.setEmail(email);
        temperature.setTemp(temp);
        temperature.setDate(date);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(companyName).child("temperature").push().setValue(temperature);
    }

    public String adminCheck(){
        String userID = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        int index = userID.indexOf("=");
        int end = userID.length();

        return userID.substring(index + 1, end);
    }
}
