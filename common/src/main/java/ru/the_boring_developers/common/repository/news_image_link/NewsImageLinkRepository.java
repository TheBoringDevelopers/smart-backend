package ru.the_boring_developers.common.repository.news_image_link;

import ru.the_boring_developers.common.entity.news.link.NewsImageLink;

public interface NewsImageLinkRepository {

    void create(NewsImageLink newsImageLink);

    Long find(Long newsId);
}
