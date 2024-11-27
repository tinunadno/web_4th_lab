package org.web_4th_lab.web_4th_lab.DAOServices;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.web_4th_lab.web_4th_lab.Utils.BackendLogger;
import org.web_4th_lab.web_4th_lab.entities.User;

public class UserDAO {
    public void saveUser(User user) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        }catch (Exception e) {
            if(transaction != null) transaction.rollback();
            System.out.println(e);
        }
    }

    public User getUserById(int id){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            return user;
        }
    }

    public void saveToken(int userId, String token) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, userId);
            if (user != null) {
                user.setToken(token);
                session.update(user);
            } else {
                System.out.println("failed find user");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println(e);
        }
    }

    public boolean validateAuthorizedUser(int id, String token) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            System.out.println(user);
            if (user != null) {
                return user.getToken().equals(token);
            }else{
                return false;
            }
        }catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public int getUserID(String name){
        User user = getUserByName(name);
        if(user != null)return user.getId();
        return -1;
    }

    public boolean userExists(String username) {
        return getUserByName(username) != null;
    }

    private User getUserByName(String name) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("from User where username = :name", User.class);
            query.setParameter("name", name);
            return query.uniqueResult();
        }
    }

    public boolean userPasswordMatches(String username, String password) {
        User user = getUserByName(username);
        return user.getPassword().equals(password);
    }

}
