package edu.upc.dsa;

import edu.upc.dsa.models.Juego;
import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import org.graalvm.compiler.lir.sparc.SPARCTailDelayedLIRInstruction;

import javax.servlet.http.Part;

public class GameEngineImpl implements GameEngine {
    private static GameEngine instance;
    protected List<Usuario> usuarios;
    protected List<Partida> partidas;
    final static Logger logger = Logger.getLogger(GameEngineImpl.class);

    private GameEngineImpl() {
        this.usuarios = new LinkedList<>();
    }

    public static GameEngine getInstance() {
        if (instance==null) instance = new GameEngineImpl();
        return instance;
    }

    public void createGame(String gameId, String descripcion, int numNiveles) {
        if (gameId == null || gameId.isEmpty() || descripcion == null || descripcion.isEmpty() || numNiveles <= 0) {
         //   throw new GameCreateException("No se pudo crear el juego. Parámetros inválidos.");
            logger.error("No se puede crear el juego");
        }
       Juego newJuego=new Juego(descripcion,numNiveles);
       logger.info("Creando un nuevo juego: gameId= "+gameId+", descripcion= "+descripcion+",numNiveles="+numNiveles);
    }
    public void StartGame(String gameId,String userId){

        if(userId==null|| userId.isEmpty()||gameId==null||gameId.isEmpty()){
            logger.error("No se puede empezar la partida");
            return;
        }

        logger.info("Partida iniciada por " + userId + " en el juego " + gameId + " en el primer nivel con 50 puntos iniciales.");

    }
    public int getNivelActual(String userId) {
    logger.info("getNivelActual(" + userId + ")");

    boolean usuarioEncontrado = false;
    int nivelActual = -1;
    String idPartida;
    for (Partida partida : partidas) {
        if (partida.getUsuario().equals(userId)) {
            nivelActual = partida.getNivelActual();
            idPartida = partida.getId();
            usuarioEncontrado = true;
            break;
        }
    }
    if (usuarioEncontrado) {
        logger.info("Nivel Actual: " + nivelActual);
        return nivelActual;
    } else {
        logger.error("El jugador no existe o no está en ninguna partida actualmente");
        return -1;
    }
}

    public int getPuntuacionActual(String userId) {
        logger.info("getPuntuacion(" + userId + ")");

        boolean usuarioEncontrado = false;
        int puntuacionActual = -1;
        String idPartida;
        for (Partida partida : partidas) {
            if (partida.getUsuario().equals(userId)) {
                puntuacionActual = partida.getPuntuacion();
                idPartida = partida.getId();
                usuarioEncontrado = true;
                break;
            }
        }
        if (usuarioEncontrado) {
            logger.info("Puntuación Actual: " + puntuacionActual);
            return puntuacionActual;
        } else {
            logger.error("El jugador no existe o no está en ninguna partida actualmente");
            return -1;
        }
    }

    public void subirNivel(String userId, int puntuacion, Date fecha){
        logger.info("getNivelActual(" + userId + ")");

        boolean usuarioEncontrado = false;
        int puntuacionActual = -1;
        String idPartida;
        for (Partida partida : partidas) {
            if (partida.getUsuario().equals(userId)) {
                puntuacionActual = partida.getPuntuacion();
                idPartida = partida.getId();
                usuarioEncontrado = true;
                break;
            }
        }
        if (usuarioEncontrado) {
            Partida partida=null;

            int nivelActual= partida.getNivelActual();
            logger.info("Puntuación Actual: " + puntuacionActual);
            puntuacionActual +=puntuacion;
            partida.setPuntuacion(puntuacionActual);
            if(nivelActual<partida.getJuego().getNumNiveles()){
              nivelActual++;
              partida.setNivelActual(nivelActual);
            }else{
                puntuacionActual+=100;
                partida.setPuntuacion(puntuacionActual);
                finishGame(userId);
            }

        } else {
            logger.error("El jugador no existe o no está en ninguna partida actualmente");

        }
    }

    public void finishGame(String userId){
        logger.info("finishGame("+userId+")");
        boolean partidaEncontrada=false;

        List<Partida>nuevasPartidas=new ArrayList<>();
        for(Partida partida:partidas){
            if(partida.getUsuario().equals(userId)){
                partidaEncontrada=true;
                logger.info("Partida del jugador"+userId+ "finalizada");
            }else{
                nuevasPartidas.add(partida);
            }
        }
        if(!partidaEncontrada){
            logger.error("No se ha encontrado ninguna partida activa del usuario:"+userId);
        }
        partidas=nuevasPartidas;
    }

    public List<Usuario> usuariosJuego(String juegoId){

    }
    public List<Partida> usuarioPartida(String userId){
        logger.info("partidasDeUsuario(" + userId + ")");
        List<Partida>partidasDelUsuario=new ArrayList<>();

        for(Partida partida: partidas){
            if(partida.getUsuario().equals(userId)){
                partidasDelUsuario.add(partida);
            }
        }
        if(partidasDelUsuario.isEmpty()){
            for(Partida partida : partidasDelUsuario){
                logger.info("partida id:"+partida.getId()+"puntuacion:"+partida.getPuntuacion());
            }
        }else{
            logger.error("No existe el jugador o no ha jugado ninguna partida por el momento");
        }
        return partidasDelUsuario;
    }

}