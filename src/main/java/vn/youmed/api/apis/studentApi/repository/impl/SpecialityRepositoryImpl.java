package vn.youmed.api.apis.studentApi.repository.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.youmed.api.apis.entities.SpecialityEntity;
import vn.youmed.api.apis.studentApi.constants.SomeContants;
import vn.youmed.api.apis.studentApi.repository.SpecialityRepository;

import java.util.ArrayList;
import java.util.List;

public class SpecialityRepositoryImpl implements SpecialityRepository {

    private final MongoClient client;
    private final Logger LOGGER = LoggerFactory.getLogger(SpecialityRepositoryImpl.class);

    public SpecialityRepositoryImpl(MongoClient client) {
        this.client = client;
    }

    @Override
    public Future<List<SpecialityEntity>> findAll() {
        Future<List<SpecialityEntity>> listFuture = Future.future();
        client.find(SomeContants.SPECIALITY_COLLECTION, new JsonObject(), ar -> {
            if (ar.succeeded()) {
                List<SpecialityEntity> list = new ArrayList<>();
                for (JsonObject json : ar.result()) {
                    SpecialityEntity entity = json.mapTo(SpecialityEntity.class);
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
    public Future<SpecialityEntity> findById(String id) {
        Future<SpecialityEntity> specialityEntityFuture = Future.future();
        JsonObject query = new JsonObject().put("_id", id);
        JsonObject field = new JsonObject();
        client.findOne(SomeContants.SPECIALITY_COLLECTION, query, field, ar -> {
            if (ar.succeeded() && ar.result() != null) {
                SpecialityEntity entity = ar.result().mapTo(SpecialityEntity.class);
                LOGGER.info("{}", ar.result());
                specialityEntityFuture.complete(entity);
            } else {
                LOGGER.error("can't find {}", new Throwable());
                specialityEntityFuture.fail("speciality id not found");
            }
        });
        return specialityEntityFuture;
    }

    @Override
    public Future<SpecialityEntity> update(String id, SpecialityEntity entity) {
        Future<SpecialityEntity> specialityEntityFuture = Future.future();
        JsonObject parseToJson = JsonObject.mapFrom(entity);
        JsonObject update = new JsonObject().put("$set", parseToJson);
        JsonObject query = new JsonObject().put("_id", id);
        client.updateCollection(SomeContants.SPECIALITY_COLLECTION, query, update, ar -> {
            if (ar.succeeded()) {
                LOGGER.info("Doc upserted {}", ar.result().getDocUpsertedId());
                specialityEntityFuture.complete(entity);
            } else {
                specialityEntityFuture.fail("repo fail");
            }
        });
        return specialityEntityFuture;
    }

    @Override
    public Future<SpecialityEntity> insert(SpecialityEntity entity) {
        entity.set_id(ObjectId.get().toHexString());
        Future<SpecialityEntity> entityFuture = Future.future();
        JsonObject document = JsonObject.mapFrom(entity);
        client.save(SomeContants.SPECIALITY_COLLECTION, document, ar -> {
            if (ar.succeeded()) {
                entityFuture.complete(entity);
            } else {
                entityFuture.fail(ar.cause().getMessage());
            }
        });

        return entityFuture;
    }

    @Override
    public void delete(String id) {
        JsonObject removeId = new JsonObject().put("_id", id);
        client.removeDocument(SomeContants.SPECIALITY_COLLECTION, removeId, AsyncResult::succeeded);
    }

    @Override
    public Future<Boolean> checkIfNameIsExist(String name) {
//        Future<Boolean> check = Future.future();
//        JsonObject queryByName = new JsonObject().put("name", name);
//        JsonObject field = new JsonObject();
//        client.findOne(SomeContants.SPECIALITY_COLLECTION, queryByName, field, ar -> {
//            if (ar.succeeded() && ar.result() != null) {
//                LOGGER.info("ar {}", ar);
//                check.fail("Name is already exist");
//            } else {
//                LOGGER.error("error ", ar.cause());
//                check.complete(true);
//            }
//        });
//        return check;
        return null;
    }
}
