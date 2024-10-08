package com.github.gabrielsilper.daos;

import com.github.gabrielsilper.models.CEP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CEPDao {

    public void addCEP(Connection con, CEP cep){
        String sql = "INSERT INTO ceps " +
                " (cep, logradouro, complemento, bairro, localidade, uf, ibge) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?) " +
                " ON DUPLICATE KEY UPDATE " +
                " logradouro = VALUES(logradouro), " +
                " complemento = VALUES(complemento), " +
                " bairro = VALUES(bairro);";

        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, cep.getCep());
            ps.setString(2, cep.getLogradouro());
            ps.setString(3, cep.getComplemento());
            ps.setString(4, cep.getBairro());
            ps.setString(5, cep.getLocalidade());
            ps.setString(6, cep.getUf());
            ps.setString(7, cep.getIbge());
            ps.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void addCEPsBatch(Connection con, List<CEP> ceps) {
        String sql = "INSERT INTO ceps (cep, logradouro, complemento, bairro, localidade, uf, ibge) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "logradouro = VALUES(logradouro), " +
                "complemento = VALUES(complemento), " +
                "bairro = VALUES(bairro);";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (CEP cep : ceps) {
                ps.setString(1, cep.getCep());
                ps.setString(2, cep.getLogradouro());
                ps.setString(3, cep.getComplemento());
                ps.setString(4, cep.getBairro());
                ps.setString(5, cep.getLocalidade());
                ps.setString(6, cep.getUf());
                ps.setString(7, cep.getIbge());
                ps.addBatch(); // Adiciona a consulta ao batch
            }

            ps.executeBatch(); // Executa todas as consultas em batch
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
