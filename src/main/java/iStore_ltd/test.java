package iStore_ltd;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.*;

import java.util.Properties;

public class test {
    public static void main(String[] args) {
        String str = "eeEee!";
        if(str.matches("^.*[^a-zA-Z0-9 ].*$")){
            System.out.println("good");
        }

    }
}
