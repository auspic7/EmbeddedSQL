package me.wonny.test;

import com.holub.database.Table;
import com.holub.tools.ArrayIterator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLImporter implements Table.Importer {
    private BufferedReader in;            // null once end-of-file reached
    private String[] columnNames;
    private String tableName;

    public XMLImporter(Reader in) {
        this.in = in instanceof BufferedReader
                ? (BufferedReader) in
                : new BufferedReader(in)
        ;
    }

    public void startTable() throws IOException {
        tableName = in.readLine().trim();
        tableName = tableName.substring(1, tableName.length() - 1);
        assert in.readLine().trim().equals("<colnames>");
        List<String> colnames = new ArrayList<String>();
        String line = in.readLine();
        while (!line.equals("</colnames>")) {
            Matcher matcher = Pattern.compile("(?<=<).*(?=/>)").matcher(line);
            matcher.find();
            colnames.add(matcher.group());
            line = in.readLine();
        }
        columnNames = colnames.toArray(new String[colnames.size()]);
    }

    public String loadTableName() throws IOException {
        return tableName;
    }

    public int loadWidth() throws IOException {
        return columnNames.length;
    }

    public Iterator loadColumnNames() throws IOException {
        return new ArrayIterator(columnNames);  //{=CSVImporter.ArrayIteratorCall}
    }

    public Iterator loadRow() throws IOException {
        Iterator row = null;
        if (in != null) {
            String line = in.readLine();
            if (line == null)
                in = null;
            else {
                List<String> items = new ArrayList<String>();
                line = in.readLine();
                while (line != null && !line.equals("</row>")) {
                    Matcher matcher = Pattern.compile("(?<=>).*(?=</)").matcher(line);
                    matcher.find();
                    items.add(matcher.group());
                    line = in.readLine();
                }
                row = items.size() != 0 ? new ArrayIterator(items.toArray()) : null;
            }
        }
        return row;
    }

    public void endTable() throws IOException {
    }
}
