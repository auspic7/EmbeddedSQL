package me.wonny.test;

import com.holub.database.Table;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XMLExporter implements Table.Exporter {
    private final Writer out;
    private String tableName;
    private List<String> columnNames = new ArrayList<String>();

    public XMLExporter(Writer out) {
        this.out = out;
    }

    @Override
    public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {
        this.tableName = tableName;
        out.write("<" + tableName + ">\n" +
                "<colnames>\n");
        while (columnNames.hasNext()){
            String colname = columnNames.next().toString();
            this.columnNames.add(colname);
            out.write("<" + colname + "/>\n");
        }
        out.write("</colnames>\n");

    }

    @Override
    public void storeRow(Iterator data) throws IOException {
        out.write("<row>\n");
        for(String columnName: columnNames){
            String text = data.next().toString();
            out.write("<"+columnName+">"+text+"</"+columnName+">\n");
        }
        out.write("</row>\n");
    }

    @Override
    public void startTable() throws IOException { }

    @Override
    public void endTable() throws IOException {
        out.write("</" + tableName + ">\n");
    }
}
