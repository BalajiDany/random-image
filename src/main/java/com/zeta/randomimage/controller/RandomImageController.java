package com.zeta.randomimage.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zeta.randomimage.entity.ImageEntity;
import com.zeta.randomimage.service.ImageProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RandomImageController {

	private final ImageProvider imageProvider;

	@GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] randomImage(@PathVariable Optional<Integer> id) {
		return imageProvider.getImageById(id.get());
	}

	@GetMapping("/images")
	@ResponseBody
	public List<ImageEntity> getAllImages() {
		return imageProvider.getAllImages();
	}

}
