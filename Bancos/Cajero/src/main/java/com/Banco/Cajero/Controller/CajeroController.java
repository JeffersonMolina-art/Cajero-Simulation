package com.Banco.Cajero.Controller;

import com.Banco.Cajero.Entity.Cajero;
import com.Banco.Cajero.Service.CajeroSerivice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/cajero")
@CrossOrigin("*")
public class CajeroController {
    @Autowired
    private CajeroSerivice cajeroSerivice;
    @GetMapping("/historialCajero")
    public ResponseEntity<List<Cajero>> historialCajero(){
        var result = cajeroSerivice.historialCajero();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/saldoCajero")
    public ResponseEntity<List<Cajero>> saldoCajero(){
        var result = cajeroSerivice.saldoCajero();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/reponer/{user}/{pin}/{billetesDe100}/{billetesDe50}")
    public ResponseEntity<String> reponerDinero(@PathVariable("user") String user,
                                                @PathVariable("pin") String pin,
                                                @PathVariable("billetesDe100") int billetesDe100,
                                                @PathVariable("billetesDe50") int billetesDe50) {
        try {
            cajeroSerivice.reponerDineroCajero(user, pin, billetesDe100, billetesDe50);
            return ResponseEntity.ok("Transacción realizada con éxito");
        } catch (Exception e) {
            // Manejo de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor");
        }
    }


}
