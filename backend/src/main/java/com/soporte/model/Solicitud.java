package com.soporte.model;

import java.time.Duration;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Solicitud {

    @JsonProperty("tiempoFormateado")
public String getTiempoFormateado() {

    LocalDateTime fin = (estado.equals("ATENDIDA") && fechaRespuesta != null)
            ? fechaRespuesta
            : LocalDateTime.now();

    Duration duracion = Duration.between(fechaCreacion, fin);

    long horas = duracion.toHours();
    long minutos = duracion.toMinutes() % 60;

    return horas + "h " + minutos + "m";
}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuario;
    private String mensaje;
    private int prioridad; // 1 Alta, 2 Media, 3 Baja
    private String estado = "PENDIENTE";

    private LocalDateTime fechaCreacion = LocalDateTime.now();
    private LocalDateTime fechaRespuesta;

    // ===== GETTERS =====

    public Long getId() { return id; }

    public String getUsuario() { return usuario; }

    public String getMensaje() { return mensaje; }

    public int getPrioridad() { return prioridad; }

    public String getEstado() { return estado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }

    public LocalDateTime getFechaRespuesta() { return fechaRespuesta; }

    // ===== SETTERS =====

    public void setUsuario(String usuario) { this.usuario = usuario; }

    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public void setPrioridad(int prioridad) { this.prioridad = prioridad; }

    public void setEstado(String estado) { this.estado = estado; }

    public void setFechaRespuesta(LocalDateTime fechaRespuesta) { this.fechaRespuesta = fechaRespuesta; }

    // ===== CÁLCULO EXACTO EN DECIMALES =====

    public double getHorasTranscurridas() {
        Duration duracion = Duration.between(fechaCreacion, LocalDateTime.now());
        return duracion.toMinutes() / 60.0;
    }
}
