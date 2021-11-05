/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ujmd.veterinaria.controladores;

import edu.ujmd.veterinaria.controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.ujmd.veterinaria.entidades.Empleados;
import edu.ujmd.veterinaria.entidades.Historialmedico;
import edu.ujmd.veterinaria.entidades.Mascotas;
import edu.ujmd.veterinaria.entidades.Vacunas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author meev9
 */
public class HistorialmedicoJpaController implements Serializable {
    private EntityManagerFactory emf = null;

    public HistorialmedicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
     public HistorialmedicoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Proyecto-VeterinariaPU");
    }
    

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Historialmedico historialmedico) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleados codEmpleados = historialmedico.getCodEmpleados();
            if (codEmpleados != null) {
                codEmpleados = em.getReference(codEmpleados.getClass(), codEmpleados.getCodEmpleados());
                historialmedico.setCodEmpleados(codEmpleados);
            }
            Mascotas codMascotas = historialmedico.getCodMascotas();
            if (codMascotas != null) {
                codMascotas = em.getReference(codMascotas.getClass(), codMascotas.getCodMascotas());
                historialmedico.setCodMascotas(codMascotas);
            }
            Vacunas codVacunas = historialmedico.getCodVacunas();
            if (codVacunas != null) {
                codVacunas = em.getReference(codVacunas.getClass(), codVacunas.getCodVacunas());
                historialmedico.setCodVacunas(codVacunas);
            }
            em.persist(historialmedico);
            if (codEmpleados != null) {
                codEmpleados.getHistorialmedicoList().add(historialmedico);
                codEmpleados = em.merge(codEmpleados);
            }
            if (codMascotas != null) {
                codMascotas.getHistorialmedicoList().add(historialmedico);
                codMascotas = em.merge(codMascotas);
            }
            if (codVacunas != null) {
                codVacunas.getHistorialmedicoList().add(historialmedico);
                codVacunas = em.merge(codVacunas);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Historialmedico historialmedico) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Historialmedico persistentHistorialmedico = em.find(Historialmedico.class, historialmedico.getCodHistorial());
            Empleados codEmpleadosOld = persistentHistorialmedico.getCodEmpleados();
            Empleados codEmpleadosNew = historialmedico.getCodEmpleados();
            Mascotas codMascotasOld = persistentHistorialmedico.getCodMascotas();
            Mascotas codMascotasNew = historialmedico.getCodMascotas();
            Vacunas codVacunasOld = persistentHistorialmedico.getCodVacunas();
            Vacunas codVacunasNew = historialmedico.getCodVacunas();
            if (codEmpleadosNew != null) {
                codEmpleadosNew = em.getReference(codEmpleadosNew.getClass(), codEmpleadosNew.getCodEmpleados());
                historialmedico.setCodEmpleados(codEmpleadosNew);
            }
            if (codMascotasNew != null) {
                codMascotasNew = em.getReference(codMascotasNew.getClass(), codMascotasNew.getCodMascotas());
                historialmedico.setCodMascotas(codMascotasNew);
            }
            if (codVacunasNew != null) {
                codVacunasNew = em.getReference(codVacunasNew.getClass(), codVacunasNew.getCodVacunas());
                historialmedico.setCodVacunas(codVacunasNew);
            }
            historialmedico = em.merge(historialmedico);
            if (codEmpleadosOld != null && !codEmpleadosOld.equals(codEmpleadosNew)) {
                codEmpleadosOld.getHistorialmedicoList().remove(historialmedico);
                codEmpleadosOld = em.merge(codEmpleadosOld);
            }
            if (codEmpleadosNew != null && !codEmpleadosNew.equals(codEmpleadosOld)) {
                codEmpleadosNew.getHistorialmedicoList().add(historialmedico);
                codEmpleadosNew = em.merge(codEmpleadosNew);
            }
            if (codMascotasOld != null && !codMascotasOld.equals(codMascotasNew)) {
                codMascotasOld.getHistorialmedicoList().remove(historialmedico);
                codMascotasOld = em.merge(codMascotasOld);
            }
            if (codMascotasNew != null && !codMascotasNew.equals(codMascotasOld)) {
                codMascotasNew.getHistorialmedicoList().add(historialmedico);
                codMascotasNew = em.merge(codMascotasNew);
            }
            if (codVacunasOld != null && !codVacunasOld.equals(codVacunasNew)) {
                codVacunasOld.getHistorialmedicoList().remove(historialmedico);
                codVacunasOld = em.merge(codVacunasOld);
            }
            if (codVacunasNew != null && !codVacunasNew.equals(codVacunasOld)) {
                codVacunasNew.getHistorialmedicoList().add(historialmedico);
                codVacunasNew = em.merge(codVacunasNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = historialmedico.getCodHistorial();
                if (findHistorialmedico(id) == null) {
                    throw new NonexistentEntityException("The historialmedico with id " + id + " no longer exists.");
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
            Historialmedico historialmedico;
            try {
                historialmedico = em.getReference(Historialmedico.class, id);
                historialmedico.getCodHistorial();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historialmedico with id " + id + " no longer exists.", enfe);
            }
            Empleados codEmpleados = historialmedico.getCodEmpleados();
            if (codEmpleados != null) {
                codEmpleados.getHistorialmedicoList().remove(historialmedico);
                codEmpleados = em.merge(codEmpleados);
            }
            Mascotas codMascotas = historialmedico.getCodMascotas();
            if (codMascotas != null) {
                codMascotas.getHistorialmedicoList().remove(historialmedico);
                codMascotas = em.merge(codMascotas);
            }
            Vacunas codVacunas = historialmedico.getCodVacunas();
            if (codVacunas != null) {
                codVacunas.getHistorialmedicoList().remove(historialmedico);
                codVacunas = em.merge(codVacunas);
            }
            em.remove(historialmedico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Historialmedico> findHistorialmedicoEntities() {
        return findHistorialmedicoEntities(true, -1, -1);
    }

    public List<Historialmedico> findHistorialmedicoEntities(int maxResults, int firstResult) {
        return findHistorialmedicoEntities(false, maxResults, firstResult);
    }

    private List<Historialmedico> findHistorialmedicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Historialmedico.class));
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

    public Historialmedico findHistorialmedico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Historialmedico.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistorialmedicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Historialmedico> rt = cq.from(Historialmedico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
