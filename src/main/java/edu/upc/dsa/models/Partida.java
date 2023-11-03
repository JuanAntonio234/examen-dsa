package edu.upc.dsa.models;
import edu.upc.dsa.util.RandomUtils;

import  java.util.Date;
public class Partida {

    String id;
    Usuario usuario;
    Juego juego;
    int nivelActual;
    int puntuacion;
    Date fechaInicio;

    public Partida() {
        this.id = RandomUtils.getId();
    }

    public Partida(Usuario usuario,Juego juego ){
        this();
        this.usuario=usuario;
        this.juego=juego;
        this.nivelActual=1;
        this.puntuacion=50;
        this.fechaInicio=new Date();
    }

    public String getId(){
        return id;
    }

    public void setId(String id) {
        this.id=id;
    }
    public  Usuario getUsuario(){
        return  usuario;
    }
    public Juego getJuego(){
        return juego;
    }
    public int getNivelActual(){
        return nivelActual;
    }
    public void setNivelActual(int nivelActual){
        this.nivelActual=nivelActual;
    }
    public int getPuntuacion(){
        return puntuacion;
    }
    public void setPuntuacion(int puntuacion){
        this.puntuacion=puntuacion;
    }
    public Date getFechaInicio(){
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio){
        this.fechaInicio=fechaInicio;
    }
}
