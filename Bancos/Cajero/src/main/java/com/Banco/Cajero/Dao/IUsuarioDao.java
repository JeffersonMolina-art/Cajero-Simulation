package com.Banco.Cajero.Dao;

import com.Banco.Cajero.Entity.Usuario;

import java.util.List;

public interface IUsuarioDao {
    List<Usuario> inicioSeccion(String cuenta, String pin);
    List<Usuario> accesAdmin(String user, String pin);

    List<Usuario>consultarSaldo(String cuenta);

    List<Usuario>reporteSaldo(String cuenta);

}
