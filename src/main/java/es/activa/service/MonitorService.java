package es.activa.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import es.activa.config.JPAUtil;
import es.activa.domain.Monitor;

public class MonitorService {

    // Método para crear un monitor.
    public Long crearMonitor(String nombre, String especialidad) {

        // Comprobar que los campos contienen texto.
        if (nombre == null || nombre.trim().isEmpty()
                || especialidad == null || especialidad.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre y la especialidad son obligatorios.");
        }

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            // Iniciar la transacción.
            tx.begin();
            // Crear y guardar el monitor.
            Monitor monitor = new Monitor(
                    nombre.trim(), especialidad.trim());
            em.persist(monitor);
            // Confirmar el alta.
            tx.commit();

            return monitor.getId();

        } catch (RuntimeException e) {

            // Deshacer los cambios si ocurre un error.
            if (tx.isActive()) {
                tx.rollback();
            }

            throw e;

        } finally {

            // Cerrar el gestor de persistencia.
            em.close();
        }
    }
}