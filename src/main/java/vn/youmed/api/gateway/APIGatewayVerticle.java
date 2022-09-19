package vn.youmed.api.gateway;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.youmed.api.apis.gatewayDO.WorkerInfo;
import vn.youmed.api.apis.studentApi.constants.SomeContants;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class APIGatewayVerticle extends AbstractVerticle {
<<<<<<< HEAD
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
=======
    private static final Logger LOGGER = LoggerFactory.getLogger(APIGatewayVerticle.class);

    private static final Map<String, WorkerInfo> mapAddress = new HashMap<>();


    @Override
    public void start(Future<Void> startFuture) throws Exception {
        LOGGER.info("start Gateway ... ");
        vertx.eventBus().consumer("gateway", handler -> {
            LOGGER.info("gateway receive worker information:  {}", handler.body());
            WorkerInfo workerInfo = JsonObject.mapFrom(handler.body()).mapTo(WorkerInfo.class);
            LOGGER.info("{}", workerInfo);
            mapAddress.put(workerInfo.getWorker(), workerInfo);
            handler.reply(Json.encodePrettily(workerInfo));
        });
>>>>>>> c9db8adb73d5323668d020a589ea0428697cd370
        Router router = Router.router(vertx);
        router.route("/*").handler(this::dispatchRequest);
        createHttpServer(router, startFuture);

    }

    public void createHttpServer(Router router, Future<Void> startFuture) {
        vertx.createHttpServer().requestHandler(router).listen(8080, result -> {
            if (result.succeeded()) {
                LOGGER.info("starting Gateway on port: {} is succeeded ", result.result().actualPort());
                startFuture.complete();
            } else {
                LOGGER.error("start fail {}", result.cause());
                startFuture.complete();
            }
        });
        router.route();
    }

<<<<<<< HEAD
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
=======
    public void dispatchRequest(RoutingContext routingContext) {
        LOGGER.info("doDispatch uri = {}", routingContext.request().uri());
>>>>>>> c9db8adb73d5323668d020a589ea0428697cd370
        HttpMethod httpMethod = routingContext.request().method();
        String pathParams = routingContext.request().path();
        String queryParams = routingContext.request().query();
//        lay ra string sau slash dau tien
        String[] output = routingContext.request().uri().split("/");
        String api = output[1];
        JsonObject message = new JsonObject().put("api", api)
                .put("method", httpMethod).put("params", pathParams).put("query", queryParams);
        Optional<WorkerInfo> optional = getNext();
        if (optional.isPresent()) {
            WorkerInfo next = optional.get();
            LOGGER.info("chosen worker :{}", next.getWorker());
            vertx.eventBus().send(next.getWorker(), message, replyHandler -> {
                LOGGER.info("sending message...");
                if (replyHandler.succeeded()) {
                    routingContext
                            .request()
                            .response()
                            .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                            .end(Json.encodePrettily(Json.decodeValue(replyHandler.result().body().toString())));
                    LOGGER.info("replyHandler succeeded");
                } else {
                    routingContext
                            .request()
                            .response()
                            .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                            .end(Json.encodePrettily(replyHandler.cause()));
                    LOGGER.error("replyHandler failed");
                    LOGGER.error("replyHandler cause {}", replyHandler.cause());
                }
            });
            updateRequestedWorker(next);
        } else {
            routingContext
                    .request()
                    .response()
                    .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                    .setStatusCode(503).end("Service not available");

        }
    }

    private void updateRequestedWorker(WorkerInfo requested) {
        requested.setCurrentRequest(requested.getCurrentRequest() + 1);
        requested.setRequested(true);
        LOGGER.debug(" {} currentRequest {}", requested.getWorker(), requested.getCurrentRequest());
        mapAddress.put(requested.getWorker(), requested);
    }

    public Optional<WorkerInfo> getNext() {
//        lay ra cac entry set (unique)
        Set<Map.Entry<String, WorkerInfo>> entries = mapAddress.entrySet();
        LOGGER.info("map {}", mapAddress);
        if (entries.isEmpty()) {
            LOGGER.warn("worker not available");
            return Optional.empty();
        }
//        loop cai set
        WorkerInfo next = null;

        for (Map.Entry<String, WorkerInfo> entry : entries) {
            WorkerInfo workerInfo = entry.getValue();
            if (!workerInfo.isRequested() && workerInfo.getCurrentRequest() < workerInfo.getNumberRequest()) {
                next = workerInfo;
                return Optional.of(next);
            }
        }
        if (next == null) {
            tryToRefreshAddressMap();
            return getNext();
        } else {
            return Optional.of(next);
        }

<<<<<<< HEAD
>>>>>>> befe05054e4e27dac16c90486eabea7af0095718
=======
  //        return mapAddress.entrySet().stream().filter(entry -> {
//            WorkerInfo workerInfo = entry.getValue();
//            return !workerInfo.isRequested() && workerInfo.getCurrentRequest() < workerInfo.getNumberRequest();
//        }).findAny().map(Map.Entry::getValue).orElse(null);
//        optional.map(Map.Entry::getValue).orElse(null);
    }

    private void tryToRefreshAddressMap() {
        if (allOfWorkerFullRequest()) {
            LOGGER.warn("all of worker request receive is full, try to reset all worker");
            resetAllWorkers();
        } else {
            makeRequestAble();
        }
    }

    private void makeRequestAble() {
        Set<Map.Entry<String, WorkerInfo>> entries = mapAddress.entrySet();
        for (Map.Entry<String, WorkerInfo> entry : entries) {
            WorkerInfo workerInfo = entry.getValue();
            if (workerInfo.getCurrentRequest() < workerInfo.getNumberRequest()) {
                LOGGER.warn("make {} request able", workerInfo.getWorker());
                workerInfo.setRequested(false);
            }
        }
    }

    private void resetAllWorkers() {
        Set<Map.Entry<String, WorkerInfo>> entries = mapAddress.entrySet();
        for (Map.Entry<String, WorkerInfo> entry : entries) {
            WorkerInfo workerInfo = entry.getValue();
            workerInfo.setCurrentRequest(0);
            workerInfo.setRequested(false);
        }
    }

    private boolean allOfWorkerFullRequest() {
        Set<Map.Entry<String, WorkerInfo>> entries = mapAddress.entrySet();
        int full = 0;
        String workerName = null;
        for (Map.Entry<String, WorkerInfo> entry : entries) {
            if (entry.getValue().getCurrentRequest() == entry.getValue().getNumberRequest()) {
                workerName = entry.getValue().getWorker();
                full++;
            }
>>>>>>> c9db8adb73d5323668d020a589ea0428697cd370
        }
        LOGGER.warn("{} is full request", workerName);
        return full == entries.size();
    }


}
