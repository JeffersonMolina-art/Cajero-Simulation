package com.Banco.Cajero.Dao;

import com.Banco.Cajero.Entity.Cajero;

import java.util.Date;
import java.util.List;

public interface ICajeroDao {
    List<Cajero> historialCajero();
    List<Cajero> saldoCajero();
    void reponerDineroCajero(String user, String pin, int billetesDe100, int billetesDe50);


}
