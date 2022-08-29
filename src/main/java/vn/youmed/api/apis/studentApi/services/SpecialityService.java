package vn.youmed.api.apis.studentApi.services;

import io.vertx.core.Future;
import vn.youmed.api.apis.studentApi.dto.SpecialityDTO;

import java.util.List;

public interface SpecialityService {
    Future<List<SpecialityDTO>> getAll();

    Future<SpecialityDTO> findById(String id);

    Future<SpecialityDTO> insert(SpecialityDTO dto);

    Future<SpecialityDTO> update(String id, SpecialityDTO dto);

    void delete(String id);
}
