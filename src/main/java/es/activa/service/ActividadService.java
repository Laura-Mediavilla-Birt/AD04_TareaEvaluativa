package es.activa.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import es.activa.config.JPAUtil;
import es.activa.domain.Actividad;
import es.activa.domain.Monitor;

public class ActividadService {

    // Método para crear una actividad.
    public Long crearActividad(String nombre, String localidad,
            LocalDateTime fechaHora, int plazasTotales, Long idMonitor) {

        // Comprobar los datos obligatorios.
        if (nombre == null || nombre.trim().isEmpty()
                || localidad == null || localidad.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre y la localidad son obligatorios.");
        }

        if (fechaHora == null || plazasTotales <= 0 || idMonitor == null) {
            throw new IllegalArgumentException(
                    "La fecha, las plazas y el monitor son obligatorios.");
        }

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Buscar el monitor.
            Monitor monitor = em.find(Monitor.class, idMonitor);

            if (monitor == null) {
                throw new IllegalArgumentException(
                        "No existe el monitor indicado.");
            }

            // Crear la actividad con todas las plazas disponibles.
            Actividad actividad = new Actividad(
                    nombre.trim(),
                    localidad.trim(),
                    fechaHora,
                    plazasTotales,
                    plazasTotales,
                    monitor
            );

            // Guardar la actividad.
            em.persist(actividad);

            // Mantener coherente la relación bidireccional.
            monitor.getActividades().add(actividad);

            tx.commit();

            return actividad.getId();

        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;

        } finally {
            em.close();
        }
    }

    // Buscar actividades disponibles por localidad, día y plazas necesarias.
    public List<Actividad> buscarDisponibles(
            String localidad, LocalDate dia, int plazasNecesarias) {

        // Comprobar los datos de búsqueda.
        if (localidad == null || localidad.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "La localidad es obligatoria.");
        }

        if (dia == null || plazasNecesarias <= 0) {
            throw new IllegalArgumentException(
                    "El día y el número de plazas deben ser válidos.");
        }

        EntityManager em = JPAUtil.getEntityManager();

        try {
            // Consulta JPQL parametrizada.
            return em.createQuery(
                    "SELECT a FROM Actividad a "
                    + "WHERE LOWER(a.localidad) = LOWER(:localidad) "
                    + "AND a.fechaHora >= :inicio "
                    + "AND a.fechaHora < :fin "
                    + "AND a.plazasDisponibles >= :plazas "
                    + "ORDER BY a.fechaHora",
                    Actividad.class)
                    .setParameter("localidad", localidad.trim())
                    .setParameter("inicio", dia.atStartOfDay())
                    .setParameter("fin", dia.plusDays(1).atStartOfDay())
                    .setParameter("plazas", plazasNecesarias)
                    .getResultList();

        } finally {
            em.close();
        }
    }

    // Método para listar todas las actividades.
    public List<Actividad> listarActividades() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT a FROM Actividad a ORDER BY a.fechaHora",
                    Actividad.class)
                    .getResultList();

        } finally {
            em.close();
        }
    }
}