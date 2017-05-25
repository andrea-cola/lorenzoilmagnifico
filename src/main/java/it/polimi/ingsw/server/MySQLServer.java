package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.LoginEnum;
import it.polimi.ingsw.exceptions.LoginException;

import java.sql.*;
import java.util.ArrayList;

public class MySQLServer{

    /**
     * Database url.
     */
    private final String url = "jdbc:sqlite:lorenzo.db";

    /**
     * Database connection timeout.
     */
    private final int timeout = 30;

    /**
     * Connection object.
     */
    private Connection connection;

    /**
     * Class constructor.
     */
    MySQLServer(){

    }

    /**
     * Connection method.
     */
    void connectAndCreate() throws LoginException{
        String query = "CREATE TABLE IF NOT EXISTS users (username text PRIMARY KEY, password text NOT NULL);";
        try {
            this.connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            statement.executeUpdate(query);
            System.out.println("Inizializzazione finita.");
        } catch(SQLException e){
            throw new LoginException(LoginEnum.CANNOT_CONNECT_TO_DATABASE);
        }
    }

    /**
     * Method to register user into database. Before add new user in database check if the username already exists.
     * @param username
     * @param password
     * @throws LoginException
     */
    void signin(String username, String password) throws LoginException{
        String query = "INSERT INTO users (username, password) VALUES(?, ?);";
        if(!isAlreadyRegistered(username)) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new LoginException(LoginEnum.SQL_PROBLEM);
            }
        }
        else {
            throw new LoginException(LoginEnum.USER_ALREADY_REGISTERED);
        }
    }

    /**
     * Method to validate the provided password.
     * @param username
     * @param password
     * @return
     * @throws LoginException
     */
    void login(String username, String password) throws LoginException{
        String query = "SELECT COUNT(*) AS number FROM users WHERE username=? AND password=?;";
        if(isAlreadyRegistered(username)) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next())
                    if (resultSet.getInt("number") == 0)
                        throw new LoginException(LoginEnum.WRONG_PASSWORD);
            } catch (SQLException e) {
                throw new LoginException(LoginEnum.SQL_PROBLEM);
            }
        }else{
            throw new LoginException(LoginEnum.USER_NOT_REGISTERED);
        }

    }

    /**
     * Method to check if user is already registered. If already exist throw a LoginException.
     * @param username
     * @return
     * @throws LoginException
     */
    boolean isAlreadyRegistered(String username) throws LoginException{
        String query = "SELECT COUNT(*) AS number FROM users WHERE username=?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
                if (resultSet.getInt("number") == 1)
                    return true;
            return false;
        }catch(SQLException e){
            throw new LoginException(LoginEnum.SQL_PROBLEM);
        }
    }

    /**
     * Method to close connection.
     * @throws LoginException
     */
    void closeSafely() throws LoginException{
        try{
            connection.close();
        }catch(SQLException e){
            System.out.println("Error while closing connection."); //------------------------------------//
        }
    }

}