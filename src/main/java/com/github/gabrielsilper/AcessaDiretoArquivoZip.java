package com.github.gabrielsilper;

import com.github.gabrielsilper.daos.CEPDao;
import com.github.gabrielsilper.db.DatabaseConnection;
import com.github.gabrielsilper.models.CEP;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class AcessaDiretoArquivoZip {
    public static void main(String[] args) {
        // Arquivo que baixei do OpenCEP -> https://github.com/SeuAliado/OpenCEP/releases/
        String zipFilePath = "json-ceps.zip";
        String folder = "json-ceps/";
        Gson gson = new Gson();
        CEPDao cepDao = new CEPDao();

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);
            try (FileInputStream fis = new FileInputStream(zipFilePath);
                 ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis))) {

                ZipEntry entry;
                int insertions = 0;
                while ((entry = zis.getNextEntry()) != null && insertions < 100) {
                    if (entry.getName().startsWith(folder) && entry.getName().endsWith(".json")) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(zis, StandardCharsets.UTF_8));

                        CEP cep = gson.fromJson(reader, CEP.class);
                        cepDao.addCEP(connection, cep);
                        insertions++;
                    }
                }
                connection.commit();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                connection.rollback();
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
