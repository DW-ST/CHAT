package com.soporte.service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
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

public Map<String,Object> calcularIndicadores(String inicio, String fin){

List<Solicitud> lista;

if(inicio != null && fin != null){

LocalDateTime i = LocalDate.parse(inicio).atStartOfDay();
LocalDateTime f = LocalDate.parse(fin).atTime(23,59);

lista = repository.findByFechaCreacionBetween(i,f);

}else{

lista = repository.findAll();

}

long oportunas = 0;
long alerta = 0;
long inoportunas = 0;

double totalHoras = 0;
long respondidas = 0;

for(Solicitud s : lista){

double horas = s.getHorasTranscurridas();

if(horas <= 1) oportunas++;
else if(horas < 3) alerta++;
else inoportunas++;

if(s.getFechaRespuesta() != null){

double respuesta = Duration
.between(s.getFechaCreacion(), s.getFechaRespuesta())
.toMinutes()/60.0;

totalHoras += respuesta;
respondidas++;

}

}

double promedio = respondidas > 0 ? totalHoras/respondidas : 0;

Map<String,Object> r = new HashMap<>();

r.put("oportunas", oportunas);
r.put("alerta", alerta);
r.put("inoportunas", inoportunas);
r.put("promedio", promedio);

return r;

}


}