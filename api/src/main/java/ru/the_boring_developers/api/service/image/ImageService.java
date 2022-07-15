package ru.the_boring_developers.api.service.image;

import ru.the_boring_developers.common.entity.image.Image;

import java.util.List;
import java.util.Map;

public interface ImageService {

    Long create(Long userId, byte[] body);
    Image find(Long id);
    Map<Long, byte[]> find(Long userId, List<Long> ids);
}
