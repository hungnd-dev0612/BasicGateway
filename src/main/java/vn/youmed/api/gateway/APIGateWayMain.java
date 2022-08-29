package vn.youmed.api.gateway;

import io.vertx.core.Vertx;
import vn.youmed.api.apis.StudentVerticle;

public class APIGateWayMain {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new APIGatewayVerticle());
        vertx.deployVerticle(new StudentVerticle());
    }
}
