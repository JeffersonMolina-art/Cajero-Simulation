package com.Banco.Cajero.Dao;

import com.Banco.Cajero.Entity.Cajero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class CajeroDao implements ICajeroDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public CajeroDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Cajero> historialCajero() {
        String SQL = "EXEC HistorialCajero ";
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Cajero.class));
    }

    @Override
    public List<Cajero> saldoCajero() {
        String SQL = "EXEC SaldoCajero";
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(Cajero.class));
    }

    @Override
    public void reponerDineroCajero(String user, String pin, int billetesDe100, int billetesDe50) {
        String sql = "EXEC ReponerDineroCajero ?, ?, ?, ?";
        jdbcTemplate.update(sql, user,pin,billetesDe100,billetesDe50);
    }
}
