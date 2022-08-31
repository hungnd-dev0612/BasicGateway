package vn.youmed.api.apis;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.youmed.api.apis.studentApi.dto.DTOMessage.StudentMessage;
import vn.youmed.api.apis.studentApi.dto.StudentDTO;
import vn.youmed.api.apis.studentApi.pattern.Facade;
import vn.youmed.api.apis.studentApi.routers.handlers.StudentHandler;
import vn.youmed.api.apis.studentApi.services.StudentService;

import java.util.ArrayList;
import java.util.List;

public class StudentVerticle1 extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentVerticle1.class);

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        getRequestFromEventBus();
    }

    public void getRequestFromEventBus() {

        List<JsonObject> studentDTOList = new ArrayList<>();
        vertx.eventBus().consumer("worker1", msg -> {
            LOGGER.warn("Goi vao thang so 1");
//            parse json request from gateway to this dto and get method, /api, params, query
            StudentMessage studentMessage = JsonObject.mapFrom(msg.body()).mapTo(StudentMessage.class);
            Facade facade = new Facade(vertx);
            StudentService service = facade.getStudentService();
            if (studentMessage.getPath().equals("students") && studentMessage.getMethodType().equals(HttpMethod.GET)) {
                service.getAll().setHandler(ar -> {
                    if (ar.succeeded()) {
                        for (StudentDTO dto : ar.result()) {
                            JsonObject jsonObject1 = JsonObject.mapFrom(dto);
                            studentDTOList.add(jsonObject1);
                        }
                        msg.reply(Json.encodePrettily(studentDTOList));

                    } else {
                        msg.reply(ar.cause());
                    }

                });
            }
//
//            msg.reply(msg.body());
        });

    }

    public void getRouter(StudentMessage studentMessage, Router router) {
        Facade facade = new Facade(vertx);
        StudentHandler studentHandler = facade.getStudentRouter().getStudentHandler();
        router.route(studentMessage.getMethodType(), "/" + studentMessage.getPath()).handler(studentHandler::getAll);
        router.route(studentMessage.getMethodType(), "/" + studentMessage.getPath()).handler(studentHandler::insert);
        router.route(studentMessage.getMethodType(), "/" + studentMessage.getPath()).handler(studentHandler::update);
        router.route(studentMessage.getMethodType(), "/" + studentMessage.getPath()).handler(studentHandler::delete);
        router.get("/" + studentMessage.getPath()).handler(studentHandler::getAll);

//        router.get("/students").handler(studentHandler::getAll);
//        router.post("/students").handler(studentHandler::insert);
//        router.put("/students/:id").handler(studentHandler::update);
//        router.delete("/students/:id").handler(studentHandler::delete);
//        LOGGER.info("go into student router {}");
//
//        router.get("/classes").handler(classHandler::getAll);
//        router.post("/classes").handler(classHandler::insert);
//        router.put("/classes/:id").handler(classHandler::update);
//        router.delete("/classes/:id").handler(classHandler::delete);
//        LOGGER.info("go into class router {}");
//
//        router.get("/specialities").handler(specialityHandler::getAll);
//        router.post("/specialities").handler(specialityHandler::insert);
//        router.put("/specialities/:id").handler(specialityHandler::update);
//        router.delete("/specialities/:id").handler(specialityHandler::delete);
//        LOGGER.info("go into speciality router {}");

    }
}
