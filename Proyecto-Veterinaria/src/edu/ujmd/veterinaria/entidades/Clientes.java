/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ujmd.veterinaria.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author meev9
 */
@Entity
@Table(catalog = "veterinaria", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Clientes c")
    , @NamedQuery(name = "Clientes.findByCodClientes", query = "SELECT c FROM Clientes c WHERE c.codClientes = :codClientes")
    , @NamedQuery(name = "Clientes.findByNombre", query = "SELECT c FROM Clientes c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Clientes.findByApellido", query = "SELECT c FROM Clientes c WHERE c.apellido = :apellido")
    , @NamedQuery(name = "Clientes.findByTelefono", query = "SELECT c FROM Clientes c WHERE c.telefono = :telefono")
    , @NamedQuery(name = "Clientes.findByDireccion", query = "SELECT c FROM Clientes c WHERE c.direccion = :direccion")})
public class Clientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_clientes", nullable = false)
    private Integer codClientes;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String nombre;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String apellido;
    @Basic(optional = false)
    @Column(nullable = false, length = 8)
    private String telefono;
    @Basic(optional = false)
    @Column(nullable = false, length = 200)
    private String direccion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codClientes")
    private List<Factura> facturaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codClientes")
    private List<Citas> citasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codClientes")
    private List<Mascotas> mascotasList;

    public Clientes() {
    }

    public Clientes(Integer codClientes) {
        this.codClientes = codClientes;
    }

    public Clientes(Integer codClientes, String nombre, String apellido, String telefono, String direccion) {
        this.codClientes = codClientes;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public Integer getCodClientes() {
        return codClientes;
    }

    public void setCodClientes(Integer codClientes) {
        this.codClientes = codClientes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @XmlTransient
    public List<Factura> getFacturaList() {
        return facturaList;
    }

    public void setFacturaList(List<Factura> facturaList) {
        this.facturaList = facturaList;
    }

    @XmlTransient
    public List<Citas> getCitasList() {
        return citasList;
    }

    public void setCitasList(List<Citas> citasList) {
        this.citasList = citasList;
    }

    @XmlTransient
    public List<Mascotas> getMascotasList() {
        return mascotasList;
    }

    public void setMascotasList(List<Mascotas> mascotasList) {
        this.mascotasList = mascotasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codClientes != null ? codClientes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clientes)) {
            return false;
        }
        Clientes other = (Clientes) object;
        if ((this.codClientes == null && other.codClientes != null) || (this.codClientes != null && !this.codClientes.equals(other.codClientes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.ujmd.veterinaria.entidades.Clientes[ codClientes=" + codClientes + " ]";
    }
    
}
