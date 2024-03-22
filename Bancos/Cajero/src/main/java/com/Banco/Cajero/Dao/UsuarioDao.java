package com.Banco.Cajero.Dao;

import com.Banco.Cajero.Entity.Cajero;
import com.Banco.Cajero.Entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UsuarioDao implements IUsuarioDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public UsuarioDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Usuario> inicioSeccion(String cuenta, String pin) {
        String SQL = "EXEC inicioSeccion ?, ?";
        return jdbcTemplate.query(SQL, new Object[]{cuenta,pin}, new BeanPropertyRowMapper<>(Usuario.class));
    }

    @Override
    public List<Usuario> accesAdmin(String user, String pin) {
        String SQL = "EXEC accesAdmin ?, ?";
        return jdbcTemplate.query(SQL, new Object[]{user,pin}, new BeanPropertyRowMapper<>(Usuario.class));
    }

    @Override
    public List<Usuario> consultarSaldo(String cuenta) {
        String SQL = "EXEC consultarSaldo ? ";
        return jdbcTemplate.query(SQL, new Object[]{cuenta}, new BeanPropertyRowMapper<>(Usuario.class));

    }

    @Override
    public List<Usuario> reporteSaldo(String cuenta) {
        String SQL = "EXEC ReporteComprobacionSaldos ? ";
        return jdbcTemplate.query(SQL, new Object[]{cuenta}, new BeanPropertyRowMapper<>(Usuario.class));
    }
}
