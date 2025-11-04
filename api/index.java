package api;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class Index {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);
        
        router.route().handler(BodyHandler.create());
        
        // Rota de health check
        router.get("/").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response
                .putHeader("content-type", "text/plain")
                .end("Somar API - Spring Boot no Vercel");
        });
        
        router.get("/health").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response
                .putHeader("content-type", "application/json")
                .end("{\"status\":\"OK\",\"service\":\"somar-api\"}");
        });
        
        // Inicia o servidor
        vertx.createHttpServer()
            .requestHandler(router)
            .listen(Integer.parseInt(System.getenv("PORT")), "0.0.0.0");
    }
}