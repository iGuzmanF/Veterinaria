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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @NamedQuery(name = "Mascotas.findAll", query = "SELECT m FROM Mascotas m")
    , @NamedQuery(name = "Mascotas.findByCodMascotas", query = "SELECT m FROM Mascotas m WHERE m.codMascotas = :codMascotas")
    , @NamedQuery(name = "Mascotas.findByNombre", query = "SELECT m FROM Mascotas m WHERE m.nombre = :nombre")
    , @NamedQuery(name = "Mascotas.findByEdad", query = "SELECT m FROM Mascotas m WHERE m.edad = :edad")
    , @NamedQuery(name = "Mascotas.findByRaza", query = "SELECT m FROM Mascotas m WHERE m.raza = :raza")
    , @NamedQuery(name = "Mascotas.findByGenero", query = "SELECT m FROM Mascotas m WHERE m.genero = :genero")
    , @NamedQuery(name = "Mascotas.findByPeso", query = "SELECT m FROM Mascotas m WHERE m.peso = :peso")
    , @NamedQuery(name = "Mascotas.findByEstadoVacuna", query = "SELECT m FROM Mascotas m WHERE m.estadoVacuna = :estadoVacuna")})
public class Mascotas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_mascotas", nullable = false)
    private Integer codMascotas;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String nombre;
    @Basic(optional = false)
    @Column(nullable = false, length = 3)
    private String edad;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String raza;
    @Basic(optional = false)
    @Column(nullable = false, length = 20)
    private String genero;
    @Basic(optional = false)
    @Column(nullable = false, length = 4)
    private String peso;
    @Basic(optional = false)
    @Column(name = "Estado_Vacuna", nullable = false, length = 2)
    private String estadoVacuna;
    @JoinColumn(name = "cod_clientes", referencedColumnName = "cod_clientes", nullable = false)
    @ManyToOne(optional = false)
    private Clientes codClientes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codMascotas")
    private List<Historialmedico> historialmedicoList;

    public Mascotas() {
    }

    public Mascotas(Integer codMascotas) {
        this.codMascotas = codMascotas;
    }

    public Mascotas(Integer codMascotas, String nombre, String edad, String raza, String genero, String peso, String estadoVacuna) {
        this.codMascotas = codMascotas;
        this.nombre = nombre;
        this.edad = edad;
        this.raza = raza;
        this.genero = genero;
        this.peso = peso;
        this.estadoVacuna = estadoVacuna;
    }

    public Integer getCodMascotas() {
        return codMascotas;
    }

    public void setCodMascotas(Integer codMascotas) {
        this.codMascotas = codMascotas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getEstadoVacuna() {
        return estadoVacuna;
    }

    public void setEstadoVacuna(String estadoVacuna) {
        this.estadoVacuna = estadoVacuna;
    }

    public Clientes getCodClientes() {
        return codClientes;
    }

    public void setCodClientes(Clientes codClientes) {
        this.codClientes = codClientes;
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
        hash += (codMascotas != null ? codMascotas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mascotas)) {
            return false;
        }
        Mascotas other = (Mascotas) object;
        if ((this.codMascotas == null && other.codMascotas != null) || (this.codMascotas != null && !this.codMascotas.equals(other.codMascotas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.ujmd.veterinaria.entidades.Mascotas[ codMascotas=" + codMascotas + " ]";
    }
    
}
