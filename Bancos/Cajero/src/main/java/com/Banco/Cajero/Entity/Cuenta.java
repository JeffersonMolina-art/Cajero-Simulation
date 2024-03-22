package com.Banco.Cajero.Entity;

import lombok.Data;

@Data
public class Cuenta {
    String nombreUsuario;
    String rol;
    String cuenta;
    float saldo;
    String telefono;

}
