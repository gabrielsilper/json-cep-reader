package com.github.gabrielsilper;

import com.github.gabrielsilper.daos.CEPDao;
import com.github.gabrielsilper.db.DatabaseConnection;
import com.github.gabrielsilper.models.CEP;
import com.github.gabrielsilper.utils.JsonCepReader;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws SQLException, InterruptedException {
        long startTime = System.nanoTime();

        JsonCepReader cepJsonReader = new JsonCepReader();
        Connection con = DatabaseConnection.getConnection();
        CEPDao cepDao = new CEPDao();
        File dir = new File("json-ceps");
        File[] jsons = dir.listFiles();

        try {
            con.setAutoCommit(false);
            if (Objects.nonNull(jsons)) {
                // Testes para melhorar o desempenho, com esse sleep para dar folga as operações dd banco, foi 3x mais rápido.
                System.out.println(jsons.length);
                for (int i = 0; i < 10000; i++) {
                    if (jsons[i].getName().endsWith(".json")) {
                        CEP cep = cepJsonReader.read(jsons[i]);
                        cepDao.addCEP(con, cep);
                    }
                }
                Thread.sleep(2000);
                for (int i = 10000; i < 20000; i++) {
                    CEP cep = cepJsonReader.read(jsons[i]);
                    cepDao.addCEP(con, cep);
                }
                Thread.sleep(2000);
                for (int i = 20000; i < 30000; i++) {
                    CEP cep = cepJsonReader.read(jsons[i]);
                    cepDao.addCEP(con, cep);
                }
                System.out.println("CEPs adicionados");
            }
            con.commit();
        } finally {
            con.rollback();
            con.setAutoCommit(true);
            con.close();
        }

        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0; // Converte para segundos
        System.out.println("Tempo de execução: " + durationInSeconds + " segundos");
    }
}