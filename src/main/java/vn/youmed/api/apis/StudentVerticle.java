package vn.youmed.api.apis;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.youmed.api.apis.studentApi.pattern.Facade;

public class StudentVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentVerticle.class);

    @Override
    public void start(Future<Void> startFuture) throws Exception {
//        createHttp(new Facade(vertx).getStudentRouter().getRouter());
        address();
    }

    public Future<HttpServer> createHttp(Router router) {
        Future<HttpServer> createServerHandler = Future.future();
        vertx.createHttpServer().requestHandler(router).listen(8081, createServerHandler);
        createServerHandler.setHandler(handler -> {
            if (handler.succeeded()) {
                LOGGER.info("start http succeeded {} ", handler.succeeded());
                createServerHandler.complete();
            } else {
                LOGGER.error("start http fail ", handler.cause());
                createServerHandler.fail(handler.cause());
            }
        });

        return createServerHandler;
    }

    public void address() {
        vertx.eventBus().consumer("worker1", msg -> {
            if (msg.isSend()) {
                LOGGER.info(Json.encodePrettily(msg.body()));
                msg.reply(Json.encodePrettily(msg.body()));
            } else {
//                LOGGER.error(msg.fail(1, "fail"));
                msg.fail(1, "fail");
            }

        });
    }

}
