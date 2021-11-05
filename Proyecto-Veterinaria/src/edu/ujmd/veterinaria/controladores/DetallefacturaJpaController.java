/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ujmd.veterinaria.controladores;

import edu.ujmd.veterinaria.controladores.exceptions.NonexistentEntityException;
import edu.ujmd.veterinaria.entidades.Detallefactura;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.ujmd.veterinaria.entidades.Factura;
import edu.ujmd.veterinaria.entidades.Formapago;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author meev9
 */
public class DetallefacturaJpaController implements Serializable {

      private EntityManagerFactory emf = null;
    public DetallefacturaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
     public DetallefacturaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Proyecto-VeterinariaPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Detallefactura detallefactura) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factura codFactura = detallefactura.getCodFactura();
            if (codFactura != null) {
                codFactura = em.getReference(codFactura.getClass(), codFactura.getCodFactura());
                detallefactura.setCodFactura(codFactura);
            }
            Formapago codFormapago = detallefactura.getCodFormapago();
            if (codFormapago != null) {
                codFormapago = em.getReference(codFormapago.getClass(), codFormapago.getCodFormapago());
                detallefactura.setCodFormapago(codFormapago);
            }
            em.persist(detallefactura);
            if (codFactura != null) {
                codFactura.getDetallefacturaList().add(detallefactura);
                codFactura = em.merge(codFactura);
            }
            if (codFormapago != null) {
                codFormapago.getDetallefacturaList().add(detallefactura);
                codFormapago = em.merge(codFormapago);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Detallefactura detallefactura) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Detallefactura persistentDetallefactura = em.find(Detallefactura.class, detallefactura.getCodDetalle());
            Factura codFacturaOld = persistentDetallefactura.getCodFactura();
            Factura codFacturaNew = detallefactura.getCodFactura();
            Formapago codFormapagoOld = persistentDetallefactura.getCodFormapago();
            Formapago codFormapagoNew = detallefactura.getCodFormapago();
            if (codFacturaNew != null) {
                codFacturaNew = em.getReference(codFacturaNew.getClass(), codFacturaNew.getCodFactura());
                detallefactura.setCodFactura(codFacturaNew);
            }
            if (codFormapagoNew != null) {
                codFormapagoNew = em.getReference(codFormapagoNew.getClass(), codFormapagoNew.getCodFormapago());
                detallefactura.setCodFormapago(codFormapagoNew);
            }
            detallefactura = em.merge(detallefactura);
            if (codFacturaOld != null && !codFacturaOld.equals(codFacturaNew)) {
                codFacturaOld.getDetallefacturaList().remove(detallefactura);
                codFacturaOld = em.merge(codFacturaOld);
            }
            if (codFacturaNew != null && !codFacturaNew.equals(codFacturaOld)) {
                codFacturaNew.getDetallefacturaList().add(detallefactura);
                codFacturaNew = em.merge(codFacturaNew);
            }
            if (codFormapagoOld != null && !codFormapagoOld.equals(codFormapagoNew)) {
                codFormapagoOld.getDetallefacturaList().remove(detallefactura);
                codFormapagoOld = em.merge(codFormapagoOld);
            }
            if (codFormapagoNew != null && !codFormapagoNew.equals(codFormapagoOld)) {
                codFormapagoNew.getDetallefacturaList().add(detallefactura);
                codFormapagoNew = em.merge(codFormapagoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detallefactura.getCodDetalle();
                if (findDetallefactura(id) == null) {
                    throw new NonexistentEntityException("The detallefactura with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Detallefactura detallefactura;
            try {
                detallefactura = em.getReference(Detallefactura.class, id);
                detallefactura.getCodDetalle();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detallefactura with id " + id + " no longer exists.", enfe);
            }
            Factura codFactura = detallefactura.getCodFactura();
            if (codFactura != null) {
                codFactura.getDetallefacturaList().remove(detallefactura);
                codFactura = em.merge(codFactura);
            }
            Formapago codFormapago = detallefactura.getCodFormapago();
            if (codFormapago != null) {
                codFormapago.getDetallefacturaList().remove(detallefactura);
                codFormapago = em.merge(codFormapago);
            }
            em.remove(detallefactura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Detallefactura> findDetallefacturaEntities() {
        return findDetallefacturaEntities(true, -1, -1);
    }

    public List<Detallefactura> findDetallefacturaEntities(int maxResults, int firstResult) {
        return findDetallefacturaEntities(false, maxResults, firstResult);
    }

    private List<Detallefactura> findDetallefacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Detallefactura.class));
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

    public Detallefactura findDetallefactura(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Detallefactura.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetallefacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Detallefactura> rt = cq.from(Detallefactura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
