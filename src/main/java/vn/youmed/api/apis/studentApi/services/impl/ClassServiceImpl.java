package vn.youmed.api.apis.studentApi.services.impl;

import io.vertx.core.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.youmed.api.apis.entities.ClassEntity;
import vn.youmed.api.apis.studentApi.dto.ClassDTO;
import vn.youmed.api.apis.studentApi.repository.ClassRepository;
import vn.youmed.api.apis.studentApi.repository.SpecialityRepository;
import vn.youmed.api.apis.studentApi.services.ClassService;

import java.util.ArrayList;
import java.util.List;

public class ClassServiceImpl implements ClassService {
    private final ClassRepository repository;
    private final SpecialityRepository specRepo;
    private final Logger LOGGER = LoggerFactory.getLogger(ClassServiceImpl.class);

    public ClassServiceImpl(ClassRepository repository, SpecialityRepository specRepo) {
        this.repository = repository;
        this.specRepo = specRepo;
    }

    @Override
    public Future<List<ClassDTO>> getAll() {
        Future<List<ClassDTO>> listFuture = Future.future();
        repository.findAll().setHandler(listAr -> {
            if (listAr.succeeded()) {
                List<ClassDTO> list = new ArrayList<>();
                for (ClassEntity entity : listAr.result()) {
                    ClassDTO dto = ClassDTO.convertToDTO(entity);
                    list.add(dto);
                }

                listFuture.complete(list);
            } else {
                listFuture.fail("failed");
            }
        });
        return listFuture;
    }

    @Override
    public Future<ClassDTO> findById(String id) {
        Future<ClassDTO> dtoFuture = Future.future();
        repository.findById(id).setHandler(ar -> {
            if (ar.succeeded()) {
                ClassDTO dto = ClassDTO.convertToDTO(ar.result());
                dtoFuture.complete(dto);
            } else {
                dtoFuture.fail("service fail");
            }
        });
        return dtoFuture;
    }

    @Override
    public Future<ClassDTO> insert(ClassDTO dto, String specialityId) {
        Future<ClassDTO> dtoFuture = Future.future();
        ClassEntity entity = ClassDTO.convertToEntity(dto);
        specRepo.findById(specialityId).setHandler(result -> {
            if (result.succeeded()) {
                repository.insert(entity).setHandler(ar -> {
                    if (ar.succeeded()) {
                        ClassDTO parseToDto = ClassDTO.convertToDTO(ar.result());
                        dtoFuture.complete(parseToDto);
                    } else {
                        dtoFuture.fail(ar.cause());
                    }
                });
            } else {
                dtoFuture.fail(result.cause());
            }
        });

        return dtoFuture;
    }

    @Override
    public Future<ClassDTO> update(String id, ClassDTO dto) {
        Future<ClassDTO> dtoFuture = Future.future();
        dto.setId(id);
        ClassEntity entity = ClassDTO.convertToEntity(dto);
        repository.update(id, entity).setHandler(ar -> {
            if (ar.succeeded()) {
                ClassDTO parseToDto = ClassDTO.convertToDTO(ar.result());
                dtoFuture.complete(parseToDto);
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
