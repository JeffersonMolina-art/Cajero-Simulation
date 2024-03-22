package com.Banco.Cajero.Controller;

import com.Banco.Cajero.Entity.Transaccion;
import com.Banco.Cajero.Service.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trans")
@CrossOrigin("*")
public class TransaccionController {

    @Autowired
    private TransaccionService transaccionService;
    @GetMapping("/historialuser/{cuenta}")
    public ResponseEntity<List<Transaccion>> listarHistorialUser(@PathVariable String cuenta){
        var result = transaccionService.historialUser(cuenta);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping("/movimiento/{cuenta}/{transTipo}/{transValor}")
    public ResponseEntity<String> realizarTransaccion( @PathVariable("cuenta") String cuenta,
                                                       @PathVariable("transTipo") String tipo,
                                                       @PathVariable("transValor") float monto) {

        transaccionService.movimiento(cuenta, tipo, monto);
        return ResponseEntity.ok("Transacción realizada con éxito");
    }
    @PostMapping("/transferenciaCuenta/{cuentaOrigen}/{cuentaDestino}/{saldoTransaccion}")
    public ResponseEntity<String> trasnferenciaCuenta(
            @PathVariable("cuentaOrigen") String cuentaOrigen,
            @PathVariable("cuentaDestino") String cuentaDestino,
            @PathVariable("saldoTransaccion") float saldoTransaccion) {

        transaccionService.transferirCuenta(cuentaOrigen, cuentaDestino, saldoTransaccion);
        return ResponseEntity.ok("Transferencia realizada con éxito");
    }

    @PostMapping("/transferenciaNumero/{cuentaOrigen}/{numeroTelefono}/{saldoTransferencia}")
    public ResponseEntity<String> transferenciaNumero( @PathVariable("cuentaOrigen") String cuentaOrigen,
                                                       @PathVariable("numeroTelefono") String numeroTelefono,
                                                       @PathVariable("saldoTransferencia") float saldoTransferencia) {

        transaccionService.transferirPorNumero(cuentaOrigen, numeroTelefono, saldoTransferencia);
        return ResponseEntity.ok("Tranferencia realizada con éxito");
    }

}
