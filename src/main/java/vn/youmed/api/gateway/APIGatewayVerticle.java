package vn.youmed.api.gateway;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.youmed.api.apis.studentApi.constants.SomeContants;
import vn.youmed.api.apis.studentApi.pattern.Facade;
import vn.youmed.api.apis.studentApi.routers.handlers.StudentHandler;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class APIGatewayVerticle extends AbstractVerticle {
    private final Logger LOGGER = LoggerFactory.getLogger(APIGatewayVerticle.class);
    public static final NavigableMap<String, Integer> mapAddress = new TreeMap<>();

<<<<<<< HEAD
=======
    private static final NavigableMap<String, Integer> mapAddress = new TreeMap<>();

>>>>>>> befe05054e4e27dac16c90486eabea7af0095718
    static {
        mapAddress.put("worker1", 0);
        mapAddress.put("worker2", 0);
        mapAddress.put("worker3", 0);
<<<<<<< HEAD
        mapAddress.put("worker4", 0);
=======
>>>>>>> befe05054e4e27dac16c90486eabea7af0095718
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
<<<<<<< HEAD
//        Router router = new Facade(vertx).getStudentRouter().getRouter();
=======
>>>>>>> befe05054e4e27dac16c90486eabea7af0095718
        Router router = Router.router(vertx);
        router.route("/*").handler(this::publishMessage);
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
<<<<<<< HEAD
        String address = getAddress();
        vertx.eventBus().send(address, "JsonObject", msg -> {
            routingContext.request().response().end(address);
        });
    }

    public String getAddress() {
        String address = null;
        String lastKey = null;
        Map.Entry<String, Integer> lastEntry = mapAddress.lastEntry();
        for (Map.Entry<String, Integer> entry : mapAddress.entrySet()) {
            if (entry.getValue() == 0) {
                address = entry.getKey();
                entry.setValue(+1);
                LOGGER.info("map over time{}", mapAddress);
                if (entry.getKey().equals(lastEntry.getKey())) {
                    lastKey = entry.getKey();
                    address = lastKey;
                    mapAddress.replaceAll((k, v) -> v = 0);
                }
                return address;
            }
=======
        HttpMethod httpMethod = routingContext.request().method();
        String pathParams = routingContext.request().path();
        String queryParams = routingContext.request().query();
        Facade facade = new Facade(vertx);
        StudentHandler studentHandler = facade.getStudentRouter().getStudentHandler();
//        lay ra string sau slash dau tien
        String[] output = routingContext.request().uri().split("/");
        String api = output[1];
        LOGGER.info("output {}", api);
        LOGGER.info("method {}", httpMethod);
        JsonObject message = new JsonObject().put("api", api)
                .put("method", httpMethod).put("params", pathParams).put("query", queryParams);
        String address = getAddress();
        LOGGER.warn(" address hereee {}", address);
        vertx.eventBus().send(address, message, msg -> {
            routingContext.request()
                    .response()
                    .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                    .end(Json.encodePrettily(Json.decodeValue(msg.result().body().toString())));
        });


    }

    private String getAddress() {
        String lastKey = mapAddress.lastKey();
        String address = null;

        for (Map.Entry<String, Integer> entry : mapAddress.entrySet()) {
            if (entry.getValue() == 0) {
                address = entry.getKey();
                int countRequest = entry.getValue();
                entry.setValue(countRequest + 1);
                if (lastKey.equals(entry.getKey())) {
                    String temporary = lastKey;
                    mapAddress.replaceAll((k, v) -> v = 0);
                    address = temporary;
                }
                return address;
            }

>>>>>>> befe05054e4e27dac16c90486eabea7af0095718
        }
        return address;
    }


}
