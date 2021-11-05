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
import edu.ujmd.veterinaria.entidades.Clientes;
import edu.ujmd.veterinaria.entidades.Historialmedico;
import edu.ujmd.veterinaria.entidades.Mascotas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author meev9
 */
public class MascotasJpaController implements Serializable {

     private EntityManagerFactory emf = null;
     
    public MascotasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
     public MascotasJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Proyecto-VeterinariaPU");
    }
   

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mascotas mascotas) {
        if (mascotas.getHistorialmedicoList() == null) {
            mascotas.setHistorialmedicoList(new ArrayList<Historialmedico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes codClientes = mascotas.getCodClientes();
            if (codClientes != null) {
                codClientes = em.getReference(codClientes.getClass(), codClientes.getCodClientes());
                mascotas.setCodClientes(codClientes);
            }
            List<Historialmedico> attachedHistorialmedicoList = new ArrayList<Historialmedico>();
            for (Historialmedico historialmedicoListHistorialmedicoToAttach : mascotas.getHistorialmedicoList()) {
                historialmedicoListHistorialmedicoToAttach = em.getReference(historialmedicoListHistorialmedicoToAttach.getClass(), historialmedicoListHistorialmedicoToAttach.getCodHistorial());
                attachedHistorialmedicoList.add(historialmedicoListHistorialmedicoToAttach);
            }
            mascotas.setHistorialmedicoList(attachedHistorialmedicoList);
            em.persist(mascotas);
            if (codClientes != null) {
                codClientes.getMascotasList().add(mascotas);
                codClientes = em.merge(codClientes);
            }
            for (Historialmedico historialmedicoListHistorialmedico : mascotas.getHistorialmedicoList()) {
                Mascotas oldCodMascotasOfHistorialmedicoListHistorialmedico = historialmedicoListHistorialmedico.getCodMascotas();
                historialmedicoListHistorialmedico.setCodMascotas(mascotas);
                historialmedicoListHistorialmedico = em.merge(historialmedicoListHistorialmedico);
                if (oldCodMascotasOfHistorialmedicoListHistorialmedico != null) {
                    oldCodMascotasOfHistorialmedicoListHistorialmedico.getHistorialmedicoList().remove(historialmedicoListHistorialmedico);
                    oldCodMascotasOfHistorialmedicoListHistorialmedico = em.merge(oldCodMascotasOfHistorialmedicoListHistorialmedico);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mascotas mascotas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mascotas persistentMascotas = em.find(Mascotas.class, mascotas.getCodMascotas());
            Clientes codClientesOld = persistentMascotas.getCodClientes();
            Clientes codClientesNew = mascotas.getCodClientes();
            List<Historialmedico> historialmedicoListOld = persistentMascotas.getHistorialmedicoList();
            List<Historialmedico> historialmedicoListNew = mascotas.getHistorialmedicoList();
            List<String> illegalOrphanMessages = null;
            for (Historialmedico historialmedicoListOldHistorialmedico : historialmedicoListOld) {
                if (!historialmedicoListNew.contains(historialmedicoListOldHistorialmedico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Historialmedico " + historialmedicoListOldHistorialmedico + " since its codMascotas field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codClientesNew != null) {
                codClientesNew = em.getReference(codClientesNew.getClass(), codClientesNew.getCodClientes());
                mascotas.setCodClientes(codClientesNew);
            }
            List<Historialmedico> attachedHistorialmedicoListNew = new ArrayList<Historialmedico>();
            for (Historialmedico historialmedicoListNewHistorialmedicoToAttach : historialmedicoListNew) {
                historialmedicoListNewHistorialmedicoToAttach = em.getReference(historialmedicoListNewHistorialmedicoToAttach.getClass(), historialmedicoListNewHistorialmedicoToAttach.getCodHistorial());
                attachedHistorialmedicoListNew.add(historialmedicoListNewHistorialmedicoToAttach);
            }
            historialmedicoListNew = attachedHistorialmedicoListNew;
            mascotas.setHistorialmedicoList(historialmedicoListNew);
            mascotas = em.merge(mascotas);
            if (codClientesOld != null && !codClientesOld.equals(codClientesNew)) {
                codClientesOld.getMascotasList().remove(mascotas);
                codClientesOld = em.merge(codClientesOld);
            }
            if (codClientesNew != null && !codClientesNew.equals(codClientesOld)) {
                codClientesNew.getMascotasList().add(mascotas);
                codClientesNew = em.merge(codClientesNew);
            }
            for (Historialmedico historialmedicoListNewHistorialmedico : historialmedicoListNew) {
                if (!historialmedicoListOld.contains(historialmedicoListNewHistorialmedico)) {
                    Mascotas oldCodMascotasOfHistorialmedicoListNewHistorialmedico = historialmedicoListNewHistorialmedico.getCodMascotas();
                    historialmedicoListNewHistorialmedico.setCodMascotas(mascotas);
                    historialmedicoListNewHistorialmedico = em.merge(historialmedicoListNewHistorialmedico);
                    if (oldCodMascotasOfHistorialmedicoListNewHistorialmedico != null && !oldCodMascotasOfHistorialmedicoListNewHistorialmedico.equals(mascotas)) {
                        oldCodMascotasOfHistorialmedicoListNewHistorialmedico.getHistorialmedicoList().remove(historialmedicoListNewHistorialmedico);
                        oldCodMascotasOfHistorialmedicoListNewHistorialmedico = em.merge(oldCodMascotasOfHistorialmedicoListNewHistorialmedico);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mascotas.getCodMascotas();
                if (findMascotas(id) == null) {
                    throw new NonexistentEntityException("The mascotas with id " + id + " no longer exists.");
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
            Mascotas mascotas;
            try {
                mascotas = em.getReference(Mascotas.class, id);
                mascotas.getCodMascotas();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mascotas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Historialmedico> historialmedicoListOrphanCheck = mascotas.getHistorialmedicoList();
            for (Historialmedico historialmedicoListOrphanCheckHistorialmedico : historialmedicoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Mascotas (" + mascotas + ") cannot be destroyed since the Historialmedico " + historialmedicoListOrphanCheckHistorialmedico + " in its historialmedicoList field has a non-nullable codMascotas field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Clientes codClientes = mascotas.getCodClientes();
            if (codClientes != null) {
                codClientes.getMascotasList().remove(mascotas);
                codClientes = em.merge(codClientes);
            }
            em.remove(mascotas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mascotas> findMascotasEntities() {
        return findMascotasEntities(true, -1, -1);
    }

    public List<Mascotas> findMascotasEntities(int maxResults, int firstResult) {
        return findMascotasEntities(false, maxResults, firstResult);
    }

    private List<Mascotas> findMascotasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mascotas.class));
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

    public Mascotas findMascotas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mascotas.class, id);
        } finally {
            em.close();
        }
    }

    public int getMascotasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mascotas> rt = cq.from(Mascotas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
