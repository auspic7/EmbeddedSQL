package me.wonny;

import com.holub.database.Table;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

public class HTMLExporter implements Table.Exporter {
    private final Writer out;
    private 	  int	 width;


    public HTMLExporter(Writer out) {
        this.out = out;
    }

    @Override
    public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {
        out.write("<thead>\n");
        storeRow( columnNames ); // comma separated list of columns ids
        out.write("</thead>\n<tbody>");
    }

    @Override
    public void storeRow(Iterator data) throws IOException {
        out.write("<tr>\n");
        while (data.hasNext()){
            String text = data.next().toString();
            out.write("<td>"+text+"</td>\n");
        }
        out.write("</tr>\n");
    }

    @Override
    public void startTable() throws IOException {
        out.write("<table>");
    }

    @Override
    public void endTable() throws IOException {
        out.write("</tbody>\n" +
                "</table>");
    }
}
