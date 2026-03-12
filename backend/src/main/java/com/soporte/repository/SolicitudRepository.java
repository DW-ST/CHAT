package com.soporte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soporte.model.Solicitud;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    List<Solicitud> findByEstadoOrderByFechaCreacionAsc(String estado);

    List<Solicitud> findAllByOrderByPrioridadAscFechaCreacionAsc();
}