package edu.upc.dsa;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by juan on 16/11/16.
 */
public class JuegoServiceTest {
    private GameEngine gameEngine;



    @Test
    public void testIniciarPartida() throws Exception {
        String userId = "Perico";
        String gameId = "prince of persia";

        String resultado = gameEngine.startGame(userId, gameId);
        assertEquals("Perico ha iniciado la partida en prince of persia en el primer nivel con 50 puntos iniciales.", resultado);
    }

    @Test
    public void testIniciarPartida() throws Exception {
        String userId = "usuari1";
        String gameId = "JuegoInexistente";

        String resultado = gameEngine.startGame(userId, gameId);

        assertEquals("Error: El usuario con el id usuari1  no existe.", resultado);

    }

}