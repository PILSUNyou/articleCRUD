package org.example.service;

import org.example.dao.ArticleDao;
import org.example.dto.Article;

import java.util.List;

public class ArticleService {
        ArticleDao articleDao = new ArticleDao();

    public int articleWirte(String title, String body) {
        return articleDao.articleWirte(title, body);
    }

    public List<Article> getArticles() {
        return new ArticleDao().getArticles();
    }
    public Article getAritcle(int numberId){
        return articleDao.getArticle(numberId);
    }

    public void modify(int id, String modifyTitle, String modifyBody) {
        new ArticleDao().modify(id, modifyTitle, modifyBody);
    }

    public void delete(int deleteArticle) {
        articleDao.delete(deleteArticle);
    }

    public Article getdetailArticle(int detailId) {
        return articleDao.getdetailArticle(detailId);
    }
}
