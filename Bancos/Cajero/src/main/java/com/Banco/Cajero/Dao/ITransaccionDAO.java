package com.Banco.Cajero.Dao;

import com.Banco.Cajero.Entity.Transaccion;

import java.util.List;

public interface ITransaccionDAO {
    void movimiento(String cuenta, String transTipo, float transvalor);
    List<Transaccion> historialUser(String cuenta);
    void transferirCuenta(String cuentaOrigen, String cuentaDestino, float saldoTransferencia);
    void TransferirPorNumero(String cuentaOrigen, String numeroTelefono, float saldoTransferencia);

}
