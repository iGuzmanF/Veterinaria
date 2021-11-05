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
import edu.ujmd.veterinaria.entidades.Citas;
import edu.ujmd.veterinaria.entidades.Empleados;
import java.util.ArrayList;
import java.util.List;
import edu.ujmd.veterinaria.entidades.Historialmedico;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author meev9
 */
public class EmpleadosJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    public EmpleadosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
      public EmpleadosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Proyecto-VeterinariaPU");
    }
    
    

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleados empleados) {
        if (empleados.getCitasList() == null) {
            empleados.setCitasList(new ArrayList<Citas>());
        }
        if (empleados.getHistorialmedicoList() == null) {
            empleados.setHistorialmedicoList(new ArrayList<Historialmedico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Citas> attachedCitasList = new ArrayList<Citas>();
            for (Citas citasListCitasToAttach : empleados.getCitasList()) {
                citasListCitasToAttach = em.getReference(citasListCitasToAttach.getClass(), citasListCitasToAttach.getCodCitas());
                attachedCitasList.add(citasListCitasToAttach);
            }
            empleados.setCitasList(attachedCitasList);
            List<Historialmedico> attachedHistorialmedicoList = new ArrayList<Historialmedico>();
            for (Historialmedico historialmedicoListHistorialmedicoToAttach : empleados.getHistorialmedicoList()) {
                historialmedicoListHistorialmedicoToAttach = em.getReference(historialmedicoListHistorialmedicoToAttach.getClass(), historialmedicoListHistorialmedicoToAttach.getCodHistorial());
                attachedHistorialmedicoList.add(historialmedicoListHistorialmedicoToAttach);
            }
            empleados.setHistorialmedicoList(attachedHistorialmedicoList);
            em.persist(empleados);
            for (Citas citasListCitas : empleados.getCitasList()) {
                Empleados oldCodEmpleadosOfCitasListCitas = citasListCitas.getCodEmpleados();
                citasListCitas.setCodEmpleados(empleados);
                citasListCitas = em.merge(citasListCitas);
                if (oldCodEmpleadosOfCitasListCitas != null) {
                    oldCodEmpleadosOfCitasListCitas.getCitasList().remove(citasListCitas);
                    oldCodEmpleadosOfCitasListCitas = em.merge(oldCodEmpleadosOfCitasListCitas);
                }
            }
            for (Historialmedico historialmedicoListHistorialmedico : empleados.getHistorialmedicoList()) {
                Empleados oldCodEmpleadosOfHistorialmedicoListHistorialmedico = historialmedicoListHistorialmedico.getCodEmpleados();
                historialmedicoListHistorialmedico.setCodEmpleados(empleados);
                historialmedicoListHistorialmedico = em.merge(historialmedicoListHistorialmedico);
                if (oldCodEmpleadosOfHistorialmedicoListHistorialmedico != null) {
                    oldCodEmpleadosOfHistorialmedicoListHistorialmedico.getHistorialmedicoList().remove(historialmedicoListHistorialmedico);
                    oldCodEmpleadosOfHistorialmedicoListHistorialmedico = em.merge(oldCodEmpleadosOfHistorialmedicoListHistorialmedico);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleados empleados) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleados persistentEmpleados = em.find(Empleados.class, empleados.getCodEmpleados());
            List<Citas> citasListOld = persistentEmpleados.getCitasList();
            List<Citas> citasListNew = empleados.getCitasList();
            List<Historialmedico> historialmedicoListOld = persistentEmpleados.getHistorialmedicoList();
            List<Historialmedico> historialmedicoListNew = empleados.getHistorialmedicoList();
            List<String> illegalOrphanMessages = null;
            for (Citas citasListOldCitas : citasListOld) {
                if (!citasListNew.contains(citasListOldCitas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Citas " + citasListOldCitas + " since its codEmpleados field is not nullable.");
                }
            }
            for (Historialmedico historialmedicoListOldHistorialmedico : historialmedicoListOld) {
                if (!historialmedicoListNew.contains(historialmedicoListOldHistorialmedico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Historialmedico " + historialmedicoListOldHistorialmedico + " since its codEmpleados field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Citas> attachedCitasListNew = new ArrayList<Citas>();
            for (Citas citasListNewCitasToAttach : citasListNew) {
                citasListNewCitasToAttach = em.getReference(citasListNewCitasToAttach.getClass(), citasListNewCitasToAttach.getCodCitas());
                attachedCitasListNew.add(citasListNewCitasToAttach);
            }
            citasListNew = attachedCitasListNew;
            empleados.setCitasList(citasListNew);
            List<Historialmedico> attachedHistorialmedicoListNew = new ArrayList<Historialmedico>();
            for (Historialmedico historialmedicoListNewHistorialmedicoToAttach : historialmedicoListNew) {
                historialmedicoListNewHistorialmedicoToAttach = em.getReference(historialmedicoListNewHistorialmedicoToAttach.getClass(), historialmedicoListNewHistorialmedicoToAttach.getCodHistorial());
                attachedHistorialmedicoListNew.add(historialmedicoListNewHistorialmedicoToAttach);
            }
            historialmedicoListNew = attachedHistorialmedicoListNew;
            empleados.setHistorialmedicoList(historialmedicoListNew);
            empleados = em.merge(empleados);
            for (Citas citasListNewCitas : citasListNew) {
                if (!citasListOld.contains(citasListNewCitas)) {
                    Empleados oldCodEmpleadosOfCitasListNewCitas = citasListNewCitas.getCodEmpleados();
                    citasListNewCitas.setCodEmpleados(empleados);
                    citasListNewCitas = em.merge(citasListNewCitas);
                    if (oldCodEmpleadosOfCitasListNewCitas != null && !oldCodEmpleadosOfCitasListNewCitas.equals(empleados)) {
                        oldCodEmpleadosOfCitasListNewCitas.getCitasList().remove(citasListNewCitas);
                        oldCodEmpleadosOfCitasListNewCitas = em.merge(oldCodEmpleadosOfCitasListNewCitas);
                    }
                }
            }
            for (Historialmedico historialmedicoListNewHistorialmedico : historialmedicoListNew) {
                if (!historialmedicoListOld.contains(historialmedicoListNewHistorialmedico)) {
                    Empleados oldCodEmpleadosOfHistorialmedicoListNewHistorialmedico = historialmedicoListNewHistorialmedico.getCodEmpleados();
                    historialmedicoListNewHistorialmedico.setCodEmpleados(empleados);
                    historialmedicoListNewHistorialmedico = em.merge(historialmedicoListNewHistorialmedico);
                    if (oldCodEmpleadosOfHistorialmedicoListNewHistorialmedico != null && !oldCodEmpleadosOfHistorialmedicoListNewHistorialmedico.equals(empleados)) {
                        oldCodEmpleadosOfHistorialmedicoListNewHistorialmedico.getHistorialmedicoList().remove(historialmedicoListNewHistorialmedico);
                        oldCodEmpleadosOfHistorialmedicoListNewHistorialmedico = em.merge(oldCodEmpleadosOfHistorialmedicoListNewHistorialmedico);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empleados.getCodEmpleados();
                if (findEmpleados(id) == null) {
                    throw new NonexistentEntityException("The empleados with id " + id + " no longer exists.");
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
            Empleados empleados;
            try {
                empleados = em.getReference(Empleados.class, id);
                empleados.getCodEmpleados();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleados with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Citas> citasListOrphanCheck = empleados.getCitasList();
            for (Citas citasListOrphanCheckCitas : citasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleados (" + empleados + ") cannot be destroyed since the Citas " + citasListOrphanCheckCitas + " in its citasList field has a non-nullable codEmpleados field.");
            }
            List<Historialmedico> historialmedicoListOrphanCheck = empleados.getHistorialmedicoList();
            for (Historialmedico historialmedicoListOrphanCheckHistorialmedico : historialmedicoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleados (" + empleados + ") cannot be destroyed since the Historialmedico " + historialmedicoListOrphanCheckHistorialmedico + " in its historialmedicoList field has a non-nullable codEmpleados field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(empleados);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleados> findEmpleadosEntities() {
        return findEmpleadosEntities(true, -1, -1);
    }

    public List<Empleados> findEmpleadosEntities(int maxResults, int firstResult) {
        return findEmpleadosEntities(false, maxResults, firstResult);
    }

    private List<Empleados> findEmpleadosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleados.class));
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

    public Empleados findEmpleados(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleados.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleados> rt = cq.from(Empleados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
