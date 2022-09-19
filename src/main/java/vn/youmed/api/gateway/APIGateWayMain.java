package vn.youmed.api.gateway;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.youmed.api.apis.StudentVerticle1;
import vn.youmed.api.apis.StudentVerticle2;
import vn.youmed.api.apis.StudentVerticle3;

public class APIGateWayMain {
    public static Logger LOGGER = LoggerFactory.getLogger(APIGateWayMain.class);

    public static void main(String[] args) {
        ClusterManager mgr = new HazelcastClusterManager();
        VertxOptions options = new VertxOptions().setClusterManager(mgr);
        Vertx.clusteredVertx(options, cluster -> {
            if (cluster.succeeded()) {
                cluster.result().deployVerticle(new APIGatewayVerticle(), res -> {
                    if (res.succeeded()) {
                        LOGGER.info("deployment id is : {}", res.result());
                        LOGGER.warn("you can use this : {} id to undeploy verticle", res.result());
                    } else {
                        LOGGER.error("deployment failed : ", res.cause());
                    }
                });
            } else {
                LOGGER.error("Cluster fail: {}", cluster.cause());
            }

        });

    }
}
