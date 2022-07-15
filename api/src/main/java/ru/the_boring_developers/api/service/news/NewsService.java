package ru.the_boring_developers.api.service.news;

import ru.the_boring_developers.common.entity.news.dto.CreateNewsDto;
import ru.the_boring_developers.common.entity.news.dto.GetNewsDto;

import java.util.List;

public interface NewsService {
    Long create(CreateNewsDto createNewsDto);
    List<GetNewsDto> findAll(Long userId);
}
