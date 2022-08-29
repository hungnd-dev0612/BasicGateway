package vn.youmed.api.apis.studentApi.routers.handlers;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import vn.youmed.api.apis.studentApi.constants.SomeContants;
import vn.youmed.api.apis.studentApi.dto.ClassDTO;
import vn.youmed.api.apis.studentApi.services.ClassService;

public class ClassHandler {
    private final ClassService service;

    public ClassHandler(ClassService service) {
        this.service = service;
    }

    public void insert(RoutingContext routingContext) {
        String specialitiesId = routingContext.request().getParam("specialitiesId");
        routingContext.request().bodyHandler(buffer -> {
            JsonObject json = buffer.toJsonObject();
            ClassDTO dto = json.mapTo(ClassDTO.class);
            service.insert(dto, specialitiesId).setHandler(handler -> {
                if (handler.succeeded()) {
                    routingContext.response()
                            .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                            .end(Json.encodePrettily(handler.result()));
                } else {
                    routingContext.response()
                            .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                            .setStatusCode(400)
                            .end(Json.encodePrettily(handler.cause().getMessage()));
                }
            });
        });
    }

    public void getAll(RoutingContext routingContext) {
        service.getAll().setHandler(handler -> {
            if (handler.succeeded()) {
                routingContext.response()
                        .putHeader("content-type", "application/json")
                        .putHeader("Access-Control-Allow-Origin", "http://localhost:3000")
                        .putHeader("Access-Control-Allow-Methods","GET, POST, OPTIONS")
                        .putHeader("Access-Control-Allow-Credentials", "true")
                        .end(Json.encodePrettily(handler.result()));
            } else {
                routingContext.response()
                        .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                        .end(Json.encodePrettily(handler.cause()));
            }
        });

    }

    public void update(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        JsonObject checkNull = new JsonObject().put("id", "id iis null");
        if (id == null) {
            routingContext.response().setStatusCode(200)
                    .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                    .end(Json.encodePrettily(checkNull.encodePrettily()));
        }
        routingContext.request().bodyHandler(buffer -> {
            JsonObject json = buffer.toJsonObject();
            ClassDTO dto = json.mapTo(ClassDTO.class);
            service.update(id, dto).setHandler(handler -> {
                if (handler.succeeded()) {
                    routingContext.response().setStatusCode(200)
                            .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                            .end(Json.encodePrettily(handler.result()));
                } else {
                    routingContext.response().setStatusCode(400)
                            .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                            .end(Json.encodePrettily(handler.cause()));
                }
            });


        });


    }

    public void delete(RoutingContext routingContext) {
        service.delete(routingContext.request().getParam("id"));
        routingContext.response().setStatusCode(200).putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                .end(new JsonObject().put("delete", "succeeded").encodePrettily());
    }

    public void findById(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        JsonObject json = new JsonObject();
        service.findById(id).setHandler(handler -> {
            if (handler.succeeded()) {
                JsonObject json2 = JsonObject.mapFrom(handler.result());
                routingContext.response()
                        .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                        .end(json2.encodePrettily());
            } else {
                routingContext.response()
                        .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                        .end(json.toString());
            }
        });
    }


}
