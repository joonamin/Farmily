package com.ssafy.farmily.service.file;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.farmily.dto.ImageDto;
import com.ssafy.farmily.entity.Image;
import com.ssafy.farmily.repository.ImageRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractFileService implements FileService {

	private final ImageRepository imageRepository;

	@Override
	@Transactional
	public Image saveImage(MultipartFile file) {
		ImageDto dto = uploadImage(file);
		Image entity = Image.builder()
			.location(dto.getLocation())
			.originalFileName(dto.getOriginalFileName())
			.build();
		return imageRepository.save(entity);
	}

	@Override
	@Transactional
	public List<Image> saveImages(Collection<MultipartFile> files) {
		List<ImageDto> dtos = uploadImages(files);
		List<Image> entities = dtos.stream()
			.map(dto -> Image.builder()
				.location(dto.getLocation())
				.originalFileName(dto.getOriginalFileName())
				.build()
			).toList();
		return imageRepository.saveAll(entities);
	}

	@Override
	public Image connectImage(URI uri) {
		return connectImage(uri.toString());
	}

	@Override
	public Image connectImage(String uri) {
		// 외부 이미지를 DB에 연결, OAuth 사용자 프로필 이미지 등록 등에 사용
		throw new RuntimeException("아직 구현되지 않음");
	}


	protected abstract ImageDto uploadImage(MultipartFile file);

	protected List<ImageDto> uploadImages(Collection<MultipartFile> files) {
		return files.stream().map(this::uploadImage).toList();
	}
}
