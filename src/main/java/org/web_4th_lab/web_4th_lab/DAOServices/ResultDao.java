package org.web_4th_lab.web_4th_lab.DAOServices;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.web_4th_lab.web_4th_lab.Utils.BackendLogger;
import org.web_4th_lab.web_4th_lab.entities.Result;

import java.util.ArrayList;
import java.util.List;

public class ResultDao {

    public void saveResult(Result result) {
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
                e.printStackTrace();
            }
        }
    }
    public List<Result> getResultsByUserId(int userId) {
        List<Result> results = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Создаем запрос для получения всех результатов с нужным user_id
            Query<Result> query = session.createQuery("FROM Result r WHERE r.user.id = :userId", Result.class);
            query.setParameter("userId", userId);
            results = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}
