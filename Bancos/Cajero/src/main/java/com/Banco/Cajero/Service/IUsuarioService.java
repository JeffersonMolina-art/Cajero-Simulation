package com.Banco.Cajero.Service;

import com.Banco.Cajero.Entity.Usuario;

import java.util.List;

public interface IUsuarioService {
    List<Usuario> inicioSeccion(String cuenta, String pin);
    List<Usuario> accesAdmin(String user, String pin);
    List<Usuario>consultarSaldo(String cuenta);
    List<Usuario>reporteSaldo(String cuenta);



}
