package com.Banco.Cajero.Entity;

import lombok.Data;

import java.util.Date;

@Data
public class Cajero {

    int cantidad;
    int billetesDe100;
    int billetesDe50;
    String fechahora;
    int total;
    String accion;
}
