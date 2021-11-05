/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ujmd.veterinaria.controladores;

import edu.ujmd.veterinaria.controladores.exceptions.NonexistentEntityException;
import edu.ujmd.veterinaria.entidades.Citas;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.ujmd.veterinaria.entidades.Clientes;
import edu.ujmd.veterinaria.entidades.Empleados;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author meev9
 */
public class CitasJpaController implements Serializable {

     private EntityManagerFactory emf = null;
    public CitasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
     public CitasJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Proyecto-VeterinariaPU");
    }
   

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Citas citas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes codClientes = citas.getCodClientes();
            if (codClientes != null) {
                codClientes = em.getReference(codClientes.getClass(), codClientes.getCodClientes());
                citas.setCodClientes(codClientes);
            }
            Empleados codEmpleados = citas.getCodEmpleados();
            if (codEmpleados != null) {
                codEmpleados = em.getReference(codEmpleados.getClass(), codEmpleados.getCodEmpleados());
                citas.setCodEmpleados(codEmpleados);
            }
            em.persist(citas);
            if (codClientes != null) {
                codClientes.getCitasList().add(citas);
                codClientes = em.merge(codClientes);
            }
            if (codEmpleados != null) {
                codEmpleados.getCitasList().add(citas);
                codEmpleados = em.merge(codEmpleados);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Citas citas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Citas persistentCitas = em.find(Citas.class, citas.getCodCitas());
            Clientes codClientesOld = persistentCitas.getCodClientes();
            Clientes codClientesNew = citas.getCodClientes();
            Empleados codEmpleadosOld = persistentCitas.getCodEmpleados();
            Empleados codEmpleadosNew = citas.getCodEmpleados();
            if (codClientesNew != null) {
                codClientesNew = em.getReference(codClientesNew.getClass(), codClientesNew.getCodClientes());
                citas.setCodClientes(codClientesNew);
            }
            if (codEmpleadosNew != null) {
                codEmpleadosNew = em.getReference(codEmpleadosNew.getClass(), codEmpleadosNew.getCodEmpleados());
                citas.setCodEmpleados(codEmpleadosNew);
            }
            citas = em.merge(citas);
            if (codClientesOld != null && !codClientesOld.equals(codClientesNew)) {
                codClientesOld.getCitasList().remove(citas);
                codClientesOld = em.merge(codClientesOld);
            }
            if (codClientesNew != null && !codClientesNew.equals(codClientesOld)) {
                codClientesNew.getCitasList().add(citas);
                codClientesNew = em.merge(codClientesNew);
            }
            if (codEmpleadosOld != null && !codEmpleadosOld.equals(codEmpleadosNew)) {
                codEmpleadosOld.getCitasList().remove(citas);
                codEmpleadosOld = em.merge(codEmpleadosOld);
            }
            if (codEmpleadosNew != null && !codEmpleadosNew.equals(codEmpleadosOld)) {
                codEmpleadosNew.getCitasList().add(citas);
                codEmpleadosNew = em.merge(codEmpleadosNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = citas.getCodCitas();
                if (findCitas(id) == null) {
                    throw new NonexistentEntityException("The citas with id " + id + " no longer exists.");
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
            Citas citas;
            try {
                citas = em.getReference(Citas.class, id);
                citas.getCodCitas();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The citas with id " + id + " no longer exists.", enfe);
            }
            Clientes codClientes = citas.getCodClientes();
            if (codClientes != null) {
                codClientes.getCitasList().remove(citas);
                codClientes = em.merge(codClientes);
            }
            Empleados codEmpleados = citas.getCodEmpleados();
            if (codEmpleados != null) {
                codEmpleados.getCitasList().remove(citas);
                codEmpleados = em.merge(codEmpleados);
            }
            em.remove(citas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Citas> findCitasEntities() {
        return findCitasEntities(true, -1, -1);
    }

    public List<Citas> findCitasEntities(int maxResults, int firstResult) {
        return findCitasEntities(false, maxResults, firstResult);
    }

    private List<Citas> findCitasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Citas.class));
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

    public Citas findCitas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Citas.class, id);
        } finally {
            em.close();
        }
    }

    public int getCitasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Citas> rt = cq.from(Citas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
