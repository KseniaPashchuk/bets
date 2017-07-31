package com.epam.bets;

import com.epam.bets.dao.FaqDAO;
import com.epam.bets.dao.TransactionManager;
import com.epam.bets.dao.UserDAO;
import com.epam.bets.dao.impl.FaqDAOImpl;
import com.epam.bets.dao.impl.UserDAOImpl;
import com.epam.bets.entity.FAQ;
import com.epam.bets.entity.User;
import com.epam.bets.entity.UserRole;
import com.epam.bets.exception.DaoException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.pool.ConnectionPool;
import com.epam.bets.pool.ProxyConnection;
import com.epam.bets.receiver.UserReceiver;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;

public class Main {
    public static void main(String[] args) {
//        UserReceiver receiver = new UserReceiverImpl();
//        //UserDAO dao = new UserDAOImpl(ConnectionPool.getInstance().takeConnection());
//        FaqDAO dao = new FaqDAOImpl(ConnectionPool.getInstance().takeConnection());
//        TransactionManager manager = new TransactionManager();
//        manager.beginTransaction(dao);
//        FAQ faq = new FAQ();
//        faq.setQuestion("lala");
//        faq.setAnswer("LSKFDJG;AJKRG;");
//        User user = new User();
//        user.setRole(UserRole.ADMIN);
//        user.setLogin("ksenia@gmail.com");
//        user.setPassword(DigestUtils.md5Hex("Tomoe127"));
//        user.setFirstName("Ksenia");
//        user.setLastName("Pashchuk");
//        user.setBirthDate(LocalDate.of(1996, Month.OCTOBER, 2));
//        try {
//            dao.create(faq);
//            manager.commit();
//
//        } catch (DaoException e) {
//            System.err.println(e);
//            manager.rollback();
//        }
//        finally {
//            manager.close();
//
//
        final String SELECT_ALL_FAQ = "SELECT match_id, date_time, confederacy, first_team.team_name AS first_team, second_team.team_name AS second_team, total_value,\n" +
                "                max_bet_value FROM (football_match  JOIN football_team AS first_team ON football_match.first_team_id = first_team.team_id\n" +
                "                JOIN football_team AS second_team ON football_match.second_team_id = second_team.team_id \n" +
                "                JOIN match_bet_info ON match_id = match_bet_info.football_match_id) WHERE confederacy=?";


        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement statementFAQ = connection.prepareStatement(SELECT_ALL_FAQ)) {
            statementFAQ.setString(1, "friend match");
            ResultSet resultSet = statementFAQ.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("match_id"));
                System.out.println(resultSet.getDate("date_time"));
                System.out.println(resultSet.getString("confederacy"));
                System.out.println(resultSet.getString("first_team"));
                System.out.println(resultSet.getString("second_team"));
                System.out.println(resultSet.getBigDecimal("total_value"));
                System.out.println(resultSet.getBigDecimal("max_bet_value"));
            }

        } catch (SQLException e) {
            System.err.println(e);
        }
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println(e);
        }


    }
}

