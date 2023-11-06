package edu.upc.dsa.services;


import edu.upc.dsa.GameEngine;
import edu.upc.dsa.GameEngineImpl;
import edu.upc.dsa.models.Usuario;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Api(value = "/juego", description = "Endpoint to Juegos Service")
@Path("/juego")
public class JuegosServicios {

    private GameEngine gameEngine;

    public JuegosServicios() {
        this.gameEngine = GameEngineImpl.getInstance();
    }

    @POST
    @ApiOperation(value = "Crear un juego", notes = "Crea un nuevo juego")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Juego creado"),
            @ApiResponse(code = 500, message = "Error de validación")
    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createGame(@QueryParam("gameID") String gameID, @QueryParam("descripcion") String descripcion, @QueryParam("numNiveles") int numNiveles) {
        this.gameEngine.createGame(gameID, descripcion, numNiveles);
        return Response.status(201).build();
    }

     @POST
      @ApiOperation(value ="Iniciar un juego", notes = "Inicia un juego para un usuario")
      @ApiResponses(value = {  @ApiResponse(code = 201, message = "Juego iniciado"),
              @ApiResponse(code = 404, message = "Juego no encontrado")
      })
      @Path("/")
      public Response startGame(@QueryParam("gameId") String gameId, @QueryParam("userId") String userId) {
          boolean juegoIniciado = this.gameEngine.startGame(gameId, userId);
          if (juegoIniciado) {
              return Response.status(201).build();
          } else {
              return Response.status(404).build();
          }
      }
    @GET
    @ApiOperation(value = "Obtener el nivel actual de un usuario", notes = "Obtiene el nivel actual de un usuario en un juego")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exitoso"),
            @ApiResponse(code = 404, message = "Usuario no encontrado")
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNivelActual(@QueryParam("userId") String userId) {
        int nivelActual = this.gameEngine.getNivelActual(userId);
        if (nivelActual >= 0) {
            return Response.status(201).entity(nivelActual).build();
        } else {
            return Response.status(404).build();
        }
    }

    @GET
    @ApiOperation(value = "Obtener la puntuación actual de un usuario", notes = "Obtiene la puntuación actual de un usuario en un juego")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exitoso"),
            @ApiResponse(code = 404, message = "Usuario no encontrado")
    })
    @Path("/puntuacion")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPuntuacionActual(@QueryParam("userId") String userId) {
        int puntuacionActual = this.gameEngine.getPuntuacionActual(userId);
        if (puntuacionActual >= 0) {
            return Response.status(201).entity(puntuacionActual).build();
        } else {
            return Response.status(404).build();
        }
    }
   @POST
    @ApiOperation(value = "Subir de nivel", notes = "Sube de nivel a un usuario en un juego")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Nivel subido"),
            @ApiResponse(code = 404, message = "Usuario no encontrado")
    })
   @Path("/subirNivel")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response subirNivel(@QueryParam("userId") String userId, @QueryParam("puntuacion") int puntuacion, @QueryParam("fecha") Date fecha) {
        try{
            this.gameEngine.subirNivel(userId, puntuacion, fecha);
            return Response.status(200).build();
        }catch (Exception e){
            return Response.status(404).entity("Usuario no encontrado").build();
        }
    }

    @POST
    @ApiOperation(value = "Finalizar un juego", notes = "Finaliza el juego de un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Juego finalizado"),
            @ApiResponse(code = 404, message = "Usuario no encontrado")
    })
    @Path("/finish")
    public Response finishGame(@QueryParam("userId") String userId) {
        try{
            this.gameEngine.finishGame(userId);
            return Response.status(201).build();
        }catch (Exception e){
            return Response.status(404).entity("Usuario no encontrado").build();
        }
    }
}