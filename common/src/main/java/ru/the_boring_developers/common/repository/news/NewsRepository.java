package ru.the_boring_developers.common.repository.news;


import ru.the_boring_developers.common.entity.news.News;
import ru.the_boring_developers.common.repository.Repository;

import java.util.List;

public interface NewsRepository extends Repository {
    List<News> findAll();

    Long create(News news);
}
