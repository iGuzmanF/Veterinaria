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
    @NamedQuery(name = "Formapago.findAll", query = "SELECT f FROM Formapago f")
    , @NamedQuery(name = "Formapago.findByCodFormapago", query = "SELECT f FROM Formapago f WHERE f.codFormapago = :codFormapago")
    , @NamedQuery(name = "Formapago.findByTarjeta", query = "SELECT f FROM Formapago f WHERE f.tarjeta = :tarjeta")
    , @NamedQuery(name = "Formapago.findByEfectivo", query = "SELECT f FROM Formapago f WHERE f.efectivo = :efectivo")})
public class Formapago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_formapago", nullable = false)
    private Integer codFormapago;
    @Basic(optional = false)
    @Column(nullable = false, length = 16)
    private String tarjeta;
    @Basic(optional = false)
    @Column(nullable = false)
    private float efectivo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codFormapago")
    private List<Detallefactura> detallefacturaList;

    public Formapago() {
    }

    public Formapago(Integer codFormapago) {
        this.codFormapago = codFormapago;
    }

    public Formapago(Integer codFormapago, String tarjeta, float efectivo) {
        this.codFormapago = codFormapago;
        this.tarjeta = tarjeta;
        this.efectivo = efectivo;
    }

    public Integer getCodFormapago() {
        return codFormapago;
    }

    public void setCodFormapago(Integer codFormapago) {
        this.codFormapago = codFormapago;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public float getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(float efectivo) {
        this.efectivo = efectivo;
    }

    @XmlTransient
    public List<Detallefactura> getDetallefacturaList() {
        return detallefacturaList;
    }

    public void setDetallefacturaList(List<Detallefactura> detallefacturaList) {
        this.detallefacturaList = detallefacturaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codFormapago != null ? codFormapago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Formapago)) {
            return false;
        }
        Formapago other = (Formapago) object;
        if ((this.codFormapago == null && other.codFormapago != null) || (this.codFormapago != null && !this.codFormapago.equals(other.codFormapago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.ujmd.veterinaria.entidades.Formapago[ codFormapago=" + codFormapago + " ]";
    }
    
}
