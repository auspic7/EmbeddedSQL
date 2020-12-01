package me.wonny.test;

import com.holub.database.Database;
import com.holub.database.jdbc.JDBCStatement;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseTest {
    @Test
    public void insertAndSelectAddress() {
        try {
            Class.forName("com.holub.database.jdbc.JDBCDriver").newInstance();
            Statement statement = null;
            Database database = null;
            database = new Database("c:/dp2020");
            statement = new JDBCStatement(database);
            statement.executeUpdate("create table address (addrId int, street varchar, city varchar, state char(2), zip int, primary key(addrId))");
            statement.executeUpdate("create table name(first varchar(10), last varchar(10), addrId integer)");
            statement.executeUpdate("insert into address values( 0,'12 MyStreet','Berkeley','CA','99999')");
            statement.executeUpdate("insert into address values( 1, '34 Quarry Ln.', 'Bedrock' , 'XX', '00000')");
            statement.executeUpdate("insert into name VALUES ('Fred',  'Flintstone', '1')");
            statement.executeUpdate("insert into name VALUES ('Wilma', 'Flintstone', '1')");
            statement.executeUpdate("insert into name (last,first,addrId) VALUES('Holub','Allen',(10-10*1))");

            ResultSet resultSet = statement.executeQuery("SELECT state FROM address");
            resultSet.next();
            assertEquals(resultSet.getString("state"), "CA");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
