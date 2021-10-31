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
    @NamedQuery(name = "Citas.findAll", query = "SELECT c FROM Citas c")
    , @NamedQuery(name = "Citas.findByCodCitas", query = "SELECT c FROM Citas c WHERE c.codCitas = :codCitas")
    , @NamedQuery(name = "Citas.findByFechaHora", query = "SELECT c FROM Citas c WHERE c.fechaHora = :fechaHora")})
public class Citas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_citas", nullable = false)
    private Integer codCitas;
    @Basic(optional = false)
    @Column(name = "Fecha_Hora", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @JoinColumn(name = "cod_clientes", referencedColumnName = "cod_clientes", nullable = false)
    @ManyToOne(optional = false)
    private Clientes codClientes;
    @JoinColumn(name = "cod_empleados", referencedColumnName = "cod_empleados", nullable = false)
    @ManyToOne(optional = false)
    private Empleados codEmpleados;

    public Citas() {
    }

    public Citas(Integer codCitas) {
        this.codCitas = codCitas;
    }

    public Citas(Integer codCitas, Date fechaHora) {
        this.codCitas = codCitas;
        this.fechaHora = fechaHora;
    }

    public Integer getCodCitas() {
        return codCitas;
    }

    public void setCodCitas(Integer codCitas) {
        this.codCitas = codCitas;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Clientes getCodClientes() {
        return codClientes;
    }

    public void setCodClientes(Clientes codClientes) {
        this.codClientes = codClientes;
    }

    public Empleados getCodEmpleados() {
        return codEmpleados;
    }

    public void setCodEmpleados(Empleados codEmpleados) {
        this.codEmpleados = codEmpleados;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCitas != null ? codCitas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Citas)) {
            return false;
        }
        Citas other = (Citas) object;
        if ((this.codCitas == null && other.codCitas != null) || (this.codCitas != null && !this.codCitas.equals(other.codCitas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.ujmd.veterinaria.entidades.Citas[ codCitas=" + codCitas + " ]";
    }
    
}
