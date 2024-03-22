package com.Banco.Cajero.Service;

import com.Banco.Cajero.Dao.ICajeroDao;
import com.Banco.Cajero.Entity.Cajero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class CajeroSerivice implements CajeroService{
    @Autowired
    private ICajeroDao iCajeroDao;
    @Override
    public List<Cajero> historialCajero() {
        List<Cajero> historialCajero;
        try {
            historialCajero = iCajeroDao.historialCajero();
        }catch (Exception ex){
            throw ex;
        }
        return historialCajero;
    }

    @Override
    public List<Cajero> saldoCajero() {
        List<Cajero> saldoCajero;
        try {
            saldoCajero = iCajeroDao.saldoCajero();
        }catch (Exception ex){
            throw ex;
        }
        return saldoCajero;
    }

    @Override
    public void reponerDineroCajero(String user, String pin, int billetesDe100, int billetesDe50) {
        try {
            iCajeroDao.reponerDineroCajero(user, pin, billetesDe100, billetesDe50);
        }catch (Exception ex){
            throw ex;
        }
    }
}
