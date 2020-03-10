package com.lscag.converter;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;
import java.util.List;

public class Doc2SimpleHtmlConverter implements DocConverter {
    @Override
    public boolean convertFile(String from, String to) {
        try {
            InputStream is = new FileInputStream(from);
            XWPFDocument document = new XWPFDocument(is);
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            StringBuilder builder = new StringBuilder();

            for(int i = 0 ; i < paragraphs.size() ; i++) {
                XWPFParagraph paragraph = paragraphs.get(i);
                List<XWPFRun> runs = paragraph.getRuns();
                for(int j = 0 ; j < runs.size(); j++) {
                    XWPFRun run = runs.get(j);
                    if(run.isBold()) {
                        builder.append("<b>");
                    }
                    builder.append(run.text());
                    if(run.isBold()) {
                        builder.append("</b>");
                    }
                }
                builder.append("<br>");
                builder.append("\n");
            }
            saveFile(builder, to);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveFile(StringBuilder builder, String path) throws IOException {
        String text = builder.toString();
        OutputStreamWriter out = new OutputStreamWriter(
                new FileOutputStream(path, false),
                "UTF-8"
        );
        out.write(text);
        out.flush();
        return true;
    }
}
