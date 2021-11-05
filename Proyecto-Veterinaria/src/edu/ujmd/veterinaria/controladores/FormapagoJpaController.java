/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ujmd.veterinaria.controladores;

import edu.ujmd.veterinaria.controladores.exceptions.IllegalOrphanException;
import edu.ujmd.veterinaria.controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.ujmd.veterinaria.entidades.Detallefactura;
import edu.ujmd.veterinaria.entidades.Formapago;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author meev9
 */
public class FormapagoJpaController implements Serializable {

     private EntityManagerFactory emf = null;
    public FormapagoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
   

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
     public FormapagoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Proyecto-VeterinariaPU");
    }

    public void create(Formapago formapago) {
        if (formapago.getDetallefacturaList() == null) {
            formapago.setDetallefacturaList(new ArrayList<Detallefactura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Detallefactura> attachedDetallefacturaList = new ArrayList<Detallefactura>();
            for (Detallefactura detallefacturaListDetallefacturaToAttach : formapago.getDetallefacturaList()) {
                detallefacturaListDetallefacturaToAttach = em.getReference(detallefacturaListDetallefacturaToAttach.getClass(), detallefacturaListDetallefacturaToAttach.getCodDetalle());
                attachedDetallefacturaList.add(detallefacturaListDetallefacturaToAttach);
            }
            formapago.setDetallefacturaList(attachedDetallefacturaList);
            em.persist(formapago);
            for (Detallefactura detallefacturaListDetallefactura : formapago.getDetallefacturaList()) {
                Formapago oldCodFormapagoOfDetallefacturaListDetallefactura = detallefacturaListDetallefactura.getCodFormapago();
                detallefacturaListDetallefactura.setCodFormapago(formapago);
                detallefacturaListDetallefactura = em.merge(detallefacturaListDetallefactura);
                if (oldCodFormapagoOfDetallefacturaListDetallefactura != null) {
                    oldCodFormapagoOfDetallefacturaListDetallefactura.getDetallefacturaList().remove(detallefacturaListDetallefactura);
                    oldCodFormapagoOfDetallefacturaListDetallefactura = em.merge(oldCodFormapagoOfDetallefacturaListDetallefactura);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Formapago formapago) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Formapago persistentFormapago = em.find(Formapago.class, formapago.getCodFormapago());
            List<Detallefactura> detallefacturaListOld = persistentFormapago.getDetallefacturaList();
            List<Detallefactura> detallefacturaListNew = formapago.getDetallefacturaList();
            List<String> illegalOrphanMessages = null;
            for (Detallefactura detallefacturaListOldDetallefactura : detallefacturaListOld) {
                if (!detallefacturaListNew.contains(detallefacturaListOldDetallefactura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Detallefactura " + detallefacturaListOldDetallefactura + " since its codFormapago field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Detallefactura> attachedDetallefacturaListNew = new ArrayList<Detallefactura>();
            for (Detallefactura detallefacturaListNewDetallefacturaToAttach : detallefacturaListNew) {
                detallefacturaListNewDetallefacturaToAttach = em.getReference(detallefacturaListNewDetallefacturaToAttach.getClass(), detallefacturaListNewDetallefacturaToAttach.getCodDetalle());
                attachedDetallefacturaListNew.add(detallefacturaListNewDetallefacturaToAttach);
            }
            detallefacturaListNew = attachedDetallefacturaListNew;
            formapago.setDetallefacturaList(detallefacturaListNew);
            formapago = em.merge(formapago);
            for (Detallefactura detallefacturaListNewDetallefactura : detallefacturaListNew) {
                if (!detallefacturaListOld.contains(detallefacturaListNewDetallefactura)) {
                    Formapago oldCodFormapagoOfDetallefacturaListNewDetallefactura = detallefacturaListNewDetallefactura.getCodFormapago();
                    detallefacturaListNewDetallefactura.setCodFormapago(formapago);
                    detallefacturaListNewDetallefactura = em.merge(detallefacturaListNewDetallefactura);
                    if (oldCodFormapagoOfDetallefacturaListNewDetallefactura != null && !oldCodFormapagoOfDetallefacturaListNewDetallefactura.equals(formapago)) {
                        oldCodFormapagoOfDetallefacturaListNewDetallefactura.getDetallefacturaList().remove(detallefacturaListNewDetallefactura);
                        oldCodFormapagoOfDetallefacturaListNewDetallefactura = em.merge(oldCodFormapagoOfDetallefacturaListNewDetallefactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = formapago.getCodFormapago();
                if (findFormapago(id) == null) {
                    throw new NonexistentEntityException("The formapago with id " + id + " no longer exists.");
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
            Formapago formapago;
            try {
                formapago = em.getReference(Formapago.class, id);
                formapago.getCodFormapago();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The formapago with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Detallefactura> detallefacturaListOrphanCheck = formapago.getDetallefacturaList();
            for (Detallefactura detallefacturaListOrphanCheckDetallefactura : detallefacturaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Formapago (" + formapago + ") cannot be destroyed since the Detallefactura " + detallefacturaListOrphanCheckDetallefactura + " in its detallefacturaList field has a non-nullable codFormapago field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(formapago);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Formapago> findFormapagoEntities() {
        return findFormapagoEntities(true, -1, -1);
    }

    public List<Formapago> findFormapagoEntities(int maxResults, int firstResult) {
        return findFormapagoEntities(false, maxResults, firstResult);
    }

    private List<Formapago> findFormapagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Formapago.class));
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

    public Formapago findFormapago(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Formapago.class, id);
        } finally {
            em.close();
        }
    }

    public int getFormapagoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Formapago> rt = cq.from(Formapago.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
