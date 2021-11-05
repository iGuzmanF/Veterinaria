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
    @NamedQuery(name = "Vacunas.findAll", query = "SELECT v FROM Vacunas v")
    , @NamedQuery(name = "Vacunas.findByCodVacunas", query = "SELECT v FROM Vacunas v WHERE v.codVacunas = :codVacunas")
    , @NamedQuery(name = "Vacunas.findByNomVacuna", query = "SELECT v FROM Vacunas v WHERE v.nomVacuna = :nomVacuna")
    , @NamedQuery(name = "Vacunas.findByDetalle", query = "SELECT v FROM Vacunas v WHERE v.detalle = :detalle")})
public class Vacunas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_vacunas", nullable = false)
    private Integer codVacunas;
    @Basic(optional = false)
    @Column(name = "Nom_Vacuna", nullable = false, length = 50)
    private String nomVacuna;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String detalle;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codVacunas")
    private List<Historialmedico> historialmedicoList;

    public Vacunas() {
    }

    public Vacunas(Integer codVacunas) {
        this.codVacunas = codVacunas;
    }

    public Vacunas(Integer codVacunas, String nomVacuna, String detalle) {
        this.codVacunas = codVacunas;
        this.nomVacuna = nomVacuna;
        this.detalle = detalle;
    }

    public Integer getCodVacunas() {
        return codVacunas;
    }

    public void setCodVacunas(Integer codVacunas) {
        this.codVacunas = codVacunas;
    }

    public String getNomVacuna() {
        return nomVacuna;
    }

    public void setNomVacuna(String nomVacuna) {
        this.nomVacuna = nomVacuna;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
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
        hash += (codVacunas != null ? codVacunas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vacunas)) {
            return false;
        }
        Vacunas other = (Vacunas) object;
        if ((this.codVacunas == null && other.codVacunas != null) || (this.codVacunas != null && !this.codVacunas.equals(other.codVacunas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.ujmd.veterinaria.entidades.Vacunas[ codVacunas=" + codVacunas + " ]";
    }
    
}
