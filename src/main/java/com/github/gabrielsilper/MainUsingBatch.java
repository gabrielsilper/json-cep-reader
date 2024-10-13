package com.github.gabrielsilper;

import com.github.gabrielsilper.daos.CEPDao;
import com.github.gabrielsilper.db.DatabaseConnection;
import com.github.gabrielsilper.models.CEP;
import com.github.gabrielsilper.utils.JsonCepReader;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainUsingBatch {
    public static void main(String[] args) throws SQLException {
        long startTime = System.nanoTime();

        JsonCepReader cepJsonReader = new JsonCepReader();
        Connection con = DatabaseConnection.getConnection();
        CEPDao cepDao = new CEPDao();
        File dir = new File("json-ceps");
        File[] jsons = dir.listFiles();

        if (Objects.nonNull(jsons)) {
            List<CEP> cepsToInsert = new ArrayList<>();
            for (File json : jsons) {
                CEP cep = cepJsonReader.read(json);
                cepsToInsert.add(cep);
            }
            cepDao.addCEPsBatch(con, cepsToInsert);
            System.out.println("CEPs adicionados");
        }
        con.close();

        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0; // Converte para segundos
        System.out.println("Tempo de execução: " + durationInSeconds + " segundos");
    }
}