package org.web_4th_lab.web_4th_lab.Beans;

import jakarta.ejb.Stateless;
import org.hibernate.Transaction;
import org.web_4th_lab.web_4th_lab.DTO.AuthenticationResponse;
import org.web_4th_lab.web_4th_lab.dao.ResultDao;
import org.web_4th_lab.web_4th_lab.dao.UserDAO;
import org.web_4th_lab.web_4th_lab.Utils.PasswordHash;
import org.web_4th_lab.web_4th_lab.Utils.TokenGenerator;
import org.web_4th_lab.web_4th_lab.entities.User;



@Stateless
public class UserService {

    UserDAO userDAO = new UserDAO();
    ResultDao resultDao = new ResultDao();
    private final TokenGenerator tokenGenerator = new TokenGenerator();
    private final PasswordHash passwordHash = new PasswordHash();

    public AuthenticationResponse registerUser(String username, String password) throws IllegalArgumentException{
        if(userDAO.userExists(username)){
            throw new IllegalArgumentException("Username already exists");
        }
        User user = new User();
        user.setUsername(username);
        String hashedPassword = passwordHash.toSHA384(password);
        user.setPassword(hashedPassword);
        String token = tokenGenerator.getNewToken();
        user.setToken(token);
        userDAO.saveUser(user);
        long id = userDAO.getUserID(username);
        return new AuthenticationResponse(id, tokenGenerator.getNewToken());
    }

    public AuthenticationResponse authorizeUser(String username, String password) throws IllegalArgumentException{
        if(!userDAO.userExists(username)){
            throw new IllegalArgumentException("user does not exist");
        }
        String hashedPassword = passwordHash.toSHA384(password);
        if(!userDAO.userPasswordMatches(username, hashedPassword)){
            throw new IllegalArgumentException("user password does not match");
        }
        int id = userDAO.getUserID(username);
        String token = tokenGenerator.getNewToken();
        userDAO.saveToken(id, token);
        return new AuthenticationResponse(id, token);
    }

    public boolean validateAuthorizedUser(long id, String token) throws IllegalArgumentException{
        return userDAO.validateAuthorizedUser(id, token);
    }

    public void deleteUserById(long id) throws RuntimeException{
        //this is kind'a bad, but it's the best way, when I wouldn't overlap dao's
        try {
            Transaction userDeletionTransaction = userDAO.deleteUserById(id);
            try{
                resultDao.deleteResultsByUserId(id);
            } catch (RuntimeException e) {
                userDeletionTransaction.rollback();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
