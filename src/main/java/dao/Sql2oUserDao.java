package dao;

import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;


public class Sql2oUserDao implements UserDao {
    public Sql2oUserDao(){}

    @Override
    public void add(User user) {
        String sql = "INSERT INTO users (name,position,role,department) VALUES (:name,:position,:role,:department);";
        try (Connection con = DB.sql2o.open()) {
            int id = (int) con.createQuery(sql,true)
                    .bind(user)
                    .addParameter("department",user.getDepartment())
                    .executeUpdate()
                    .getKey();
            user.setId(id);
        } catch (Sql2oException ex){
            System.out.println("User not added: "+ex);
        }
    }



    @Override
    public User findById(int id) {
        String sql = "SELECT * from users WHERE id=:id;";
        try (Connection con = DB.sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(User.class);
        }
    }

    @Override
    public List<User> allUsers() {
        String sql = "SELECT * from users;";
        try (Connection con = DB.sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(User.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from users where id = :id;";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id",id)
                    .executeUpdate();
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE from users;";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        }
    }
}