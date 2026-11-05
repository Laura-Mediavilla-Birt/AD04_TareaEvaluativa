package es.activa.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import es.activa.config.JPAUtil;
import es.activa.domain.Cliente;

public class ClienteService {

    public Long crearCliente(String nombre, String email) {

        if (nombre == null || nombre.trim().isEmpty()
                || email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre y el email son obligatorios.");
        }

        email = email.trim();

        if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IllegalArgumentException("El email no es válido.");
        }

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Comprobar si el email ya está registrado.
            Long existentes = em.createQuery(
                    "SELECT COUNT(c) FROM Cliente c "
                    + "WHERE LOWER(c.email) = LOWER(:email)",
                    Long.class)
                    .setParameter("email", email)
                    .getSingleResult();

            if (existentes > 0) {
                throw new IllegalArgumentException(
                        "Ya existe un cliente con ese email.");
            }

            // Crear y guardar el cliente.
            Cliente cliente = new Cliente(nombre.trim(), email);
            em.persist(cliente);

            tx.commit();

            return cliente.getId();

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