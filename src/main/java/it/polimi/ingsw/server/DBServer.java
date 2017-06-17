package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.LoginErrorType;
import it.polimi.ingsw.exceptions.LoginException;

import java.sql.*;

/**
 * Class used to manage database server based on SQL Lite.
 */
/*package-local*/ class DBServer {

    /**
     * Database address.
     */
    private final String url = "jdbc:sqlite:lorenzo.db";

    /**
     * Database server timeout.
     */
    private final int timeout = 60;

    /**
     * Connection object.
     */
    private Connection connection;

    /**
     * Class constructor.
     */
    /*package-local*/DBServer(){
    }

    /**
     * Method to create database if not exists e connect the server to it.
     * @throws SQLException if database errors occur.
     */
    /*package-local*/ void connectToDatabase() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS users (username text PRIMARY KEY, password text NOT NULL);";
        this.connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(timeout);
        statement.executeUpdate(query);
    }

    /**
     * Method to register a new player into the database.
     * @param username passed by client.
     * @param password passed by client.
     * @throws LoginException if player can't signed in because of some error.
     */
    /*package-local*/ void signInPlayer(String username, String password) throws LoginException{
        String query = "INSERT INTO users (username, password) VALUES(?, ?);";
        if(!isAlreadyRegistered(username))
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new LoginException(LoginErrorType.GENERIC_SQL_ERROR);
            }
        else {
            throw new LoginException(LoginErrorType.USER_ALREADY_EXISTS);
        }
    }

    /**
     * Method to loginPlayer a user.
     * @param username passed by client.
     * @param password passed by client.
     * @throws LoginException if some error occurs during loginPlayer.
     */
    /*package-local*/ void loginPlayer(String username, String password) throws LoginException{
        String query = "SELECT COUNT(*) AS number FROM users WHERE username=? AND password=?;";
        if(isAlreadyRegistered(username))
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next())
                    if (resultSet.getInt("number") == 0)
                        throw new LoginException(LoginErrorType.USER_WRONG_PASSWORD);
            } catch (SQLException e) {
                throw new LoginException(LoginErrorType.GENERIC_SQL_ERROR);
            }
        else
            throw new LoginException(LoginErrorType.USER_NOT_EXISTS);

    }

    /**
     * Method that return true if the user is already registered, otherwise false.
     * @param username passed by client.
     * @return a boolean.
     * @throws LoginException if a SQL error occurs.
     */
    private boolean isAlreadyRegistered(String username) throws LoginException{
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
            throw new LoginException(LoginErrorType.GENERIC_SQL_ERROR);
        }
    }

    /*package-local*/ void showPlayers(){
        String query = "SELECT * FROM users";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next())
                System.out.println(resultSet.getString("username") + " " + resultSet.getString("password"));
        } catch (SQLException e) {

        }

    }

}