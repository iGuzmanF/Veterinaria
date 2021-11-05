/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ujmd.clases;

/**
 *
 * @author meev9
 */
public class Usuarios {
    
    private int codigo;
    private String usuario;
    private String contraseña;

    public Usuarios() {
    }

    public int getCodigo() {
        return codigo;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    
    
}
