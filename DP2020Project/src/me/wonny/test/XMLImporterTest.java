package me.wonny.test;

import com.holub.database.CSVImporter;
import com.holub.database.ConcreteTable;
import com.holub.database.Table;
import com.holub.database.TableFactory;
import org.junit.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;

public class XMLImporterTest {
    @Test
    public void exportPeopleTable() {
        try {
            Table people = TableFactory.create("people", new String[] { "last", "first", "addrId" });
            people.insert(new Object[] { "Holub", "Allen", "1" });
            people.insert(new Object[] { "Flintstone", "Wilma", "2" });
            people.insert(new String[] { "addrId", "first", "last" }, new Object[] { "2", "Fred", "Flintstone" });
            Writer writer = new FileWriter("c:/dp2020/people.xml");
            people.export(new XMLExporter(writer));
            writer.close();

            Reader in = new FileReader("c:/dp2020/people.xml");
            people = new ConcreteTable(new XMLImporter(in));
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
