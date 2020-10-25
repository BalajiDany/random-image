package com.zeta.randomimage.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.zeta.randomimage.entity.ImageEntity;
import com.zeta.randomimage.model.ImageModel;
import com.zeta.randomimage.repositories.ImageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageProviderImpl implements ImageProvider {

	private static final String IMAGE_URL = "https://picsum.photos/300/300";

	private final ImageRepository imageRepository;

	@Override
	public byte[] getImageById(int id) {
		URL imageURL = this.getPersistedImageURL(id);
		return getImageFromURL(imageURL);
	}

	@Override
	public List<ImageEntity> getAllImages() {
		Iterable<ImageModel> images = imageRepository.findAll();
		return StreamSupport.stream(images.spliterator(), false)
				.map(imageModel -> ImageEntity.fromModel(imageModel))
				.collect(Collectors.toList());
	}

	private URL getPersistedImageURL(int id) {
		Optional<ImageModel> optImageModel = imageRepository.findByImageId(id);

		if (optImageModel.isPresent()) {
			ImageModel imageModel = optImageModel.get();
			String url = imageModel.getImageUrl();
			try {
				return new URL(url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		URL url = this.getRandomPicURL();
		this.persistURLwithId(id, url.toString());
		return url;
	}

	private void persistURLwithId(int id, String url) {
		ImageModel imageModel = new ImageModel();
		imageModel.setImageId(id);
		imageModel.setImageUrl(url);
		log.info("Persist Id: {} URL: {}", id, url);
		imageRepository.save(imageModel);
	}

	private byte[] getImageFromURL(URL imageUrl) {
		byte[] imageBytes = {};
		try {
			BufferedImage image = ImageIO.read(imageUrl);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			ImageIO.write(image, "jpg", outputStream);
			imageBytes = outputStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return imageBytes;
	}

	private URL getRandomPicURL() {
		try {
			return this.getFinalURL(new URL(IMAGE_URL));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public URL getFinalURL(URL url) {
		try {
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setInstanceFollowRedirects(false);
			con.connect();
			int resCode = con.getResponseCode();
			if (resCode == HttpURLConnection.HTTP_SEE_OTHER || resCode == HttpURLConnection.HTTP_MOVED_PERM
					|| resCode == HttpURLConnection.HTTP_MOVED_TEMP) {
				String Location = con.getHeaderField("Location");
				if (Location.startsWith("/")) {
					Location = url.getProtocol() + "://" + url.getHost() + Location;
				}
				return getFinalURL(new URL(Location));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return url;
	}
}
