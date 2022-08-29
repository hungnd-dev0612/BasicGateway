package vn.youmed.api.apis.studentApi.services;


import io.vertx.core.Future;
import vn.youmed.api.apis.studentApi.dto.StudentDTO;

import java.util.List;


public interface StudentService {

    Future<List<StudentDTO>> getAll();

    Future<StudentDTO> findById(String id);

    Future<StudentDTO> insert(StudentDTO dto);

    Future<StudentDTO> update(String id, StudentDTO dto);

    void delete(String id);
}
