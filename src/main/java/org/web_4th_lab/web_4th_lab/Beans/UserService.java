package org.web_4th_lab.web_4th_lab.Beans;

import jakarta.ejb.Stateless;
import jakarta.transaction.Transactional;
import org.hibernate.Transaction;
import org.web_4th_lab.web_4th_lab.DTO.AuthenticationRequest;
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

    public AuthenticationResponse registerUser(AuthenticationRequest authenticationRequest) throws RuntimeException {
        if (userDAO.userExists(authenticationRequest.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        User user = new User(
                authenticationRequest.getUsername(),
                passwordHash.toSHA384(authenticationRequest.getPassword()),
                tokenGenerator.getNewToken()
        );
        long id = userDAO.saveUser(user);
        return new AuthenticationResponse(id, user.getToken());
    }

    public AuthenticationResponse authorizeUser(AuthenticationRequest authenticationRequest) throws RuntimeException{
        String hashedPassword = passwordHash.toSHA384(authenticationRequest.getPassword());
        long id = userDAO.getUserIdByUsernameAndPassword(authenticationRequest.getUsername(), hashedPassword);
        String token = tokenGenerator.getNewToken();
        userDAO.saveToken(id, token);
        return new AuthenticationResponse(id, token);
    }

    public boolean validateAuthorizedUser(long id, String token){
        return userDAO.validateAuthorizedUser(id, token);
    }

    public void deleteUserById(long id) throws RuntimeException{
        //this is kind'a bad, but it's the best way, but im too lazy to fix dao :D
        Transaction resultDeletionTransaction = resultDao.deleteResultsByUserId(id);
        try{
            userDAO.deleteUserById(id);
        } catch (RuntimeException e) {
            resultDeletionTransaction.rollback();
        }
    }

}
