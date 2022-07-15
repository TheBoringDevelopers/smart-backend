package ru.the_boring_developers.api.service.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.the_boring_developers.common.entity.image.Image;
import ru.the_boring_developers.common.entity.user.link.UserImageLink;
import ru.the_boring_developers.common.repository.image.ImageRepository;
import ru.the_boring_developers.common.repository.user_image_link.UserImageLinkRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final UserImageLinkRepository userImageLinkRepository;
    private final ImageRepository imageRepository;

    @Override
    public Long create(Long userId, byte[] body) {
        Image image = new Image();
        image.setContent(body);
        userImageLinkRepository.delete(userId);
        Long imageId = imageRepository.create(
                Image.builder()
                        .content(body)
                        .build()
        );
        userImageLinkRepository.create(
                UserImageLink.builder()
                        .imageId(imageId)
                        .userId(userId)
                        .build()
        );
        return imageId;
    }

    @Override
    public Image find(Long id) {
        return imageRepository.find(id);
    }

    @Override
    public Map<Long, byte[]> find(Long userId, List<Long> ids) {
        return imageRepository.find(ids);
    }
}
