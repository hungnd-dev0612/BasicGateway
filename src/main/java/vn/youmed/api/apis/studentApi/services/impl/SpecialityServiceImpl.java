package vn.youmed.api.apis.studentApi.services.impl;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import vn.youmed.api.apis.entities.SpecialityEntity;
import vn.youmed.api.apis.studentApi.dto.SpecialityDTO;
import vn.youmed.api.apis.studentApi.repository.SpecialityRepository;
import vn.youmed.api.apis.studentApi.services.SpecialityService;
import java.util.ArrayList;
import java.util.List;

public class SpecialityServiceImpl implements SpecialityService {

    private final SpecialityRepository repository;

    public SpecialityServiceImpl(SpecialityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Future<List<SpecialityDTO>> getAll() {
        Future<List<SpecialityDTO>> listFuture = Future.future();
        repository.findAll().setHandler(ar -> {
            if (ar.succeeded()) {
                List<SpecialityDTO> listStudent = new ArrayList<>();
                for (SpecialityEntity entity : ar.result()) {
                    JsonObject json = JsonObject.mapFrom(entity);
                    SpecialityDTO dto = json.mapTo(SpecialityDTO.class);
                    listStudent.add(dto);
                }
                listFuture.complete(listStudent);
            } else {
                listFuture.fail("get all service failed");
            }
        });

        return listFuture;
    }

    @Override
    public Future<SpecialityDTO> findById(String id) {
        Future<SpecialityDTO> specialityDTOFuture = Future.future();
        repository.findById(id).setHandler(ar -> {
            if (ar.succeeded()) {
                SpecialityDTO dto = JsonObject.mapFrom(ar.result()).mapTo(SpecialityDTO.class);
                specialityDTOFuture.complete(dto);
            } else {
                specialityDTOFuture.fail(ar.cause());
            }
        });
        return specialityDTOFuture;
    }

    @Override
    public Future<SpecialityDTO> insert(SpecialityDTO dto) {
        Future<SpecialityDTO> dtoFuture = Future.future();
        SpecialityEntity entity = SpecialityDTO.convertToEntity(dto);
        repository.insert(entity).setHandler(ar -> {
            if (ar.succeeded()) {
                SpecialityDTO parseToDto = SpecialityDTO.convertToDTO(ar.result());
                dtoFuture.complete(parseToDto);
            } else {
                dtoFuture.fail(ar.cause());
            }
        });
        return dtoFuture;
    }

    @Override
    public Future<SpecialityDTO> update(String id, SpecialityDTO dto) {
        Future<SpecialityDTO> dtoFuture = Future.future();
        dto.setId(id);
        SpecialityEntity entity = JsonObject.mapFrom(dto).mapTo(SpecialityEntity.class);
        repository.update(id, entity).setHandler(ar -> {
            if (ar.succeeded()) {
                SpecialityDTO dto1 = JsonObject.mapFrom(ar.result()).mapTo(SpecialityDTO.class);
                dtoFuture.complete(dto1);
            } else {
                dtoFuture.fail(ar.cause());
            }
        });
        return dtoFuture;
    }

    @Override
    public void delete(String id) {
        repository.delete(id);
    }
}
