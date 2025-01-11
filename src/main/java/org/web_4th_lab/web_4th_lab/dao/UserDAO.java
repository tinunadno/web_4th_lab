package org.web_4th_lab.web_4th_lab.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.web_4th_lab.web_4th_lab.entities.User;

public class UserDAO {
    public long saveUser(User user) throws RuntimeException {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Long userId = (Long) session.save(user);
            transaction.commit();
            return userId;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    public long getUserIdByUsernameAndPassword(String username, String password) throws RuntimeException {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User user = session.createQuery("FROM User WHERE username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();

            if (user == null) {
                throw new RuntimeException("User not found");
            }

            if (!user.getPassword().equals(password)) {
                throw new RuntimeException("Invalid password");
            }

            Long userId = user.getId();
            transaction.commit();
            return userId;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public User getUserById(long id) throws RuntimeException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            return user;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveToken(long userId, String token) throws RuntimeException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, userId);
            if (user != null) {
                user.setToken(token);
                session.update(user);
                session.flush();
            } else {
                throw new RuntimeException("user not found");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    public boolean validateAuthorizedUser(long id, String token) throws RuntimeException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            System.out.println(user);
            if (user != null) {
                return user.getToken().equals(token);
            }else{
                return false;
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean userExists(String username) throws RuntimeException{
        return getUserByName(username) != null;
    }

    private User getUserByName(String name) throws RuntimeException {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("from User where username = :name", User.class);
            query.setParameter("name", name);
            return query.uniqueResult();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUserById(long userId) throws RuntimeException{
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                Query query = session.createQuery("DELETE FROM User u WHERE u.id = :userId");
                query.setParameter("userId", userId);

                int deletedCount = query.executeUpdate();

                transaction.commit();
                System.out.println("Deleted " + deletedCount + " results for user ID: " + userId);
            } catch (Exception e) {
                if (transaction != null && transaction.getStatus().canRollback()) {
                    transaction.rollback();
                }
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException("something went wrong on user deletion " + e.getMessage());
        }
    }

}
