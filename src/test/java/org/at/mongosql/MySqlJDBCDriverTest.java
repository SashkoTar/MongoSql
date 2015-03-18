package org.at.mongosql;

import org.junit.Test;

import java.sql.*;

/**
 * Created by otarasenko on 3/18/15.
 */
public class MySqlJDBCDriverTest {

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    @Test
    public void shouldReadData() throws Exception {
        readDataBase();
    }

    public void readDataBase() throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/world?"
                            + "user=root&password=root");

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery("select * from city where CountryCode='AFG'");
            writeResultSet(resultSet);
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }

    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            String user = resultSet.getString("Name");
            String website = resultSet.getString("CountryCode");
            String summery = resultSet.getString("District");
            Integer date = resultSet.getInt("Population");
            System.out.println("Name: " + user);
            System.out.println("CountryCode: " + website);
            System.out.println("District: " + summery);
            System.out.println("Population: " + date);
        }
    }

}
