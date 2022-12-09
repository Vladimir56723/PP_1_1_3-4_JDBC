package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {


    public UserDaoHibernateImpl() {


    }


    @Override
    public void createUsersTable() {
        String rec = "CREATE TABLE IF NOT EXISTS mytable\n" +
                "                (id int NOT NULL AUTO_INCREMENT,\n" +
                "                name VARCHAR(20), \n" +
                "                lastName VARCHAR(20),\n" +
                "                age tinyint unsigned," +
                "PRIMARY KEY (id));";
        Transaction transaction1 = null;
        try (
                Session session = Util.getSessionFactory().openSession()) {

            transaction1 = session.beginTransaction();

            session.createSQLQuery(rec).executeUpdate();

            transaction1.commit();
        } catch (Exception e) {
            if (transaction1 != null) {
                transaction1.rollback();
            }
            e.printStackTrace();
        }


    }

    @Override
    public void dropUsersTable() {
        String rec = "DROP TABLE IF EXISTS mytable";
        Transaction transaction1 = null;
        try (
                Session session = Util.getSessionFactory().openSession()) {

            transaction1 = session.beginTransaction();

            session.createSQLQuery(rec).executeUpdate();
            transaction1.commit();
        } catch (Exception e) {
            if (transaction1 != null) {
                transaction1.rollback();
            }

            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction1 = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction1 = session.beginTransaction();

            session.save(new User(name, lastName, age));

            transaction1.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e) {
            if (transaction1 != null) {
                transaction1.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction1 = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction1 = session.beginTransaction();

            User user = session.get(User.class, id);
            session.delete(user);

            transaction1.commit();
        } catch (Exception e) {
            if (transaction1 != null) {
                transaction1.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            users = session.createQuery("from User", User.class).getResultList();
            transaction.commit();
            users.forEach(System.out::println);

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction1 = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction1 = session.beginTransaction();

            session.createQuery("DELETE FROM User").executeUpdate();
            transaction1.commit();

        } catch (Exception e) {
            if (transaction1 != null) {
                transaction1.rollback();
            }
            e.printStackTrace();
        }

    }
}

