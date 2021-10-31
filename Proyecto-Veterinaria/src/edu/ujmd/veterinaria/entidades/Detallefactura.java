/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ujmd.veterinaria.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author meev9
 */
@Entity
@Table(catalog = "veterinaria", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Detallefactura.findAll", query = "SELECT d FROM Detallefactura d")
    , @NamedQuery(name = "Detallefactura.findByCodDetalle", query = "SELECT d FROM Detallefactura d WHERE d.codDetalle = :codDetalle")
    , @NamedQuery(name = "Detallefactura.findByPrecio", query = "SELECT d FROM Detallefactura d WHERE d.precio = :precio")
    , @NamedQuery(name = "Detallefactura.findByIva", query = "SELECT d FROM Detallefactura d WHERE d.iva = :iva")
    , @NamedQuery(name = "Detallefactura.findByFechapago", query = "SELECT d FROM Detallefactura d WHERE d.fechapago = :fechapago")
    , @NamedQuery(name = "Detallefactura.findByDetalle", query = "SELECT d FROM Detallefactura d WHERE d.detalle = :detalle")
    , @NamedQuery(name = "Detallefactura.findBySubtotal", query = "SELECT d FROM Detallefactura d WHERE d.subtotal = :subtotal")
    , @NamedQuery(name = "Detallefactura.findByTotal", query = "SELECT d FROM Detallefactura d WHERE d.total = :total")})
public class Detallefactura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_detalle", nullable = false)
    private Integer codDetalle;
    @Basic(optional = false)
    @Column(nullable = false)
    private float precio;
    @Basic(optional = false)
    @Column(nullable = false)
    private float iva;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechapago;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String detalle;
    @Basic(optional = false)
    @Column(nullable = false)
    private float subtotal;
    @Basic(optional = false)
    @Column(nullable = false)
    private float total;
    @JoinColumn(name = "cod_factura", referencedColumnName = "cod_factura", nullable = false)
    @ManyToOne(optional = false)
    private Factura codFactura;
    @JoinColumn(name = "cod_formapago", referencedColumnName = "cod_formapago", nullable = false)
    @ManyToOne(optional = false)
    private Formapago codFormapago;

    public Detallefactura() {
    }

    public Detallefactura(Integer codDetalle) {
        this.codDetalle = codDetalle;
    }

    public Detallefactura(Integer codDetalle, float precio, float iva, Date fechapago, String detalle, float subtotal, float total) {
        this.codDetalle = codDetalle;
        this.precio = precio;
        this.iva = iva;
        this.fechapago = fechapago;
        this.detalle = detalle;
        this.subtotal = subtotal;
        this.total = total;
    }

    public Integer getCodDetalle() {
        return codDetalle;
    }

    public void setCodDetalle(Integer codDetalle) {
        this.codDetalle = codDetalle;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getIva() {
        return iva;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }

    public Date getFechapago() {
        return fechapago;
    }

    public void setFechapago(Date fechapago) {
        this.fechapago = fechapago;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Factura getCodFactura() {
        return codFactura;
    }

    public void setCodFactura(Factura codFactura) {
        this.codFactura = codFactura;
    }

    public Formapago getCodFormapago() {
        return codFormapago;
    }

    public void setCodFormapago(Formapago codFormapago) {
        this.codFormapago = codFormapago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codDetalle != null ? codDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Detallefactura)) {
            return false;
        }
        Detallefactura other = (Detallefactura) object;
        if ((this.codDetalle == null && other.codDetalle != null) || (this.codDetalle != null && !this.codDetalle.equals(other.codDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.ujmd.veterinaria.entidades.Detallefactura[ codDetalle=" + codDetalle + " ]";
    }
    
}
