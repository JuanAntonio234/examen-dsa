package edu.upc.dsa;

import edu.upc.dsa.models.Juego;
import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.Usuario;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;


import javax.servlet.http.Part;

public class GameEngineImpl implements GameEngine {
    private static GameEngine instance;
    protected List<Usuario> usuarios;
    protected List<Partida> partidas;
    protected List<Juego> juegos;
    final static Logger logger = Logger.getLogger(GameEngineImpl.class);

    private GameEngineImpl() {
        this.usuarios = new ArrayList<>();
        this.partidas=new LinkedList<>();
        this.juegos=new ArrayList<>();
    }

    public static GameEngine getInstance() {
        if (instance==null) instance = new GameEngineImpl();
        return instance;
    }

    @Override
    public void crearUsuario(String nombre) {
        Usuario newUser=new Usuario(nombre);
        usuarios.add(newUser);
        logger.info("Usuario creado"+nombre);
    }

    @Override
    public void eliminarUsuario(String userId) {
        usuarios.removeIf(usuario -> usuario.getIdUsuario().equals(userId));
    }

    @Override
    public void actualizarUsuario(String userId, String nuevoNombre) {
        for (Usuario usuario : usuarios) {
            if (usuario.getIdUsuario().equals(userId)) {
                usuario.setNombre(nuevoNombre);
                break;
            }
        }
    }

    public boolean createGame(String gameId, String descripcion, int numNiveles) {
        if (gameId == null || gameId.isEmpty() || descripcion == null || descripcion.isEmpty() || numNiveles <= 0) {
            logger.error("No se puede crear el juego");
            return false;
        }else {
            Juego newJuego=new Juego(gameId,descripcion,numNiveles);
            logger.info("Creando un nuevo juego: gameId= "+gameId+", descripcion= "+descripcion+",numNiveles="+numNiveles);
            return true;
        }
    }

    public boolean startGame(String gameId,String userId){
        for(Partida partida : partidas){
            if(partida.getUsuario().getIdUsuario().equals(userId)){
                logger.error("El jugador ya dispone de una partida activa." );
                return false;
            }
        }
        Juego juego =null;
        for(Juego j:juegos){
            if(j.getIdJuego().equals(gameId)){
                juego=j;
                break;
            }
        }
        if(juego==null){
            logger.error("No existe el juego");
            return false;
        }
        Usuario usuario=new Usuario(userId);
        Partida nuevaPartida= new Partida(usuario,juego);
        nuevaPartida.setNivelActual(1);
        nuevaPartida.setPuntuacion(50);

        partidas.add(nuevaPartida);

        logger.info("Partida iniciada por " + userId + " en el juego " + gameId + " en el primer nivel con 50 puntos iniciales.");
        return true;
    }
    public int getNivelActual(String userId) {
    logger.info("getNivelActual(" + userId + ")");

    boolean usuarioEncontrado = false;
    int nivelActual = -1;
    String idPartida=null;
    for (Partida partida : partidas) {
        if (partida.getUsuario().getIdUsuario().equals(userId)) {
            nivelActual = partida.getNivelActual();
            idPartida = partida.getPartidaId();
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
        String idPartida=null;
        for (Partida partida : partidas) {
            if (partida.getUsuario().getIdUsuario().equals(userId)) {
                puntuacionActual = partida.getPuntuacion();
                idPartida = partida.getPartidaId();
                usuarioEncontrado = true;
                break;
            }
        }
        if (usuarioEncontrado) {
            logger.info("Puntuacion Actual: " + puntuacionActual);
            return puntuacionActual;
        } else {
            logger.error("El jugador no existe o no está en ninguna partida actualmente");
            return -1;
        }
    }

    public void subirNivel(String userId, int puntuacion, Date fecha){
        logger.info("subirNivel(" + userId + ")");

        boolean usuarioEncontrado = false;
        String idPartida=null;
        for (Partida partida : partidas) {
            if (partida.getUsuario().getIdUsuario().equals(userId)) {
                usuarioEncontrado = true;
                idPartida = partida.getPartidaId();
                int nivelActual=partida.getNivelActual();
                int puntuacionActual = partida.getPuntuacion();
                logger.info("puntuacion actual:"+puntuacionActual);
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
                break;
            }
        }
        if (!usuarioEncontrado) {
            logger.error("El jugador no existe o no está en ninguna partida actualmente");
        }
    }

    public void finishGame(String userId){
        logger.info("finishGame("+userId+")");
        boolean partidaEncontrada=false;

        List<Partida>nuevasPartidas=new ArrayList<>();
        for(Partida partida:partidas){
            if(partida.getUsuario().getIdUsuario().equals(userId)){
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

    public List<Usuario> usuariosJuego(String gameId){
        Juego juego=Juego.buscarJuegoPorId(juegos,gameId);

        if(juego==null){
            logger.error("El juego no existe");
            return Collections.emptyList();
        }else{
            List<Usuario> usuariosParticipantes=new ArrayList<>();
            for(Usuario usuario:usuarios){
                int puntTotal=getPuntuacionTotal(usuario,gameId);

                if(puntTotal>0){
                    usuariosParticipantes.add(usuario);
                }
            }
            usuariosParticipantes.sort(Comparator.comparingInt(u -> -getPuntuacionTotal(u, gameId)));
            logger.info("Consulta de usuarios que han participado en un juego ordenado por puntuación (descendente). "+usuariosParticipantes);
            return usuariosParticipantes;
        }
    }
    private int getPuntuacionTotal(Usuario usuario,String gameId){
        int puntTotal = 0;

        for (Partida partida : partidas) {
            if (partida.getJuegoId().equals(gameId) && partida.getUsuario().equals(usuario)) {
                puntTotal += partida.getPuntuacion();
            }
        }
        return puntTotal;
    }
    public List<Partida> usuarioPartida(String userId){
        logger.info("partidasDeUsuario(" + userId + ")");
        List<Partida>partidasDelUsuario=new ArrayList<>();

        boolean usuarioEncontrado=false;

        for(Partida partida: partidas){
            if(partida.getUsuario().getIdUsuario().equals(userId)){
                partidasDelUsuario.add(partida);
                usuarioEncontrado=true;
            }
        }
        if(usuarioEncontrado){
            for(Partida partida : partidasDelUsuario){
                logger.info("partida id:"+partida.getPartidaId()+"puntuacion:"+partida.getPuntuacion());
            }
        }else{
            logger.error("No existe el jugador o no ha jugado ninguna partida por el momento");
        }
        return partidasDelUsuario;
    }
}