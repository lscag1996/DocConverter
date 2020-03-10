package com.lscag.utils;

import java.util.ArrayList;
import java.util.List;

public class CmdParser {
    List<Cmd> list = new ArrayList<>();

    public void registerCmd(Cmd cmd) {
        list.add(cmd);
    }

    public void parseCmd(String[] args) {
        for(int i = 0 ; i < args.length ; i++) {
            String s = args[i];
            if("-".equals(s.substring(0, 1))) {
                String temp = s.substring(1);
                for(int j = 0 ; j < list.size(); j++) {
                    if(list.get(j).cmd.equals(temp)) {
                        if(i + 1 < args.length) {
                            list.get(j).setValue(args[i + 1]);
                            i++;
                        }
                    }
                }
            }
        }
    }

    public String getValue(String cmd) {
        for(Cmd c : list) {
            if(c.cmd.equals(cmd)) {
                if(c.getValue() != null && !"".equals(c.getValue().trim())) {
                    return c.getValue();
                } else {
                    System.out.println("请输入参数: -" + c.cmd + " " + c.desc);
                    return null;
                }
            }
        }
        System.out.println("错误的参数:" + cmd);
        return null;
    }

    public static class Cmd {
        String fullname;
        String cmd;
        String value;
        String desc;

        public Cmd(String fullname, String cmd, String desc) {
            this.fullname = fullname;
            this.cmd = cmd;
            this.desc = desc;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
