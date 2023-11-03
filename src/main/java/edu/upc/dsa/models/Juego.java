package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Juego {

    String id;
    String descripcion;
    int numNiveles;

    public Juego() {
        this.id = RandomUtils.getId();
    }

    public Juego(String descripcion,int numNiveles){
        this();
        this.descripcion=descripcion;
        this.numNiveles=numNiveles;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id=id;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public int getNumNiveles() {
        return numNiveles;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void setTNumniveles(int numNiveles) {
        this.numNiveles = numNiveles;
    }
}
