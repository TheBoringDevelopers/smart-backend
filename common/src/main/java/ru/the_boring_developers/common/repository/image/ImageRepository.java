package ru.the_boring_developers.common.repository.image;

import ru.the_boring_developers.common.entity.image.Image;

import java.util.List;
import java.util.Map;

public interface ImageRepository {

    Long create(Image image);

    Image findByUserId(Long userId);
    Image find(Long id);
    Map<Long, byte[]> find(List<Long> ids);
    Image findBySportTypeId(Long sportTypeId);
}
