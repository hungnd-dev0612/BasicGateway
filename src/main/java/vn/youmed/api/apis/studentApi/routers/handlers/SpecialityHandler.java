package vn.youmed.api.apis.studentApi.routers.handlers;

import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.youmed.api.apis.studentApi.constants.SomeContants;
import vn.youmed.api.apis.studentApi.dto.SpecialityDTO;
import vn.youmed.api.apis.studentApi.services.SpecialityService;

public class SpecialityHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentHandler.class);

    private final SpecialityService service;

    public SpecialityHandler(SpecialityService service) {
        this.service = service;
    }


    public void getAll(RoutingContext routingContext) {
        service.getAll().setHandler(ar -> {
            if (ar.succeeded()) {
                routingContext.response()
                        .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                        .end(Json.encodePrettily(ar.result()));
            } else {
                routingContext.response()
                        .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                        .end(Json.encodePrettily(ar.cause()));
            }
        });
    }

    public void findById(RoutingContext routingContext) {
        service.findById(routingContext.request().getParam("id")).setHandler(ar -> {
            if (ar.succeeded()) {
                routingContext.response()
                        .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                        .end(Json.encodePrettily(ar.result()));
            } else {
                routingContext.response()
                        .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                        .end(Json.encodePrettily(ar.cause()));
            }
        });
    }

    public void insert(RoutingContext routingContext) {
        routingContext.request().bodyHandler(buffer -> {
            SpecialityDTO dto = buffer.toJsonObject().mapTo(SpecialityDTO.class);
            service.insert(dto).setHandler(ar -> {
                if (ar.succeeded()) {
                    routingContext.response()
                            .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                            .end(Json.encodePrettily(ar.result()));

                } else {
                    routingContext.response()
                            .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                            .end(Json.encodePrettily(ar.cause()));
                }
            });
        });


    }

    public void update(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        routingContext.request().bodyHandler(buffer -> {
            SpecialityDTO dto = buffer.toJsonObject().mapTo(SpecialityDTO.class);
            service.update(id, dto).setHandler(ar -> {
                if (ar.succeeded()) {
                    routingContext.response()
                            .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                            .end(Json.encodePrettily(ar.result()));

                } else {
                    routingContext.response()
                            .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                            .end(Json.encodePrettily(ar.cause()));
                }
            });
        });

    }

    public void delete(RoutingContext routingContext) {
        service.delete(routingContext.request().getParam("id"));
        routingContext.response()
                .putHeader(SomeContants.CONTENT_TYPE, SomeContants.CONTENT_VALUE)
                .end(Json.encodePrettily("delete succeeded"));
    }
}
