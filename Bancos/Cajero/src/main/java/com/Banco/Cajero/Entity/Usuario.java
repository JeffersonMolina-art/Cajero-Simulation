package com.Banco.Cajero.Entity;

import lombok.Data;

@Data
public class Usuario {
    int idUsuario;
    String nombreUsuario;
    String telefono;
    String pin;
    String rol;
    String numeroCuenta;
    String saldoCuenta;
    String saldoTransaccion;
    float saldo;
}
