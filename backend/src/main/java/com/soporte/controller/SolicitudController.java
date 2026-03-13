package com.soporte.controller;

import java.util.List;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soporte.model.Solicitud;
import com.soporte.service.SolicitudService;

@RestController
@RequestMapping("/api/solicitudes")
@CrossOrigin(origins = "*")
public class SolicitudController {

    @Autowired
    private SolicitudService service;

    @PostMapping
    public Solicitud crear(@RequestBody Solicitud s) {
        return service.guardar(s);
    }

    @GetMapping
    public List<Solicitud> listar() {
        return service.listarTodas();
    }
    
    @GetMapping("/indicadores")
    public Map<String, Object> indicadores(
    @RequestParam(required = false) String inicio,
    @RequestParam(required = false) String fin
    ) {

    return service.calcularIndicadores(inicio, fin);

    }
    
    @GetMapping("/fifo")
    public List<Solicitud> fifo() {
        return service.listarFIFO();
    }

    @GetMapping("/prioridad")
    public List<Solicitud> prioridad() {
        return service.listarPorPrioridad();
    }

    @GetMapping("/indicador/inoportunas")
    public double indicador() {
        return service.porcentajeInoportunas();
    }
    @PutMapping("/atender/{id}")
public Solicitud atender(@PathVariable Long id) {
    return service.atender(id);
}
}