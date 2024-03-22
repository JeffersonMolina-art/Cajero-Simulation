package com.Banco.Cajero.Service;

import com.Banco.Cajero.Dao.ITransaccionDAO;
import com.Banco.Cajero.Entity.Transaccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransaccionService implements ITransaccionService {
    @Autowired
    private ITransaccionDAO iTransaccionDAO;
    @Override
    public void movimiento(String cuenta, String transTipo, float transvalor) {
        try {
            iTransaccionDAO.movimiento(cuenta, transTipo, transvalor);
        }catch (Exception ex){
            throw ex;
        }
    }

    @Override
    public List<Transaccion> historialUser(String cuenta) {
        List<Transaccion> listarHistorialUser;
        try {
            listarHistorialUser = iTransaccionDAO.historialUser(cuenta);
        }catch (Exception ex){
            throw ex;
        }
        return listarHistorialUser;
    }

    @Override
    public void transferirCuenta(String cuentaOrigen, String cuentaDestino, float saldoTransferencia) {
        try {
            iTransaccionDAO.transferirCuenta(cuentaOrigen, cuentaDestino, saldoTransferencia);
        }catch (Exception ex){
            throw ex;
        }
    }

    @Override
    public void transferirPorNumero(String cuentaOrigen, String numeroTelefono, float saldoTransferencia) {
        try {
            iTransaccionDAO.TransferirPorNumero(cuentaOrigen, numeroTelefono, saldoTransferencia);
        }catch (Exception ex){
            throw ex;
        }
    }
}
