package vn.youmed.api.apis;

import io.vertx.core.Vertx;

public class StudentApp {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new StudentVerticle3());
    }
}
