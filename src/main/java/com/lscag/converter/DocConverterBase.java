package com.lscag.converter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DocConverterBase {

    DocConverter converter;

    public void convertFile(Map<String, String> paths, String convertType) {
        Set<Map.Entry<String, String>> entrySet = paths.entrySet();
        int size = entrySet.size();
        int success = 0;
        int fail = 0;
        Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
        if(converter == null) {
            converter = getConverter(convertType);
        }
        if(converter == null) {
            System.out.println("not fount convert for " + convertType);
            return;
        }
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String from = entry.getKey();
            String to = entry.getValue();
            if(converter.convertFile(from, to)) {
                success++;
                System.out.println("已完成【" + success*100/size + "%】");
            } else {
                fail++;
                System.out.println(from + " convert fail");
            }
        }
        System.out.println("完成【" + success + "】" + " 失败【" + fail + "】");
    }


    DocConverter getConverter(String convertType) {
        return new Doc2SimpleHtmlConverter();
    }
}
