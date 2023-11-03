package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Usuario {

    String id;
    String nombre;

    public Usuario() {
        this.id = RandomUtils.getId();
    }

    public Usuario(String nombre) {
        this();
        this.nombre=nombre;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id=id;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Usuario [id="+id+", nombre=]";
    }

}