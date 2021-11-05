/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ujmd.veterinaria.entidades;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author meev9
 */
@Entity
@Table(catalog = "veterinaria", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Historialmedico.findAll", query = "SELECT h FROM Historialmedico h")
    , @NamedQuery(name = "Historialmedico.findByCodHistorial", query = "SELECT h FROM Historialmedico h WHERE h.codHistorial = :codHistorial")
    , @NamedQuery(name = "Historialmedico.findByFecha", query = "SELECT h FROM Historialmedico h WHERE h.fecha = :fecha")
    , @NamedQuery(name = "Historialmedico.findByMotivo", query = "SELECT h FROM Historialmedico h WHERE h.motivo = :motivo")})
public class Historialmedico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_historial", nullable = false)
    private Integer codHistorial;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String fecha;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String motivo;
    @JoinColumn(name = "cod_empleados", referencedColumnName = "cod_empleados", nullable = false)
    @ManyToOne(optional = false)
    private Empleados codEmpleados;
    @JoinColumn(name = "cod_mascotas", referencedColumnName = "cod_mascotas", nullable = false)
    @ManyToOne(optional = false)
    private Mascotas codMascotas;
    @JoinColumn(name = "cod_vacunas", referencedColumnName = "cod_vacunas", nullable = false)
    @ManyToOne(optional = false)
    private Vacunas codVacunas;

    public Historialmedico() {
    }

    public Historialmedico(Integer codHistorial) {
        this.codHistorial = codHistorial;
    }

    public Historialmedico(Integer codHistorial, String fecha, String motivo) {
        this.codHistorial = codHistorial;
        this.fecha = fecha;
        this.motivo = motivo;
    }

    public Integer getCodHistorial() {
        return codHistorial;
    }

    public void setCodHistorial(Integer codHistorial) {
        this.codHistorial = codHistorial;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Empleados getCodEmpleados() {
        return codEmpleados;
    }

    public void setCodEmpleados(Empleados codEmpleados) {
        this.codEmpleados = codEmpleados;
    }

    public Mascotas getCodMascotas() {
        return codMascotas;
    }

    public void setCodMascotas(Mascotas codMascotas) {
        this.codMascotas = codMascotas;
    }

    public Vacunas getCodVacunas() {
        return codVacunas;
    }

    public void setCodVacunas(Vacunas codVacunas) {
        this.codVacunas = codVacunas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codHistorial != null ? codHistorial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Historialmedico)) {
            return false;
        }
        Historialmedico other = (Historialmedico) object;
        if ((this.codHistorial == null && other.codHistorial != null) || (this.codHistorial != null && !this.codHistorial.equals(other.codHistorial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.ujmd.veterinaria.entidades.Historialmedico[ codHistorial=" + codHistorial + " ]";
    }
    
}
