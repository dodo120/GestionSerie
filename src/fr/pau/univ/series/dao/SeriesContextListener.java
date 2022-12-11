package fr.pau.univ.series.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

//Cette classe permet de créer un contextListener. Elle utilise un Servlet, possible avec Tomcat et permet donc de s'occuper du côté
//serveur de ce projet. 
//Pour faire simple, un servlet = une classe Java qui permet de créer dynamiquement des données au sein d'un serveur HTTP.
//Je ne pense pas que vous ayez besoin de comprendre de A-Z le protocole HTTP, ni les relations Java <> Servlet <> Protocole HTTP <> Web, etc.
@WebListener
public class SeriesContextListener implements ServletContextListener {
    private static EntityManager em;

    /**
     * Initialise l'entity manager factory à l'aide de la classe Persistence.
     *
     * @param sce ServletContextEvent
     * @return instance de la classe EntityManagerFactory
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionSeries");
        em = emf.createEntityManager();
    }

    /**
     * Ferme la connexion à l'entity manager factory.
     *
     * @param sce ServletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (em != null) {
            em.getEntityManagerFactory().close();
        }
    }

    /**
     * Getter pour récupérer l'entity manager.
     *
     * @return instance de la classe EntityManager
     */
    public static EntityManager getEntityManager() {
        return em;
    }
}
