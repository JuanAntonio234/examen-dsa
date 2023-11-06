package edu.upc.dsa;

import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.Usuario;

import java.util.Date;
import java.util.List;

public interface GameEngine {

    public void crearUsuario(String nombre);
    public void eliminarUsuario(String userId);
    public void actualizarUsuario(String userId, String nuevoNombre);
    public boolean createGame(String gameID, String descripcion,int numNiveles);
    public boolean startGame(String gameId,String userId);
    public int getNivelActual(String userId);
    public int getPuntuacionActual(String userId);
    public void subirNivel(String userId, int puntuacion, Date fecha);
    public void finishGame(String userId);
    public List<Usuario> usuariosJuego(String juegoId);
    public List<Partida> usuarioPartida(String userId);
}
