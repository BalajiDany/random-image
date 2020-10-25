package com.zeta.randomimage.entity;

import com.zeta.randomimage.model.ImageModel;

import lombok.Data;

@Data
public class ImageEntity {

	private Integer imageId;

	private String imageURL;

	public static ImageEntity fromModel(ImageModel imageModel) {
		ImageEntity imageEntity = new ImageEntity();

		imageEntity.imageId = imageModel.getImageId();
		imageEntity.imageURL = imageModel.getImageUrl();

		return imageEntity;
	}
}
