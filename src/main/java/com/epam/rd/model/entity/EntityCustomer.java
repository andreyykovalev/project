package com.epam.rd.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Builder
public class EntityCustomer {
    @Getter
    @Setter
    Long id;
    String firstname;
    String lastname;
    String mail;

    public EntityCustomer(Long id, String firstname, String lastname, String mail, String password, double balance, List<Long> packages) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
        this.password = password;
        this.balance = balance;
        this.packages = packages;
    }

    String password;
    double balance;
    @Getter
    @Setter
    List<Long> packages;


    public EntityCustomer(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public EntityCustomer(String firstName, String lastName, String mail, String password, double balance) {
        this.firstname = firstName;
        this.lastname = lastName;
        this.mail = mail;
        this.password = password;
        this.balance = balance;
    }

    public String getFirstname() {
        return firstname;
    }


    public String getLastname() {
        return lastname;
    }


    public String getMail() {
        return mail;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
