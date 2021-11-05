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
import edu.ujmd.veterinaria.entidades.Factura;
import java.util.ArrayList;
import java.util.List;
import edu.ujmd.veterinaria.entidades.Citas;
import edu.ujmd.veterinaria.entidades.Clientes;
import edu.ujmd.veterinaria.entidades.Mascotas;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author meev9
 */
public class ClientesJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    
    public ClientesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
     public ClientesJpaController() {//Conexion a la base de datos
        this.emf = Persistence.createEntityManagerFactory("Proyecto-VeterinariaPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clientes clientes) {
        if (clientes.getFacturaList() == null) {
            clientes.setFacturaList(new ArrayList<Factura>());
        }
        if (clientes.getCitasList() == null) {
            clientes.setCitasList(new ArrayList<Citas>());
        }
        if (clientes.getMascotasList() == null) {
            clientes.setMascotasList(new ArrayList<Mascotas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Factura> attachedFacturaList = new ArrayList<Factura>();
            for (Factura facturaListFacturaToAttach : clientes.getFacturaList()) {
                facturaListFacturaToAttach = em.getReference(facturaListFacturaToAttach.getClass(), facturaListFacturaToAttach.getCodFactura());
                attachedFacturaList.add(facturaListFacturaToAttach);
            }
            clientes.setFacturaList(attachedFacturaList);
            List<Citas> attachedCitasList = new ArrayList<Citas>();
            for (Citas citasListCitasToAttach : clientes.getCitasList()) {
                citasListCitasToAttach = em.getReference(citasListCitasToAttach.getClass(), citasListCitasToAttach.getCodCitas());
                attachedCitasList.add(citasListCitasToAttach);
            }
            clientes.setCitasList(attachedCitasList);
            List<Mascotas> attachedMascotasList = new ArrayList<Mascotas>();
            for (Mascotas mascotasListMascotasToAttach : clientes.getMascotasList()) {
                mascotasListMascotasToAttach = em.getReference(mascotasListMascotasToAttach.getClass(), mascotasListMascotasToAttach.getCodMascotas());
                attachedMascotasList.add(mascotasListMascotasToAttach);
            }
            clientes.setMascotasList(attachedMascotasList);
            em.persist(clientes);
            for (Factura facturaListFactura : clientes.getFacturaList()) {
                Clientes oldCodClientesOfFacturaListFactura = facturaListFactura.getCodClientes();
                facturaListFactura.setCodClientes(clientes);
                facturaListFactura = em.merge(facturaListFactura);
                if (oldCodClientesOfFacturaListFactura != null) {
                    oldCodClientesOfFacturaListFactura.getFacturaList().remove(facturaListFactura);
                    oldCodClientesOfFacturaListFactura = em.merge(oldCodClientesOfFacturaListFactura);
                }
            }
            for (Citas citasListCitas : clientes.getCitasList()) {
                Clientes oldCodClientesOfCitasListCitas = citasListCitas.getCodClientes();
                citasListCitas.setCodClientes(clientes);
                citasListCitas = em.merge(citasListCitas);
                if (oldCodClientesOfCitasListCitas != null) {
                    oldCodClientesOfCitasListCitas.getCitasList().remove(citasListCitas);
                    oldCodClientesOfCitasListCitas = em.merge(oldCodClientesOfCitasListCitas);
                }
            }
            for (Mascotas mascotasListMascotas : clientes.getMascotasList()) {
                Clientes oldCodClientesOfMascotasListMascotas = mascotasListMascotas.getCodClientes();
                mascotasListMascotas.setCodClientes(clientes);
                mascotasListMascotas = em.merge(mascotasListMascotas);
                if (oldCodClientesOfMascotasListMascotas != null) {
                    oldCodClientesOfMascotasListMascotas.getMascotasList().remove(mascotasListMascotas);
                    oldCodClientesOfMascotasListMascotas = em.merge(oldCodClientesOfMascotasListMascotas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clientes clientes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes persistentClientes = em.find(Clientes.class, clientes.getCodClientes());
            List<Factura> facturaListOld = persistentClientes.getFacturaList();
            List<Factura> facturaListNew = clientes.getFacturaList();
            List<Citas> citasListOld = persistentClientes.getCitasList();
            List<Citas> citasListNew = clientes.getCitasList();
            List<Mascotas> mascotasListOld = persistentClientes.getMascotasList();
            List<Mascotas> mascotasListNew = clientes.getMascotasList();
            List<String> illegalOrphanMessages = null;
            for (Factura facturaListOldFactura : facturaListOld) {
                if (!facturaListNew.contains(facturaListOldFactura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Factura " + facturaListOldFactura + " since its codClientes field is not nullable.");
                }
            }
            for (Citas citasListOldCitas : citasListOld) {
                if (!citasListNew.contains(citasListOldCitas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Citas " + citasListOldCitas + " since its codClientes field is not nullable.");
                }
            }
            for (Mascotas mascotasListOldMascotas : mascotasListOld) {
                if (!mascotasListNew.contains(mascotasListOldMascotas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mascotas " + mascotasListOldMascotas + " since its codClientes field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Factura> attachedFacturaListNew = new ArrayList<Factura>();
            for (Factura facturaListNewFacturaToAttach : facturaListNew) {
                facturaListNewFacturaToAttach = em.getReference(facturaListNewFacturaToAttach.getClass(), facturaListNewFacturaToAttach.getCodFactura());
                attachedFacturaListNew.add(facturaListNewFacturaToAttach);
            }
            facturaListNew = attachedFacturaListNew;
            clientes.setFacturaList(facturaListNew);
            List<Citas> attachedCitasListNew = new ArrayList<Citas>();
            for (Citas citasListNewCitasToAttach : citasListNew) {
                citasListNewCitasToAttach = em.getReference(citasListNewCitasToAttach.getClass(), citasListNewCitasToAttach.getCodCitas());
                attachedCitasListNew.add(citasListNewCitasToAttach);
            }
            citasListNew = attachedCitasListNew;
            clientes.setCitasList(citasListNew);
            List<Mascotas> attachedMascotasListNew = new ArrayList<Mascotas>();
            for (Mascotas mascotasListNewMascotasToAttach : mascotasListNew) {
                mascotasListNewMascotasToAttach = em.getReference(mascotasListNewMascotasToAttach.getClass(), mascotasListNewMascotasToAttach.getCodMascotas());
                attachedMascotasListNew.add(mascotasListNewMascotasToAttach);
            }
            mascotasListNew = attachedMascotasListNew;
            clientes.setMascotasList(mascotasListNew);
            clientes = em.merge(clientes);
            for (Factura facturaListNewFactura : facturaListNew) {
                if (!facturaListOld.contains(facturaListNewFactura)) {
                    Clientes oldCodClientesOfFacturaListNewFactura = facturaListNewFactura.getCodClientes();
                    facturaListNewFactura.setCodClientes(clientes);
                    facturaListNewFactura = em.merge(facturaListNewFactura);
                    if (oldCodClientesOfFacturaListNewFactura != null && !oldCodClientesOfFacturaListNewFactura.equals(clientes)) {
                        oldCodClientesOfFacturaListNewFactura.getFacturaList().remove(facturaListNewFactura);
                        oldCodClientesOfFacturaListNewFactura = em.merge(oldCodClientesOfFacturaListNewFactura);
                    }
                }
            }
            for (Citas citasListNewCitas : citasListNew) {
                if (!citasListOld.contains(citasListNewCitas)) {
                    Clientes oldCodClientesOfCitasListNewCitas = citasListNewCitas.getCodClientes();
                    citasListNewCitas.setCodClientes(clientes);
                    citasListNewCitas = em.merge(citasListNewCitas);
                    if (oldCodClientesOfCitasListNewCitas != null && !oldCodClientesOfCitasListNewCitas.equals(clientes)) {
                        oldCodClientesOfCitasListNewCitas.getCitasList().remove(citasListNewCitas);
                        oldCodClientesOfCitasListNewCitas = em.merge(oldCodClientesOfCitasListNewCitas);
                    }
                }
            }
            for (Mascotas mascotasListNewMascotas : mascotasListNew) {
                if (!mascotasListOld.contains(mascotasListNewMascotas)) {
                    Clientes oldCodClientesOfMascotasListNewMascotas = mascotasListNewMascotas.getCodClientes();
                    mascotasListNewMascotas.setCodClientes(clientes);
                    mascotasListNewMascotas = em.merge(mascotasListNewMascotas);
                    if (oldCodClientesOfMascotasListNewMascotas != null && !oldCodClientesOfMascotasListNewMascotas.equals(clientes)) {
                        oldCodClientesOfMascotasListNewMascotas.getMascotasList().remove(mascotasListNewMascotas);
                        oldCodClientesOfMascotasListNewMascotas = em.merge(oldCodClientesOfMascotasListNewMascotas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clientes.getCodClientes();
                if (findClientes(id) == null) {
                    throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.");
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
            Clientes clientes;
            try {
                clientes = em.getReference(Clientes.class, id);
                clientes.getCodClientes();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Factura> facturaListOrphanCheck = clientes.getFacturaList();
            for (Factura facturaListOrphanCheckFactura : facturaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the Factura " + facturaListOrphanCheckFactura + " in its facturaList field has a non-nullable codClientes field.");
            }
            List<Citas> citasListOrphanCheck = clientes.getCitasList();
            for (Citas citasListOrphanCheckCitas : citasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the Citas " + citasListOrphanCheckCitas + " in its citasList field has a non-nullable codClientes field.");
            }
            List<Mascotas> mascotasListOrphanCheck = clientes.getMascotasList();
            for (Mascotas mascotasListOrphanCheckMascotas : mascotasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the Mascotas " + mascotasListOrphanCheckMascotas + " in its mascotasList field has a non-nullable codClientes field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(clientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clientes> findClientesEntities() {
        return findClientesEntities(true, -1, -1);
    }

    public List<Clientes> findClientesEntities(int maxResults, int firstResult) {
        return findClientesEntities(false, maxResults, firstResult);
    }

    private List<Clientes> findClientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clientes.class));
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

    public Clientes findClientes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clientes> rt = cq.from(Clientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
