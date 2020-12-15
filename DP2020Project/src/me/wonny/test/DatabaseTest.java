package me.wonny.test;

import com.holub.database.Database;
import com.holub.database.jdbc.JDBCStatement;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseTest {
    Database database = null;
    Statement statement = null;

    @Before
    public void initDBAndTable() {
        try {
            Class.forName("com.holub.database.jdbc.JDBCDriver").newInstance();
            database = new Database("c:/dp2020");
            statement = new JDBCStatement(database);
            statement.executeUpdate("create table address (addrId int, street varchar, city varchar, state char(2), zip int, primary key(addrId))");
            statement.executeUpdate("create table name(first varchar(10), last varchar(10), addrId integer)");
            statement.executeUpdate("insert into address values( 0,'12 MyStreet','Berkeley','CA','99999')");
            statement.executeUpdate("insert into address values( 1, '34 Quarry Ln.', 'Bedrock' , 'XX', '00000')");
            statement.executeUpdate("insert into name VALUES ('Fred',  'Flintstone', '1')");
            statement.executeUpdate("insert into name VALUES ('Wilma', 'Flintstone', '1')");
            statement.executeUpdate("insert into name (last,first,addrId) VALUES('Holub','Allen',(10-10*1))");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insertAndSelectAddress() {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT state FROM address");
            resultSet.next();
            assertEquals(resultSet.getString("state"), "CA");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insertAndJoinDuplicateName() {
        try {
            ResultSet resultSet = statement.executeQuery("select * from address,name where address.addrId = name.addrId\n");
            System.out.println(resultSet.next());
            assertEquals(resultSet.getString("addrId"), "0");
            assertEquals(resultSet.getString("street"), "12 MyStreet");
            assertEquals(resultSet.getString("city"), "Berkeley");
            assertEquals(resultSet.getString("zip"), "99999");
            assertEquals(resultSet.getString("first"), "Allen");
            assertEquals(resultSet.getString("last"), "Holub");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
