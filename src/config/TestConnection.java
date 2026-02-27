/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Brian
 */

package config;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args){
        Connection connection = DatabaseConnection.getConnection();
        
        if (connection != null) {
            System.out.println("Success!");
        } else {
            System.out.println("FAILED!");
        }
    }
}
