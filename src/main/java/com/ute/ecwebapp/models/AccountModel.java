package com.ute.ecwebapp.models;

import com.ute.ecwebapp.beans.User;
import com.ute.ecwebapp.utils.DbUtils;
import org.sql2o.Connection;

import java.util.List;

public class AccountModel {
        public static void add(User c) {
            String insertSql ="INSERT INTO users(username, password, name, email, address, permission) VALUES (:username,:password,:name,:email,:address,:permission)" ;
            try (Connection con = DbUtils.getConnection()) {
                con.createQuery(insertSql)
                        .addParameter("username",c.getUsername())
                        .addParameter("password", c.getPassword())
                        .addParameter("name", c.getName())
                        .addParameter("email", c.getEmail())
                        .addParameter("address",c.getAddress())
                        .addParameter("permission", c.getPermission())
                        .executeUpdate();
            }
        }
    public static User findByusername(String username) {
         String query = "select * from users where username = :username";
        try (Connection con = DbUtils.getConnection()) {
            List<User> list = con.createQuery(query)
                    .addParameter("username", username)
                    .executeAndFetch(User.class);
            if (list.size() == 0) {
                return null;
            }
            return list.get(0);
        }
    }
//    public static User checkUser(String sdt, String pw){
//         String query = "select * from user where sdt = :sdt and password = :pw";
//        try (Connection con = DbUtils.getConnection()) {
//            List<User> list = con.createQuery(query)
//                    .addParameter("pw", pw)
//                    .addParameter("sdt", sdt)
//                    .executeAndFetch(User.class);
//            if (list.size() == 0) {
//                return null;
//            }
//            return list.get(0);
//        }
   // }
}
