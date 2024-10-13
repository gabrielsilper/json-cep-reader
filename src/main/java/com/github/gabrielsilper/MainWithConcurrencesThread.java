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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainWithConcurrencesThread {
    public static void main(String[] args) throws SQLException, ExecutionException, InterruptedException {
        long startTime = System.nanoTime();

        JsonCepReader cepJsonReader = new JsonCepReader();
        Connection con = DatabaseConnection.getConnection();
        CEPDao cepDao = new CEPDao();
        File dir = new File("json-ceps");
        File[] jsons = dir.listFiles();

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<CEP>> futures = new ArrayList<>();

        if (Objects.nonNull(jsons)) {
            for (File json : jsons) {
                futures.add(executor.submit(() -> cepJsonReader.read(json)));
            }
            for (Future<CEP> future : futures) {
                CEP cep = future.get();
                cepDao.addCEP(con, cep);
            }
            System.out.println("CEPs adicionados");
        }

        executor.shutdown();
        con.close();

        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0; // Converte para segundos
        System.out.println("Tempo de execução: " + durationInSeconds + " segundos");
    }
}