package vn.youmed.api.gateway;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.impl.Http2ServerRequestImpl;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.youmed.api.apis.studentApi.pattern.Facade;

public class APIGatewayVerticle extends AbstractVerticle {
    private final Logger LOGGER = LoggerFactory.getLogger(APIGatewayVerticle.class);


    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Router router = new Facade(vertx).getStudentRouter().getRouter();
        createHttpServer(router, startFuture);
    }

    public void createHttpServer(Router router, Future<Void> startFuture) {
        vertx.createHttpServer().requestHandler(router).listen(8080, event -> {
            if (event.succeeded()) {
                LOGGER.info("succeeded");
                startFuture.complete();
            } else {
                startFuture.complete();
            }
        });
        router.route();
    }

    public void publishMessage(RoutingContext routingContext) {
        vertx.eventBus().send("vn.youmed.api.student", "", msg -> {
//routingContext.request().handler()

        });
        vertx.eventBus().send("vn.youmed.api.classes", "", mess -> {

        });
        vertx.eventBus().send("vn.youmed.api.specialities", "", mess -> {

        });
    }
}
