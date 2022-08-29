package vn.youmed.api.apis.studentApi.repository;

import io.vertx.core.Future;
import vn.youmed.api.apis.entities.ClassEntity;

import java.util.List;

public interface ClassRepository {
    Future<List<ClassEntity>> findAll();

    Future<ClassEntity> findById(String id);

    Future<ClassEntity> update(String id, ClassEntity entity);

    Future<ClassEntity> insert(ClassEntity entity);

    void delete(String id);
}
