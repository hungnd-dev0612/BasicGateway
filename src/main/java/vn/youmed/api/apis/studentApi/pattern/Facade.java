package vn.youmed.api.apis.studentApi.pattern;

import io.vertx.core.Vertx;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import vn.youmed.api.apis.studentApi.database.MongoConnection;
import vn.youmed.api.apis.studentApi.repository.ClassRepository;
import vn.youmed.api.apis.studentApi.repository.SpecialityRepository;
import vn.youmed.api.apis.studentApi.repository.StudentRepository;
import vn.youmed.api.apis.studentApi.repository.impl.ClassRepositoryImpl;
import vn.youmed.api.apis.studentApi.repository.impl.SpecialityRepositoryImpl;
import vn.youmed.api.apis.studentApi.repository.impl.StudentRepositoryImpl;
import vn.youmed.api.apis.studentApi.routers.StudentRouter;
import vn.youmed.api.apis.studentApi.routers.handlers.ClassHandler;
import vn.youmed.api.apis.studentApi.routers.handlers.SpecialityHandler;
import vn.youmed.api.apis.studentApi.routers.handlers.StudentHandler;
import vn.youmed.api.apis.studentApi.services.ClassService;
import vn.youmed.api.apis.studentApi.services.SpecialityService;
import vn.youmed.api.apis.studentApi.services.StudentService;
import vn.youmed.api.apis.studentApi.services.impl.ClassServiceImpl;
import vn.youmed.api.apis.studentApi.services.impl.SpecialityServiceImpl;
import vn.youmed.api.apis.studentApi.services.impl.StudentServiceImpl;

public class Facade {
    private final StudentRouter studentRouter;


    public Facade(Vertx vertx) {
        MongoClient client = MongoConnection.getConnection(vertx);
        SpecialityRepository specialityRepository = new SpecialityRepositoryImpl(client);
        SpecialityService specialityService = new SpecialityServiceImpl(specialityRepository);
        SpecialityHandler specialityHandler = new SpecialityHandler(specialityService);

        ClassRepository classRepository = new ClassRepositoryImpl(client);
        ClassService classService = new ClassServiceImpl(classRepository, specialityRepository);
        ClassHandler classHandler = new ClassHandler(classService);

        StudentRepository studentRepository = new StudentRepositoryImpl(client);
        StudentService studentService = new StudentServiceImpl(studentRepository);
        StudentHandler studentHandler = new StudentHandler(studentService);

        studentRouter = new StudentRouter(vertx, studentHandler, classHandler, specialityHandler);
    }

    public StudentRouter getStudentRouter() {
        return studentRouter;
    }
}
