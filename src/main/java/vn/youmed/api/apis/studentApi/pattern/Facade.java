package vn.youmed.api.apis.studentApi.pattern;

import io.vertx.core.Vertx;
import io.vertx.ext.mongo.MongoClient;
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
    private final SpecialityRepository specialityRepository;
    private final SpecialityService specialityService;
    private final SpecialityHandler specialityHandler;
    private final ClassRepository classRepository;
    private final ClassService classService;
    private final ClassHandler classHandler;
    private final StudentRepository studentRepository;
    private final StudentService studentService;
    private final StudentHandler studentHandler;

    public Facade(Vertx vertx) {
        MongoClient client = MongoConnection.getConnection(vertx);
        specialityRepository = new SpecialityRepositoryImpl(client);
        specialityService = new SpecialityServiceImpl(specialityRepository);
        specialityHandler = new SpecialityHandler(specialityService);

        classRepository = new ClassRepositoryImpl(client);
        classService = new ClassServiceImpl(classRepository, specialityRepository);
        classHandler = new ClassHandler(classService);

        studentRepository = new StudentRepositoryImpl(client);
        studentService = new StudentServiceImpl(studentRepository);
        studentHandler = new StudentHandler(studentService);

        studentRouter = new StudentRouter(vertx, studentHandler, classHandler, specialityHandler);
    }

    public StudentRouter getStudentRouter() {
        return studentRouter;
    }

    public SpecialityRepository getSpecialityRepository() {
        return specialityRepository;
    }

    public SpecialityService getSpecialityService() {
        return specialityService;
    }

    public SpecialityHandler getSpecialityHandler() {
        return specialityHandler;
    }

    public ClassRepository getClassRepository() {
        return classRepository;
    }

    public ClassService getClassService() {
        return classService;
    }

    public ClassHandler getClassHandler() {
        return classHandler;
    }

    public StudentRepository getStudentRepository() {
        return studentRepository;
    }

    public StudentService getStudentService() {
        return studentService;
    }

    public StudentHandler getStudentHandler() {
        return studentHandler;
    }
}
