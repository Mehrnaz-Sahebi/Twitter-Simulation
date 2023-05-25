package DataBase;

import java.sql.*;
import java.lang.*;

public class DB {

    // Loading driver using forName()method


    public DB() throws SQLException {
    }
    public void checkTableForSignedInUser() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "Ma@12345");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from ");
    }
}
