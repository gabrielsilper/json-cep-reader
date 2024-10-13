package com.github.gabrielsilper;

import org.zeroturnaround.zip.ZipUtil;

import java.io.File;

public class ZipPlayground {
    public static void main(String[] args) {
        boolean exists = ZipUtil.containsEntry(new File("json-ceps.zip"), "json-ceps");
        if(exists){
            ZipUtil.explode(new File("json-ceps.zip"));
        }
    }
}
