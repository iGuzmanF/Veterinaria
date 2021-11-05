/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ujmd.veterinaria.controladores;

import edu.ujmd.veterinaria.controladores.exceptions.IllegalOrphanException;
import edu.ujmd.veterinaria.controladores.exceptions.NonexistentEntityException;
import edu.ujmd.veterinaria.controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.ujmd.veterinaria.entidades.Historialmedico;
import edu.ujmd.veterinaria.entidades.Vacunas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author meev9
 */
public class VacunasJpaController implements Serializable {
    private EntityManagerFactory emf = null;


    public VacunasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
     public VacunasJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Proyecto-VeterinariaPU");
    }

    public void create(Vacunas vacunas) throws PreexistingEntityException, Exception {
        if (vacunas.getHistorialmedicoList() == null) {
            vacunas.setHistorialmedicoList(new ArrayList<Historialmedico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Historialmedico> attachedHistorialmedicoList = new ArrayList<Historialmedico>();
            for (Historialmedico historialmedicoListHistorialmedicoToAttach : vacunas.getHistorialmedicoList()) {
                historialmedicoListHistorialmedicoToAttach = em.getReference(historialmedicoListHistorialmedicoToAttach.getClass(), historialmedicoListHistorialmedicoToAttach.getCodHistorial());
                attachedHistorialmedicoList.add(historialmedicoListHistorialmedicoToAttach);
            }
            vacunas.setHistorialmedicoList(attachedHistorialmedicoList);
            em.persist(vacunas);
            for (Historialmedico historialmedicoListHistorialmedico : vacunas.getHistorialmedicoList()) {
                Vacunas oldCodVacunasOfHistorialmedicoListHistorialmedico = historialmedicoListHistorialmedico.getCodVacunas();
                historialmedicoListHistorialmedico.setCodVacunas(vacunas);
                historialmedicoListHistorialmedico = em.merge(historialmedicoListHistorialmedico);
                if (oldCodVacunasOfHistorialmedicoListHistorialmedico != null) {
                    oldCodVacunasOfHistorialmedicoListHistorialmedico.getHistorialmedicoList().remove(historialmedicoListHistorialmedico);
                    oldCodVacunasOfHistorialmedicoListHistorialmedico = em.merge(oldCodVacunasOfHistorialmedicoListHistorialmedico);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVacunas(vacunas.getCodVacunas()) != null) {
                throw new PreexistingEntityException("Vacunas " + vacunas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vacunas vacunas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vacunas persistentVacunas = em.find(Vacunas.class, vacunas.getCodVacunas());
            List<Historialmedico> historialmedicoListOld = persistentVacunas.getHistorialmedicoList();
            List<Historialmedico> historialmedicoListNew = vacunas.getHistorialmedicoList();
            List<String> illegalOrphanMessages = null;
            for (Historialmedico historialmedicoListOldHistorialmedico : historialmedicoListOld) {
                if (!historialmedicoListNew.contains(historialmedicoListOldHistorialmedico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Historialmedico " + historialmedicoListOldHistorialmedico + " since its codVacunas field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Historialmedico> attachedHistorialmedicoListNew = new ArrayList<Historialmedico>();
            for (Historialmedico historialmedicoListNewHistorialmedicoToAttach : historialmedicoListNew) {
                historialmedicoListNewHistorialmedicoToAttach = em.getReference(historialmedicoListNewHistorialmedicoToAttach.getClass(), historialmedicoListNewHistorialmedicoToAttach.getCodHistorial());
                attachedHistorialmedicoListNew.add(historialmedicoListNewHistorialmedicoToAttach);
            }
            historialmedicoListNew = attachedHistorialmedicoListNew;
            vacunas.setHistorialmedicoList(historialmedicoListNew);
            vacunas = em.merge(vacunas);
            for (Historialmedico historialmedicoListNewHistorialmedico : historialmedicoListNew) {
                if (!historialmedicoListOld.contains(historialmedicoListNewHistorialmedico)) {
                    Vacunas oldCodVacunasOfHistorialmedicoListNewHistorialmedico = historialmedicoListNewHistorialmedico.getCodVacunas();
                    historialmedicoListNewHistorialmedico.setCodVacunas(vacunas);
                    historialmedicoListNewHistorialmedico = em.merge(historialmedicoListNewHistorialmedico);
                    if (oldCodVacunasOfHistorialmedicoListNewHistorialmedico != null && !oldCodVacunasOfHistorialmedicoListNewHistorialmedico.equals(vacunas)) {
                        oldCodVacunasOfHistorialmedicoListNewHistorialmedico.getHistorialmedicoList().remove(historialmedicoListNewHistorialmedico);
                        oldCodVacunasOfHistorialmedicoListNewHistorialmedico = em.merge(oldCodVacunasOfHistorialmedicoListNewHistorialmedico);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vacunas.getCodVacunas();
                if (findVacunas(id) == null) {
                    throw new NonexistentEntityException("The vacunas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vacunas vacunas;
            try {
                vacunas = em.getReference(Vacunas.class, id);
                vacunas.getCodVacunas();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vacunas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Historialmedico> historialmedicoListOrphanCheck = vacunas.getHistorialmedicoList();
            for (Historialmedico historialmedicoListOrphanCheckHistorialmedico : historialmedicoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vacunas (" + vacunas + ") cannot be destroyed since the Historialmedico " + historialmedicoListOrphanCheckHistorialmedico + " in its historialmedicoList field has a non-nullable codVacunas field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(vacunas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vacunas> findVacunasEntities() {
        return findVacunasEntities(true, -1, -1);
    }

    public List<Vacunas> findVacunasEntities(int maxResults, int firstResult) {
        return findVacunasEntities(false, maxResults, firstResult);
    }

    private List<Vacunas> findVacunasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vacunas.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Vacunas findVacunas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vacunas.class, id);
        } finally {
            em.close();
        }
    }

    public int getVacunasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vacunas> rt = cq.from(Vacunas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
