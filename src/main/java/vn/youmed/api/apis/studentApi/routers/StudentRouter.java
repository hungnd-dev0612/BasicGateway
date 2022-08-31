package vn.youmed.api.apis.studentApi.routers;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.youmed.api.apis.studentApi.routers.handlers.ClassHandler;
import vn.youmed.api.apis.studentApi.routers.handlers.SpecialityHandler;
import vn.youmed.api.apis.studentApi.routers.handlers.StudentHandler;

public class StudentRouter {
    private final Vertx vertx;
    private final StudentHandler studentHandler;
    private final ClassHandler classHandler;
    private final SpecialityHandler specialityHandler;
    private final Logger LOGGER = LoggerFactory.getLogger(StudentRouter.class);

    public StudentRouter(Vertx vertx, StudentHandler studentHandler, ClassHandler classHandler, SpecialityHandler specialityHandler) {
        this.vertx = vertx;
        this.studentHandler = studentHandler;
        this.classHandler = classHandler;
        this.specialityHandler = specialityHandler;
    }

    public Router getRouter() {
        Router router = Router.router(vertx);
        router.get("/students").handler(studentHandler::getAll);
        router.post("/students").handler(studentHandler::insert);
        router.put("/students/:id").handler(studentHandler::update);
        router.delete("/students/:id").handler(studentHandler::delete);
        LOGGER.info("go into student router {}");

        router.get("/classes").handler(classHandler::getAll);
        router.post("/classes").handler(classHandler::insert);
        router.put("/classes/:id").handler(classHandler::update);
        router.delete("/classes/:id").handler(classHandler::delete);
        LOGGER.info("go into class router {}");

        router.get("/specialities").handler(specialityHandler::getAll);
        router.post("/specialities").handler(specialityHandler::insert);
        router.put("/specialities/:id").handler(specialityHandler::update);
        router.delete("/specialities/:id").handler(specialityHandler::delete);
        LOGGER.info("go into speciality router {}");

        return router;
    }


    public StudentHandler getStudentHandler() {
        return studentHandler;
    }
}

