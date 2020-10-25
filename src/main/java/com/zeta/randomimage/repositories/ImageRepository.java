package com.zeta.randomimage.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.zeta.randomimage.model.ImageModel;

public interface ImageRepository extends CrudRepository<ImageModel, Long> {

	Optional<ImageModel> findByImageId(Integer id);

}
