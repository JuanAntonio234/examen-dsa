package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

import java.util.List;

public class Usuario {

    List<String> juegosParticipados;
    String idUsuario;
    String nombre;

    public Usuario() {
        this.idUsuario = RandomUtils.getId();
    }

    public Usuario(String nombre) {
        this();
        this.nombre=nombre;
    }

    public String getIdUsuario() {
        return this.idUsuario;
    }

    public void setId(String idUsuario) {
        this.idUsuario=idUsuario;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean getUserParticipadoEnJuego(String gameId){
        return juegosParticipados.contains(gameId);
    }
    @Override
    public String toString() {
        return "Usuario [id="+idUsuario+", nombre=]";
    }

}