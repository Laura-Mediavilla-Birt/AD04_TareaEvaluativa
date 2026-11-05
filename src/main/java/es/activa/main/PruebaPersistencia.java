package es.activa.main;

import jakarta.persistence.EntityManager;
import es.activa.config.JPAUtil;

public class PruebaPersistencia {

    public static void main(String[] args) {

        EntityManager em = null;

        try {
            em = JPAUtil.getEntityManager();

            System.out.println("Hibernate iniciado correctamente.");
            System.out.println("Las entidades se han reconocido.");

        } catch (Exception e) {

            System.out.println("Error al iniciar la persistencia.");
            e.printStackTrace();

        } finally {

            if (em != null && em.isOpen()) {
                em.close();
            }

            JPAUtil.cerrar();
        }
    }
}