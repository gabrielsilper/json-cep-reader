package com.github.gabrielsilper.utils;

import com.github.gabrielsilper.models.CEP;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class JsonCepReader {
    private final Gson gson;

    public JsonCepReader() {
        this.gson = new Gson();
    }

    public CEP read(String path) {
        try (Reader reader = new FileReader(path)) {
            return gson.fromJson(reader, CEP.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CEP read(File json) {
        try (Reader reader = new FileReader(json)) {
            return gson.fromJson(reader, CEP.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
