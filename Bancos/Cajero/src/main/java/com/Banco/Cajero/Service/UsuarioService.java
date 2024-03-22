package com.Banco.Cajero.Service;

import com.Banco.Cajero.Dao.ICajeroDao;
import com.Banco.Cajero.Dao.IUsuarioDao;
import com.Banco.Cajero.Entity.Cajero;
import com.Banco.Cajero.Entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements IUsuarioService {
    @Autowired
    private IUsuarioDao iUsuarioDao;

    @Override
    public List<Usuario> inicioSeccion(String cuenta, String pin) {
        List<Usuario> inicioSeccion;
        try {
            inicioSeccion = iUsuarioDao.inicioSeccion(cuenta, pin);
        }catch (Exception ex){
            throw ex;
        }
        return inicioSeccion;
    }

    @Override
    public List<Usuario> accesAdmin(String user, String pin) {
        List<Usuario> accesAdmins;
        try {
            accesAdmins = iUsuarioDao.accesAdmin(user, pin);
        }catch (Exception ex){
            throw ex;
        }
        return accesAdmins;
    }

    @Override
    public List<Usuario> consultarSaldo(String cuenta) {
        List<Usuario> consultarSaldo;
        try {
            consultarSaldo = iUsuarioDao.consultarSaldo(cuenta);
        }catch (Exception ex){
            throw ex;
        }
        return consultarSaldo;
    }

    @Override
    public List<Usuario> reporteSaldo(String cuenta) {
        List<Usuario> reporteDeCuenta;
        try {
            reporteDeCuenta = iUsuarioDao.reporteSaldo(cuenta);
        }catch (Exception ex){
            throw ex;
        }
        return reporteDeCuenta;
    }
}
