package edu.upc.dsa;

import edu.upc.dsa.models.Juego;
import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.Usuario;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by juan on 16/11/16.
 */
public class JuegoServiceTest {
    private GameEngine gameEngine;

    @Before
    public void setUp() throws Exception {
        this.gameEngine = GameEngineImpl.getInstance();
    }

    @Test
    public void testCrearJuegoConIdExistente() throws Exception {

        String gameId = "prince of persia";
        String descripcion="Descripción juego";
        int numNiveles=2;
        boolean resultado=gameEngine.createGame(gameId,descripcion,numNiveles);

        Assert.assertFalse(resultado);
    }

    @Test
    public void testConsultaUsuariosPuntuacionDescendente(){
        Juego juego1=new Juego("prince of persia","descripcion",4);
        Usuario usuario1=new Usuario("Usuario1");
        Usuario usuario2=new Usuario("Usuario2");
        Usuario usuario3=new Usuario("Usuario3");
        Usuario usuario4=new Usuario("Usuario4");

        Partida partida1=new Partida(usuario1,juego1);
        partida1.setPuntuacion(100);
        Partida partida2=new Partida(usuario2,juego1);
        partida2.setPuntuacion(75);
        Partida partida3=new Partida(usuario3,juego1);
        partida3.setPuntuacion(110);
        Partida partida4=new Partida(usuario4,juego1);
        partida4.setPuntuacion(140);

        List<Usuario> usuarioParticipantes=gameEngine.usuariosJuego("juego1");
        Assert.assertNotNull((usuarioParticipantes));
        Assert.assertEquals(usuario4,usuarioParticipantes.get(0));
        Assert.assertEquals(usuario3,usuarioParticipantes.get(1));
        Assert.assertEquals(usuario1,usuarioParticipantes.get(2));
        Assert.assertEquals(usuario2,usuarioParticipantes.get(3));
    }
    @Test
    public void testConsultaNivelActual(){
        String userId = "Perico";

        int nivelActual= gameEngine.getNivelActual(userId);

        Assert.assertTrue(nivelActual>=1);
    }
    @Test
    public void testConsultaPuntuacionActual() throws Exception {
        String userId="Perico";
        String gameId = "prince of persia";

        gameEngine.startGame(gameId,userId);
        int putuacionActual=gameEngine.getPuntuacionActual(userId);

        Assert.assertEquals(50,putuacionActual);
    }

}