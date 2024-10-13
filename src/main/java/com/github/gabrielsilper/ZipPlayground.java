package com.github.gabrielsilper;

import org.zeroturnaround.zip.NameMapper;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;

public class ZipPlayground {
    public static void main(String[] args) {
        boolean exists = ZipUtil.containsEntry(new File("json-ceps.zip"), "json-ceps");
        if(exists){
            final String prefix = "json-ceps/";
            ZipUtil.unpack(new File("json-ceps.zip"), new File("json-ceps"), new NameMapper() {
                public String map(String name) {
                    return name.startsWith(prefix) ? name.substring(prefix.length()) : name;
                }
            });
        }
    }
}
