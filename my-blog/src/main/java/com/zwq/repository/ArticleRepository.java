package com.zwq.repository;

import com.zwq.pojo.Article;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article,String>, JpaSpecificationExecutor<Article> {
}
