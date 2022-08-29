package vn.youmed.api.apis.studentApi.repository;

import io.vertx.core.Future;
import vn.youmed.api.apis.entities.SpecialityEntity;

import java.util.List;

public interface SpecialityRepository {
    Future<List<SpecialityEntity>> findAll();

    Future<SpecialityEntity> findById(String id);

    Future<SpecialityEntity> update(String id, SpecialityEntity entity);

    Future<SpecialityEntity> insert(SpecialityEntity entity);

    void delete(String id);

    Future<Boolean> checkIfNameIsExist(String name);
}
