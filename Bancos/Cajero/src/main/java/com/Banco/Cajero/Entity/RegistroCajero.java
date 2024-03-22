package com.Banco.Cajero.Entity;

import lombok.Data;

import java.util.Date;

@Data
public class RegistroCajero {
    int id;
    Date fechahora;
    int billetesDe100;
    int billetesDe50;
    float total;
    int administradorId;
    String accion;
}
