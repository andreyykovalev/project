package com.epam.rd.service;

import com.epam.rd.bean.User;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.Statement;
import java.util.List;


public class DaoUserTest {
    Connection conn = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
    DaoUser sut = new DaoUser(conn);

    public DaoUserTest() throws Exception {
    }

  /*  @Test
    public void selectQuery() throws Exception {
        Statement st = conn.createStatement();
        st.execute("create table customers(customer_id integer, customer_name varchar(10))");
        st.execute("insert into customers values (1, 'Thomas')");
        st.execute("insert into customers values (2, 'Dima')");
        st.execute("insert into customers values (3, 'Mark')");
        Customer customer = new Customer(4, "Din");
        sut.insertQuery(customer);
        Assert.assertEquals("4 Din", sut.selectQuery(4).toString());
        st.execute("DROP TABLE customers");
        st.close();
        conn.close();
    }*/

    @Test
    public void insertQuery() throws Exception {
        Statement st = conn.createStatement();
        st.execute("drop table if exists users");
        st.execute("create table users(user_id integer, user_firstname varchar(100), user_lastname varchar(100), user_email varchar(100), user_password varchar(100))");

        User user = new User("bruce","lee","brucelee@gmail.com","kung-fu");
        sut.addUser(user);
        Assert.assertEquals("0 bruce lee brucelee@gmail.com kung-fu", sut.listAllUsers().get(0).toString());

        st.close();
    }

    /*@Test
    public void deleteQuery() throws Exception {
        Statement st = conn.createStatement();
        st.execute("create table customers(customer_id integer, customer_name varchar(10))");
        st.execute("insert into customers values (1, 'Thomas')");
        st.execute("insert into customers values (2, 'Tim')");
        Customer customer = new Customer(1, "Thomas");
        sut.deleteQuery(customer);
        Assert.assertEquals("2 Tim", sut.listAllCustomers().get(0).toString());
        st.execute("DROP TABLE customers");
        st.close();
        conn.close();
    }*/

    @Test
    public void ListAllCustomers() throws Exception {
        List<User> list = null;
        Statement st = conn.createStatement();
        st.execute("drop table if exists users");
        st.execute("create table users(user_id integer, user_firstname varchar(100), user_lastname varchar(100), user_email varchar(100), user_password varchar(100))");

        User user = new User("bruce","lee","brucelee@gmail.com","kung-fu");
        sut.addUser(user);
        list = sut.listAllUsers();

        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() == 1);
        Assert.assertEquals("0 bruce lee brucelee@gmail.com kung-fu", sut.listAllUsers().get(0).toString());
        st.close();
    }
}