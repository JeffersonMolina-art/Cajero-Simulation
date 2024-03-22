package com.Banco.Cajero.Dao;

import com.Banco.Cajero.Entity.Transaccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class TransaccionDao implements ITransaccionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public TransaccionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void movimiento(String cuenta, String transTipo, float transvalor) {
        String sql = "EXEC Movimiento ?, ?, ?";
        jdbcTemplate.update(sql, cuenta, transTipo, transvalor);
    }

    @Override
    public List<Transaccion> historialUser(String cuenta) {
        String SQL = "EXEC HistorialUser ?";
        return jdbcTemplate.query(SQL, new Object[]{cuenta}, new BeanPropertyRowMapper<>(Transaccion.class));
    }

    @Override
    public void transferirCuenta(String cuentaOrigen, String cuentaDestino, float saldoTransferencia) {
        String sql = "EXEC TransferirCuenta ?, ?, ?";
        jdbcTemplate.update(sql, cuentaOrigen, cuentaDestino, saldoTransferencia);
    }

    @Override
    public void TransferirPorNumero(String cuentaOrigen, String numeroTelefono, float saldoTransferencia) {
        String sql = "EXEC TransferirPorNumero ?, ?, ?";
        jdbcTemplate.update(sql, cuentaOrigen, numeroTelefono, saldoTransferencia);
    }
}
