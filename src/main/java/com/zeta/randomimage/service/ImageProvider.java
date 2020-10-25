package com.zeta.randomimage.service;

import java.util.List;

import com.zeta.randomimage.entity.ImageEntity;

public interface ImageProvider {

	byte[] getImageById(int id);

	List<ImageEntity> getAllImages();
}
