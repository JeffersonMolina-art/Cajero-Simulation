package com.Banco.Cajero.Service;

import com.Banco.Cajero.Entity.Transaccion;

import java.util.List;

public interface ITransaccionService {
    void movimiento(String cuenta, String transTipo, float transvalor);
    List<Transaccion> historialUser(String cuenta);
    void transferirCuenta(String cuentaOrigen, String cuentaDestino, float saldoTransferencia);
    void transferirPorNumero(String cuentaOrigen, String numeroTelefono, float saldoTransferencia);
}
