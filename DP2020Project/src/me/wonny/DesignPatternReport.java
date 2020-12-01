package me.wonny;

import com.holub.database.Database;
import com.holub.database.jdbc.JDBCStatement;

import java.io.IOException;
import java.sql.*;

public class DesignPatternReport {
    public static void main(String[] args) {
        // Get JBDC Driver instance we made
        try {
            Class.forName( "com.holub.database.jdbc.JDBCDriver" ).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Statement statement = null;
        Database database = null;
        final String pathname = "c:/dp2020";
        try {
             database = new Database(pathname);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            statement = new JDBCStatement(database);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM address");
            System.out.println(resultSetasString(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // At this point we've earned access to the database

//        FileWriter fileWriter = new FileWriter("c:/exporthtml.html");
    }

    public static String formatColumn(String msg, int width) {
        StringBuffer b = new StringBuffer(msg);
        for (width -= msg.length(); --width >= 0; )
            b.append(" ");
        return b.toString();
    }

    public static String resultSetasString(ResultSet results) throws SQLException {
        ResultSetMetaData metadata = results.getMetaData();

        StringBuffer b = new StringBuffer();
        int columns = metadata.getColumnCount();
        for (int i = 1; i <= columns; ++i)
            b.append(formatColumn(metadata.getColumnName(i), 10));
        b.append("\n");

        for (int i = 1; i <= columns; ++i)
            b.append("--------- ");
        b.append("\n");

        while (results.next()) {
            for (int i = 1; i <= columns; ++i)
                b.append(formatColumn(results.getString(metadata.getColumnName(i)), 10));
            b.append("\n");
        }
        return b.toString();
    }


}
