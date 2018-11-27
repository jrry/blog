package com.github.jrry.blog.service;

import com.github.jrry.blog.entity.ImageEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.jrry.blog.common.errors.NotFoundException;
import com.github.jrry.blog.forms.ImageForm;
import com.github.jrry.blog.repository.ImageRepository;
import com.github.jrry.blog.utils.ValidationUtils;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final UserService userService;
    private final ModelMapper mapper;

    @Override
    public Page<ImageEntity> getImages(int page) {
        ValidationUtils.notFoundWhenNegativePage(page);
        Page<ImageEntity> imagePage = imageRepository.findAllByOrderByCreatedDesc(PageRequest.of(page, 12));
        ValidationUtils.notFoundWhenPageIsEmpty(imagePage);
        return imagePage;
    }

    @Override
    public ImageEntity getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Optional<ImageEntity> maybeImageById(Long id) {
        if (Objects.isNull(id))
            return Optional.empty();
        return imageRepository.findById(id);
    }

    @Override
    @Transactional
    public ImageForm getImageFormById(Long id) {
        return mapper.map(getImageById(id), ImageForm.class);
    }

    @Override
    @Transactional
    public void updateImage(ImageForm imageForm) {
        ImageEntity imageEntity = getImageById(imageForm.getId());
        mapper.map(imageForm, imageEntity);
        imageEntity.setOwner(userService.getAuthenticatedUser());
        imageRepository.save(imageEntity);
    }

    @Override
    @Transactional
    public void saveImage(ImageForm imageForm) {
        ImageEntity imageEntity = mapper.map(imageForm, ImageEntity.class);
        imageEntity.setOwner(userService.getAuthenticatedUser());
        imageRepository.save(imageEntity);
    }

    @Override
    @Transactional
    public ImageEntity createImage(ImageForm imageForm) {
        ImageEntity imageEntity = mapper.map(imageForm, ImageEntity.class);
        //FIXME: dwa zapytania o to samo (Article save)
        imageEntity.setOwner(userService.getAuthenticatedUser());
        return imageRepository.save(imageEntity);
    }

}
