package database;

import java.sql.*;

public abstract class AbstractDAO {


    private Connection connection;
    private Statement statement;

    protected void simpleStatement(String sqlStatement) {

        connect();

        try {
            statement.executeUpdate(sqlStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        disconnect();
    }

    protected ResultSet resultSetStatement(String sqlStatement) {

        connect();

        try {
            return statement.executeQuery(sqlStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        disconnect();

        return null;


    }

    protected void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:h2:./lib/Conway-Game-Of-Live", "sa", "");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void disconnect() {

        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
