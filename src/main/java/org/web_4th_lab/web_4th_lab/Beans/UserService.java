package org.web_4th_lab.web_4th_lab.Beans;

import jakarta.ejb.Stateless;
import org.web_4th_lab.web_4th_lab.DAOServices.UserDAO;
import org.web_4th_lab.web_4th_lab.entities.User;



@Stateless
public class UserService {

    UserDAO userDAO = new UserDAO();

    public String registerUser(String username, String password) {
        if(userDAO.userExists(username))
            return "user already exists";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userDAO.saveUser(user);
        return "register successful";
    }

    public String authorizeUser(String username, String password){
        if(!userDAO.userExists(username))
            return "Invalid username";
        if(!userDAO.userPasswordMatches(username, password))
            return "Invalid password";
        return "Authorized";
    }

}
