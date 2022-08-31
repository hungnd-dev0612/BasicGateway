package vn.youmed.api.gateway;

import io.vertx.core.Vertx;
import vn.youmed.api.apis.StudentVerticle1;
import vn.youmed.api.apis.StudentVerticle2;
import vn.youmed.api.apis.StudentVerticle3;

public class APIGateWayMain {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new APIGatewayVerticle());
//        vertx.deployVerticle(new StudentVerticle3());
//        vertx.deployVerticle(new StudentVerticle2());
//        vertx.deployVerticle(new StudentVerticle1());
    }
}
