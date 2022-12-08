package fr.pau.univ.series.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;



@WebListener
public class SeriesContextListener implements ServletContextListener {
    private static EntityManager em;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionSeries");
        em = emf.createEntityManager();
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if(em != null) {
            em.getEntityManagerFactory().close();
        }
    }
    
    public static EntityManager getEntityManager() {
        return em;
        }
}
