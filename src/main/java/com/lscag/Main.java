package com.lscag;

import com.lscag.config.ConvertType;
import com.lscag.converter.DocConverterBase;
import com.lscag.utils.CmdParser;

import java.io.File;
import java.util.*;

public class Main {
//    static final String FROM = "E:\\file\\stick\\";
//    static final String TO = FROM + "result\\";
//    static final String DOCX = ".docx";
//    static final String HTML = ".html";

    static String FROM;
    static String TO;
    static String DOCX = ".docx";
    static String HTML = ".html";

    static CmdParser parser = new CmdParser();
    static {
        parser.registerCmd(new CmdParser.Cmd("input path", "i", "输入路径"));
        parser.registerCmd(new CmdParser.Cmd("output path", "o", "输出路径"));
    }

    public static void main(String[] args) {
        parser.parseCmd(args);
        FROM = parser.getValue("i");
        TO = parser.getValue("o");
        if(FROM == null) {
            return;
        }

        if(!checkParam(FROM)) {
            FROM = FROM + File.separator;
        }

        if(TO == null) {
            TO = FROM + "result";
            System.out.println("未指定输出路径，默认保存在：" + TO);
        }

        if(!checkParam(TO)) {
            TO = TO + File.separator;
        }

        DocConverterBase base = new DocConverterBase();
        Map<String,String> map = getMap();
        if(map == null) {
            return;
        }
        base.convertFile(map, ConvertType.HTML);
    }

    private static boolean checkParam(String param) {
        if(param.charAt(param.length() - 1) == '/' || param.substring(param.length() - 1).equals("\\")){
            return true;
        }
        return false;
    }

    public static Map<String,String> getMap() {
        File from = new File(FROM);
        if(!from.exists()) {
            System.out.println("路径：" + FROM + " 不存在");
            return null;
        }
        File to = new File(TO);
        if(!to.exists() && !to.mkdir()) {
            System.out.println("输出路径" + TO + "生成失败,请尝试手动创建，或联系lishicheng1@xiaomi.com");
            return null;
        }
        File[] files = from.listFiles();
        List<String> list = new ArrayList<>();
        if(files == null || files.length == 0) {
            System.out.println("路径：" + FROM + " 下不存在文件，请检查");
            return null;
        }
        for(File f : files) {
            if(f.isFile() && f.getName().contains(DOCX)) {
                String temp = f.getName();
                String fileName = temp.substring(0, temp.lastIndexOf("."));
                list.add(fileName);
            }
        }
        Map<String, String> convert = new HashMap<>();
        for(String s : list) {
            convert.put(FROM + s + DOCX, TO + s + HTML);
        }
        return convert;
    }
}
