package vn.youmed.api.apis.studentApi.services;


import io.vertx.core.Future;
import vn.youmed.api.apis.studentApi.dto.ClassDTO;

import java.util.List;

public interface ClassService {
    Future<List<ClassDTO>> getAll();

    Future<ClassDTO> findById(String id);

    Future<ClassDTO> insert(ClassDTO dto, String specialityId);

    Future<ClassDTO> update(String id, ClassDTO dto);

    void delete(String id);


//    Future<List<ClassDTOv2>> getAll2();
}
