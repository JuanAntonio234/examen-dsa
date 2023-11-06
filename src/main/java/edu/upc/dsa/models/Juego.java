package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

import java.util.List;

public class Juego {

    String idJuego;
    String descripcion;
    int numNiveles;

    public Juego(String gameId,String descripcion,int numNiveles){
        this.idJuego=gameId;
        this.descripcion=descripcion;
        this.numNiveles=numNiveles;
    }
    public String getIdJuego() {
        return idJuego;
    }

    public void setId(String idJuego) {
        this.idJuego=idJuego;
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

    public static Juego buscarJuegoPorId(List<Juego> juegos, String  gameId){
        for(Juego juego : juegos){
            if(juego.getIdJuego().equals(gameId)) {
                return juego;
            }
        }
        return null;
    }
}
