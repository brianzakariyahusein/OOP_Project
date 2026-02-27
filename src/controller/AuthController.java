/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UserDAO;
import model.User;

/**
 *
 * @author Brian
 */
public class AuthController {
    private UserDAO userDAO;
    
    public AuthController (){
        userDAO = new UserDAO();
    }
    
    public User login (String username, String password){
        if (username.isEmpty() || password.isEmpty()){
            return null;
        }
        
        return userDAO.login(username, password);
    }
}
