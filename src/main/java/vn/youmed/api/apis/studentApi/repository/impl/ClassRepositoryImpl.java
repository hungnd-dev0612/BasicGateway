package vn.youmed.api.apis.studentApi.repository.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.youmed.api.apis.entities.ClassEntity;
import vn.youmed.api.apis.studentApi.constants.SomeContants;
import vn.youmed.api.apis.studentApi.dto.ClassDTO;
import vn.youmed.api.apis.studentApi.repository.ClassRepository;

import java.util.ArrayList;
import java.util.List;

public class ClassRepositoryImpl implements ClassRepository {

    private final MongoClient client;
    private Logger LOGGER = LoggerFactory.getLogger(ClassRepositoryImpl.class);

    public ClassRepositoryImpl(MongoClient client) {
        this.client = client;
    }

    @Override
    public Future<List<ClassEntity>> findAll() {
        Future<List<ClassEntity>> listFuture = Future.future();
        client.find(SomeContants.CLASS_COLLECTION, new JsonObject(), ar -> {
            if (ar.succeeded()) {
                List<ClassEntity> list = new ArrayList<>();
                for (JsonObject json : ar.result()) {
                    ClassEntity entity = ClassDTO.parseJsonToEntity(json);

                    list.add(entity);
                }
                listFuture.complete(list);
            } else {
                listFuture.fail("failed repository");
            }
        });
        return listFuture;
    }

    @Override
    public Future<ClassEntity> findById(String id) {
        Future<ClassEntity> classEntityFuture = Future.future();
        JsonObject query = new JsonObject().put("_id", id);
        JsonObject field = new JsonObject();
        client.findOne(SomeContants.CLASS_COLLECTION, query, field, ar -> {
            if (ar.succeeded() && ar.result() != null) {
                ClassEntity entity = ClassDTO.parseJsonToEntity(ar.result());
                classEntityFuture.complete(entity);
            } else {
                classEntityFuture.fail("id not found");
            }
        });
        return classEntityFuture;

    }

    @Override
    public Future<ClassEntity> update(String id, ClassEntity entity) {
        Future<ClassEntity> classEntityFuture = Future.future();
        LOGGER.info("id {}", id);
        JsonObject convertToJson = JsonObject.mapFrom(entity);
        JsonObject update = new JsonObject().put("$set", convertToJson);
        JsonObject query = new JsonObject().put("_id", id);
        client.updateCollection(SomeContants.CLASS_COLLECTION, query, update, ar -> {
            if (ar.succeeded()) {
                if (ar.result().getDocMatched() != 0) {
                    LOGGER.info("FOUND  " + ar.result().getDocMatched() + "MATCHED");
                }
                LOGGER.info("ar {}", ar);
                classEntityFuture.complete(entity);
            } else {
                classEntityFuture.fail(ar.cause());
            }
        });
        return classEntityFuture;
    }

    @Override
    public Future<ClassEntity> insert(ClassEntity entity) {
        Future<ClassEntity> entityFuture = Future.future();
        entity.set_id(ObjectId.get().toHexString());
        JsonObject document = JsonObject.mapFrom(entity);
        client.insert(SomeContants.CLASS_COLLECTION, document, ar -> {
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
        client.removeDocument(SomeContants.CLASS_COLLECTION, removeId, AsyncResult::succeeded);
    }
}
