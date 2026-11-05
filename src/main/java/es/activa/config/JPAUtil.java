package es.activa.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

// Clase encargada de gestionar la persistencia JPA.
public class JPAUtil {

    // Fábrica que crea los EntityManager.
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("ActivaPU");

    // Método para obtener un EntityManager.
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Método para cerrar la fábrica de persistencia.
    public static void cerrar() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}