package com.Banco.Cajero.Controller;

import com.Banco.Cajero.Entity.Cajero;
import com.Banco.Cajero.Entity.Transaccion;
import com.Banco.Cajero.Entity.Usuario;
import com.Banco.Cajero.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/usuario")
@CrossOrigin("*")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/inicio/{cuenta},{pin}")
    public ResponseEntity<List<Usuario>> inicioSeccion(@PathVariable String cuenta, @PathVariable String pin){
        var result = usuarioService.inicioSeccion(cuenta,pin);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/admin/{user},{pin}")
    public ResponseEntity<List<Usuario>> accesAdmin(@PathVariable String user, @PathVariable String pin){
        var result = usuarioService.accesAdmin(user,pin);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/saldoAc/{cuenta}")
    public ResponseEntity<List<Usuario>> saldoActual(@PathVariable String cuenta){
        var result = usuarioService.consultarSaldo(cuenta);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/reporte/{cuenta}")
    public ResponseEntity<List<Usuario>> reporteSaldo(@PathVariable String cuenta){
        var result = usuarioService.reporteSaldo(cuenta);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
