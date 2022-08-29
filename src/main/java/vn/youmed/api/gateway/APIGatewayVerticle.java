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

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class APIGatewayVerticle extends AbstractVerticle {
    private final Logger LOGGER = LoggerFactory.getLogger(APIGatewayVerticle.class);
    public static final NavigableMap<String, Integer> mapAddress = new TreeMap<>();

    static {
        mapAddress.put("worker1", 0);
        mapAddress.put("worker2", 0);
        mapAddress.put("worker3", 0);
        mapAddress.put("worker4", 0);
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
//        Router router = new Facade(vertx).getStudentRouter().getRouter();
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
        }
        return address;
    }


}
