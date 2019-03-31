package senai.fatesg.spark.api;

import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.path;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import senai.fatesg.spark.model.Agenda;
import senai.fatesg.spark.model.Telefone;

public class ServiceApi {
	final static Logger LOGGER = Logger.getLogger(ServiceApi.class);
    final static String CONTENT_TYPE = "application/json";
    final static String TOKEN = "Bearer b9QLldmqZSVSsLfbubqR35SaTTzN8QVD";
    
    
    public static void main(String[] args) {
    	agendas();
    	fones();
    }
    
    public static void agendaApi() {
    	
    }
    
    public static void agendas() {
    	List<Agenda> lista = new ArrayList<Agenda>();
    	/**
    	 * PATH atualiza o campo especificado
    	 */
    	path("/api", () -> {
    		before("/*", (request, response) -> LOGGER.info("Received api call"));
    		path("/agenda", () -> {
    			/**
                 * GET retorna uma agenda
                 */
    			get("", (request, response) -> {
    				response.type(CONTENT_TYPE);
    				final String mensagem = "Acesso ao método POST";
    				LOGGER.info(mensagem);
    				response.status(200);
    				return new Gson().toJson(lista);
    			});
    			/**
                 * POST cria uma agenda
                 */
    			post("", (request, response) -> {
    				response.type(CONTENT_TYPE);
    				Agenda agenda = new Gson().fromJson(request.body(), Agenda.class);
    				lista.add(agenda);
    				final String mensagem = "Acesso ao método POST";
    				LOGGER.info(mensagem);
    				
    				response.status(201);
    				
    				return new Gson().toJson(agenda);
    			});
    			/**
                 * PUT cria e atualiza um telefone
                 */
    			put("", (request, response) -> {
    				Agenda agenda = new Gson().fromJson(request.body(), Agenda.class);
    				lista.removeIf(u -> u.getId().equals(agenda.getId()));
    				lista.add(agenda);
    				
    				final String mensagem = "Acesso ao método PUT";
    				LOGGER.info(mensagem);
    				response.status(201);
    				return new Gson().toJson(agenda);
    			});
    			
    			delete("/:id", (request, response) -> {
    				Agenda agenda = new Agenda();
    				agenda.setId(Long.parseLong(request.params(":id")));
    				
    				lista.removeIf(u -> u.getId().equals(agenda.getId()));
    				
    				final String mensagem = "Acesso ao método DELETE. Registro deletado.";
    				LOGGER.info(mensagem);
    				response.status(204);
    				return mensagem;
    			});
    		});
    	});
    }
    
    public static void fones() {
    	
    	 List<Telefone> lista = new ArrayList<Telefone>();

         path("/api", () -> {
             before("/*", (request, response) -> {

                 if(request.pathInfo().endsWith("/")){
                     request.pathInfo().substring(0, request.pathInfo().length() - 1);
                 }

                 LOGGER.info("Received api call");

                 boolean authenticated = false;

                 authenticated = TOKEN.equals(request.headers("Authorization"));

                 if (!authenticated) {
                     halt(401, new Gson().toJson("Sessao não autorizada. Favor informar TOKEN de acesso."));
                 }
             });
             
             path("/telefone/", () -> {

            	 /**
            	  * Get retorna a lista de telefones
            	  */
                 get("", (request, response) -> {

                     //"TIPANDO" o retorno da requisição
                     response.type(CONTENT_TYPE);
                     final String mensagem = "Acesso ao método GET";
                     LOGGER.info(mensagem);
                     response.status(200);
                     return new Gson().toJson(lista);
                 });
                 
                 /**
                  * Get /:id retorna telefone por um id
                  */
                 get("/:id", (request, response) -> {

                     //"TIPANDO" o retorno da requisição
                     response.type(CONTENT_TYPE);
                     final String mensagem = "Acesso ao método GET";
                     LOGGER.info(mensagem);
                     response.status(200);
                     return new Gson().toJson(lista.stream()
                             .filter(c -> c.getId().equals(Long.parseLong(request.params(":id"))))
                             .findAny().orElse(null));
                 });

                 /**
                  * POST cria um novo telefone
                  */
                 post("", (request, response) -> {

                     //"TIPANDO" o retorno da requisição

                     response.type(CONTENT_TYPE);

                     Telefone telefone = new Gson().fromJson(request.body(), Telefone.class);
                     lista.add(telefone);

                     final String mensagem = "Acesso ao método POST";
                     LOGGER.info(mensagem);

                     response.status(201);

                     return new Gson().toJson(telefone);
                 });
                 /**
                  * PUT cria e atualiza um telefone
                  */
                 put("", (request, response) -> {

                	 Telefone telefone = new Gson().fromJson(request.body(), Telefone.class);
                     lista.removeIf(u -> u.getId().equals(telefone.getId()));
                     lista.add(telefone);

                     final String mensagem = String.format("Acesso ao método PUT. Registro atualizado %d",telefone.getId());
                     LOGGER.info(mensagem);
                     response.status(201);
                     return new Gson().toJson(telefone);
                 });
                 /**
                  * DELETE deleta um telefone
                  */
                 delete("/:id", (request, response) -> {

                     lista.removeIf(c -> c.getId().equals(Long.parseLong(request.params(":id"))));

                     final String mensagem = String.format("Acesso ao método DELETE. Registro deletado %s.",request.params(":id"));
                     LOGGER.info(mensagem);
                     response.status(204);
                     return mensagem;
                 });
             });
         });
    	
    }
    
}
