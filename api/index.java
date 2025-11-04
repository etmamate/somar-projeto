import io.vertx.core.Vertx;

public class Index {
  public static void main(String[] args) {
    // Este arquivo é apenas um proxy para o Spring Boot
    // Sua aplicação real está em SomarApplication.java
    Vertx.vertx().deployVerticle(new io.vertx.core.AbstractVerticle() {
      @Override
      public void start() {
        // O Vercel requer este arquivo, mas a lógica principal está no JAR
        vertx.createHttpServer()
          .requestHandler(req -> {
            // Redireciona para o Spring Boot
            req.response()
              .putHeader("content-type", "text/plain")
              .end("Carregando aplicação Spring Boot...");
          })
          .listen(Integer.parseInt(System.getenv("PORT")));
      }
    });
  }
}