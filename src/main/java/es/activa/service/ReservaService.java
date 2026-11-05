package es.activa.service;

import java.time.LocalDateTime;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import es.activa.config.JPAUtil;
import es.activa.domain.Actividad;
import es.activa.domain.Cliente;
import es.activa.domain.Reserva;

public class ReservaService {

    // Método para crear una reserva.
    public Reserva crearReserva(Long idCliente, Long idActividad,
            int numeroPlazas) {

        if (idCliente == null || idActividad == null || numeroPlazas <= 0) {
            throw new IllegalArgumentException(
                    "Los datos de la reserva no son válidos.");
        }

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Buscar el cliente.
            Cliente cliente = em.find(Cliente.class, idCliente);

            if (cliente == null) {
                throw new IllegalArgumentException(
                        "No existe el cliente indicado.");
            }

            // Buscar la actividad.
            Actividad actividad = em.find(Actividad.class, idActividad);

            if (actividad == null) {
                throw new IllegalArgumentException(
                        "No existe la actividad indicada.");
            }

            // Comprobar que hay plazas suficientes.
            if (actividad.getPlazasDisponibles() < numeroPlazas) {
                throw new IllegalArgumentException(
                        "No hay suficientes plazas disponibles.");
            }

            // Crear la reserva.
            Reserva reserva = new Reserva();
            reserva.setFechaReserva(LocalDateTime.now());
            reserva.setNumeroPlazas(numeroPlazas);
            reserva.setActividad(actividad);
            reserva.setCliente(cliente);

            // Guardar la reserva.
            em.persist(reserva);

            // Descontar las plazas.
            actividad.setPlazasDisponibles(
                    actividad.getPlazasDisponibles() - numeroPlazas);

            tx.commit();

            return reserva;

        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;

        } finally {
            em.close();
        }
    }

    // Método para cancelar una reserva.
    public Reserva cancelarReserva(Long idReserva) {

        if (idReserva == null) {
            throw new IllegalArgumentException(
                    "El ID de la reserva es obligatorio.");
        }

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Buscar la reserva.
            Reserva reserva = em.find(Reserva.class, idReserva);

            if (reserva == null) {
                throw new IllegalArgumentException(
                        "No existe la reserva indicada.");
            }

            // Recuperar la actividad asociada.
            Actividad actividad = reserva.getActividad();

            // Devolver las plazas reservadas.
            actividad.setPlazasDisponibles(
                    actividad.getPlazasDisponibles()
                    + reserva.getNumeroPlazas());

            // Eliminar la reserva.
            em.remove(reserva);

            // Confirmar los cambios.
            tx.commit();

            return reserva;

        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;

        } finally {
            em.close();
        }
    }
}