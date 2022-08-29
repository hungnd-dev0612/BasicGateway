package vn.youmed.api.apis.studentApi.repository.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.youmed.api.apis.entities.StudentEntity;
import vn.youmed.api.apis.studentApi.repository.StudentRepository;
import vn.youmed.api.apis.studentApi.constants.SomeContants;

import java.util.ArrayList;
import java.util.List;

public class StudentRepositoryImpl implements StudentRepository {
    private final MongoClient client;
    private final Logger LOGGER = LoggerFactory.getLogger(StudentRepositoryImpl.class);


    public StudentRepositoryImpl(MongoClient client) {
        this.client = client;

    }


    @Override
    public Future<List<StudentEntity>> findAll() {
        Future<List<StudentEntity>> listFuture = Future.future();
        JsonObject query = new JsonObject();
        client.find(SomeContants.STUDENT_COLLECTION, query, resultHandler -> {
            if (resultHandler.succeeded()) {
                List<StudentEntity> listEntity = new ArrayList<>();
                for (JsonObject jsonObject : resultHandler.result()) {
                    StudentEntity studentEntity = jsonObject.mapTo(StudentEntity.class);
                    LOGGER.error("student {}", studentEntity);
                    listEntity.add(studentEntity);
                }
                listFuture.complete(listEntity);
            } else {
                listFuture.fail("get all student failed");
            }
        });
        return listFuture;
    }

    @Override
    public Future<StudentEntity> findById(String id) {
        Future<StudentEntity> studentEntityFuture = Future.future();
        JsonObject query = new JsonObject().put("_id", id);
        JsonObject field = new JsonObject();
        client.findOne(SomeContants.STUDENT_COLLECTION, query, field, ar -> {
            if (ar.succeeded() && ar.result() != null) {
                StudentEntity entity = ar.result().mapTo(StudentEntity.class);
                studentEntityFuture.complete(entity);
            } else {
                studentEntityFuture.fail("id not found");
            }
        });
        return studentEntityFuture;
    }

    @Override
    public Future<StudentEntity> update(String id, StudentEntity entity) {
        Future<StudentEntity> studentEntityFuture = Future.future();
        JsonObject parseToJson = JsonObject.mapFrom(entity);
        JsonObject update = new JsonObject().put("$set", parseToJson);
        JsonObject query = new JsonObject().put("_id", id);
        client.updateCollection(SomeContants.STUDENT_COLLECTION, query, update, ar -> {
            if (ar.succeeded()) {
                LOGGER.info("Doc upserted {}", ar.result().getDocUpsertedId());
                studentEntityFuture.complete(entity);
            } else {
                studentEntityFuture.fail("repo fail");
            }
        });
        return studentEntityFuture;
    }

    @Override
    public Future<StudentEntity> insert(StudentEntity entity) {
        entity.set_id(ObjectId.get().toHexString());
        Future<StudentEntity> entityFuture = Future.future();
        JsonObject document = JsonObject.mapFrom(entity);
        client.insert(SomeContants.STUDENT_COLLECTION, document, ar -> {
            if (ar.succeeded()) {
                entityFuture.complete(entity);
            } else {
                entityFuture.fail(ar.cause());
            }
        });
        return entityFuture;
    }

    @Override
    public void delete(String id) {
        JsonObject removeId = new JsonObject().put("_id", id);
        client.removeDocument(SomeContants.STUDENT_COLLECTION, removeId, AsyncResult::succeeded);
    }

}
