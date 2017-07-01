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
    private static final String url = "jdbc:sqlite:lorenzo.db";

    /**
     * Database server timeout.
     */
    private static final int timeout = 60;

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
        String query = "CREATE TABLE IF NOT EXISTS users (username text PRIMARY KEY, pass text NOT NULL);";
        this.connection = DriverManager.getConnection(url);
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            statement.executeUpdate(query);
        } finally {
            if(statement != null)
                statement.close();
        }
    }

    /**
     * Method to register a new player into the database.
     * @param username passed by client.
     * @param pass passed by client.
     * @throws LoginException if player can't signed in because of some error.
     */
    /*package-local*/ void signInPlayer(String username, String pass) throws LoginException, SQLException{
        String query = "INSERT INTO users (username, pass) VALUES(?, ?);";
        if(!isAlreadyRegistered(username)) {
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, pass);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                throw new LoginException(LoginErrorType.GENERIC_SQL_ERROR);
            } finally {
                if(preparedStatement != null)
                    preparedStatement.close();
            }
        }
        else {
            throw new LoginException(LoginErrorType.USER_ALREADY_EXISTS);
        }
    }

    /**
     * Method to loginPlayer a user.
     * @param username passed by client.
     * @param pass passed by client.
     * @throws LoginException if some error occurs during loginPlayer.
     */
    /*package-local*/ void loginPlayer(String username, String pass) throws LoginException, SQLException{
        String query = "SELECT COUNT(*) AS number FROM users WHERE username=? AND pass=?;";
        if(isAlreadyRegistered(username)) {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            try {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, pass);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next())
                    if (resultSet.getInt("number") == 0)
                        throw new LoginException(LoginErrorType.USER_WRONG_PASSWORD);
            } catch (SQLException e) {
                throw new LoginException(LoginErrorType.GENERIC_SQL_ERROR);
            } finally {
                if(preparedStatement != null)
                    preparedStatement.close();
                if(resultSet != null)
                    resultSet.close();
            }
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
    private boolean isAlreadyRegistered(String username) throws LoginException, SQLException{
        String query = "SELECT COUNT(*) AS number FROM users WHERE username=?";
        boolean flag = false;
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
                if (resultSet.getInt("number") == 1)
                    flag = true;
            resultSet.close();
            preparedStatement.close();
            return flag;
        } catch(SQLException e){
            throw new LoginException(LoginErrorType.GENERIC_SQL_ERROR);
        } finally {
            if(preparedStatement != null)
                preparedStatement.close();
        }
    }

}