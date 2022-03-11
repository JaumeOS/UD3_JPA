package controller;

import ajudes.JPAException;
import models.Centre;
import models.Preferencia;
import org.hibernate.boot.MappingException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class Persistencia {
    private String unitatPersistencia;

    public Persistencia(String unitatPersistencia) throws JPAException {
        super();
        if (unitatPersistencia == null || unitatPersistencia.trim().equals("")) {
            throw new JPAException("La unitat de persistència no pot ser buida");
        }
        this.unitatPersistencia = unitatPersistencia;
    }

    public String getUnitatPersistencia() {
        return unitatPersistencia;
    }

    public Object retornaObjecte(String pk) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(unitatPersistencia);
        EntityManager em = emf.createEntityManager();
        Object object = em.find(Centre.class, pk);
        em.close();
        emf.close();
        return object;
    }

    public void creaPreferencia(String nif, String idCos, String idEspecialitat, String idCentre, int ordre) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(unitatPersistencia);
        EntityManager em = emf.createEntityManager();
        Preferencia novaPreferencia = new Preferencia(nif, idCos, idEspecialitat, idCentre, ordre);
        em.getTransaction().begin();
        try {
            em.persist(novaPreferencia);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            try {
                throw new JPAException(e.getMessage());
            } catch (JPAException ex) {
                ex.printStackTrace();
            }
        } finally {
            em.close();
            emf.close();
        }
    }

    public ArrayList<Object[]> retornaNoms(int inici, int quantitat) throws JPAException {
        List resultat = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(unitatPersistencia);
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNamedQuery("Centre.getNomLocalitatNomCentre")
                    .setFirstResult(inici)
                    .setMaxResults(quantitat);
            resultat = query.getResultList();
        } catch (MappingException ex) {
            throw new JPAException(ex.getMessage());
        } finally {
            em.close();
            emf.close();
        }
        return (ArrayList<Object[]>) resultat;
    }
}
