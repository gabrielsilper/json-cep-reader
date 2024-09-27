package com.github.gabrielsilper;

import com.github.gabrielsilper.models.CEP;
import com.github.gabrielsilper.services.CEPJsonReader;

import java.io.File;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        CEPJsonReader cepJsonReader = new CEPJsonReader();
        File dir = new File("json-ceps");
        File[] jsons = dir.listFiles();

        if (Objects.nonNull(jsons)) {
            for (File json : jsons) {
                CEP cep = cepJsonReader.readCEPJson(json);
                System.out.println(cep);
            }
        }
    }
}