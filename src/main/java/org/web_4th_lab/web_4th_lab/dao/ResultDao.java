package org.web_4th_lab.web_4th_lab.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.web_4th_lab.web_4th_lab.entities.Result;

import java.util.ArrayList;
import java.util.List;

public class ResultDao {

    public void saveResult(Result result) throws RuntimeException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.save(result);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null && transaction.getStatus().canRollback()) {
                    transaction.rollback();
                }
                throw new RuntimeException(e);
            }
        }
    }

    public List<Result> getResultsByUserId(long userId) throws RuntimeException{
        List<Result> results;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Result> query = session.createQuery("FROM Result r WHERE r.user.id = :userId", Result.class);
            query.setParameter("userId", userId);
            results = query.getResultList();
            return results;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteResultsByUserId(long userId) throws RuntimeException{
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Query query = session.createQuery("DELETE FROM Result r WHERE r.user.id = :userId");
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
            throw new RuntimeException(e);
        }
    }

}
