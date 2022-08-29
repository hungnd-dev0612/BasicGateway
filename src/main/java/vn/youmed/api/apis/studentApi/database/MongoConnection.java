package vn.youmed.api.apis.studentApi.database;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class MongoConnection {
    public static MongoClient getConnection(Vertx vertx) {
        JsonObject config = new JsonObject()
                .put("connection_string", "mongodb://localhost:27017")
                .put("db_name", "local");
        return MongoClient.createShared(vertx, config);
    }
}
