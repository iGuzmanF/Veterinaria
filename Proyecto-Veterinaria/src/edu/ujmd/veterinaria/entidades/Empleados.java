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
    @NamedQuery(name = "Empleados.findAll", query = "SELECT e FROM Empleados e")
    , @NamedQuery(name = "Empleados.findByCodEmpleados", query = "SELECT e FROM Empleados e WHERE e.codEmpleados = :codEmpleados")
    , @NamedQuery(name = "Empleados.findByNombre", query = "SELECT e FROM Empleados e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "Empleados.findByApellido", query = "SELECT e FROM Empleados e WHERE e.apellido = :apellido")
    , @NamedQuery(name = "Empleados.findByTelefono", query = "SELECT e FROM Empleados e WHERE e.telefono = :telefono")
    , @NamedQuery(name = "Empleados.findByDui", query = "SELECT e FROM Empleados e WHERE e.dui = :dui")
    , @NamedQuery(name = "Empleados.findByCargo", query = "SELECT e FROM Empleados e WHERE e.cargo = :cargo")})
public class Empleados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_empleados", nullable = false)
    private Integer codEmpleados;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String nombre;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String apellido;
    @Basic(optional = false)
    @Column(nullable = false, length = 8)
    private String telefono;
    @Basic(optional = false)
    @Column(nullable = false, length = 15)
    private String dui;
    @Basic(optional = false)
    @Column(nullable = false, length = 15)
    private String cargo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEmpleados")
    private List<Citas> citasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEmpleados")
    private List<Historialmedico> historialmedicoList;

    public Empleados() {
    }

    public Empleados(Integer codEmpleados) {
        this.codEmpleados = codEmpleados;
    }

    public Empleados(Integer codEmpleados, String nombre, String apellido, String telefono, String dui, String cargo) {
        this.codEmpleados = codEmpleados;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.dui = dui;
        this.cargo = cargo;
    }

    public Integer getCodEmpleados() {
        return codEmpleados;
    }

    public void setCodEmpleados(Integer codEmpleados) {
        this.codEmpleados = codEmpleados;
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

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @XmlTransient
    public List<Citas> getCitasList() {
        return citasList;
    }

    public void setCitasList(List<Citas> citasList) {
        this.citasList = citasList;
    }

    @XmlTransient
    public List<Historialmedico> getHistorialmedicoList() {
        return historialmedicoList;
    }

    public void setHistorialmedicoList(List<Historialmedico> historialmedicoList) {
        this.historialmedicoList = historialmedicoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpleados != null ? codEmpleados.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleados)) {
            return false;
        }
        Empleados other = (Empleados) object;
        if ((this.codEmpleados == null && other.codEmpleados != null) || (this.codEmpleados != null && !this.codEmpleados.equals(other.codEmpleados))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.ujmd.veterinaria.entidades.Empleados[ codEmpleados=" + codEmpleados + " ]";
    }
    
}
