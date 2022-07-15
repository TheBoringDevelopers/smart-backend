package ru.the_boring_developers.api.service.news;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.the_boring_developers.common.entity.image.Image;
import ru.the_boring_developers.common.entity.news.News;
import ru.the_boring_developers.common.entity.news.dto.CreateNewsDto;
import ru.the_boring_developers.common.entity.news.dto.GetNewsDto;
import ru.the_boring_developers.common.entity.news.link.NewsImageLink;
import ru.the_boring_developers.common.repository.news.NewsRepository;
import ru.the_boring_developers.common.repository.news_image_link.NewsImageLinkRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final NewsImageLinkRepository newsImageLinkRepository;

    @Override
    public Long create(CreateNewsDto createNewsDto) {
        Long newsId = newsRepository.create(createNewsDto.getNews());
        newsImageLinkRepository.create(NewsImageLink.builder()
                .newsId(newsId)
                .imageId(createNewsDto.getImageId())
                .build()
        );
        return newsId;
    }

    @Override
    public List<GetNewsDto> findAll(Long userId) {
        List<News> news = newsRepository.findAll();
        return news.stream()
                .map(it -> GetNewsDto.builder()
                        .news(it)
                        .image(Image.builder()
                                .id(newsImageLinkRepository.find(it.getId()))
                                .build())
                        .build())
                .collect(Collectors.toList());
    }
}
