package com.soporte.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soporte.model.Solicitud;
import com.soporte.repository.SolicitudRepository;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository repository;

    public Solicitud guardar(Solicitud s) {
    System.out.println("Entró al método guardar");
    Solicitud guardada = repository.save(s);
    System.out.println("Guardado ID: " + guardada.getId());
    return guardada;
    }

    public List<Solicitud> listarFIFO() {
        return repository.findByEstadoOrderByFechaCreacionAsc("PENDIENTE");
    }

    public List<Solicitud> listarPorPrioridad() {
        return repository.findAllByOrderByPrioridadAscFechaCreacionAsc();
    }

    public double porcentajeInoportunas() {
        List<Solicitud> todas = repository.findAll();
        long inoportunas = todas.stream()
                .filter(s -> s.getHorasTranscurridas() >= 3)
                .count();
        return (inoportunas * 100.0) / todas.size();
    }

    public List<Solicitud> listarTodas() {
    return repository.findAll();
}
public Solicitud atender(Long id) {
    Solicitud s = repository.findById(id).orElseThrow();
    s.setEstado("ATENDIDA");
    s.setFechaRespuesta(LocalDateTime.now());
    return repository.save(s);
}
}