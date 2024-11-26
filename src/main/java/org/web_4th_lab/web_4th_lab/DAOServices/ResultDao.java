package org.web_4th_lab.web_4th_lab.DAOServices;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.web_4th_lab.web_4th_lab.entities.Result;

import java.util.ArrayList;
import java.util.List;

public class ResultDao {

    public void saveResult(Result result) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(result); // Сохраняем объект в таблицу
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
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
