package vn.youmed.api.apis.studentApi.repository;

import io.vertx.core.Future;
import vn.youmed.api.apis.entities.StudentEntity;

import java.util.List;

public interface StudentRepository {

    Future<List<StudentEntity>> findAll();

    Future<StudentEntity> findById(String id);

    Future<StudentEntity> update(String id, StudentEntity entity);

    Future<StudentEntity> insert(StudentEntity entity);

    void delete(String id);

}
