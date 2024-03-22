package com.Banco.Cajero.Entity;
import lombok.Data;

import java.util.Date;

@Data
public class Transaccion {

    int idTransaccion;
    String TipoTransaccion;
    float monto;
    Date fechahora;
    int NumeroCuentaOrigen;
    int NumeroCuentaDestino;

}
