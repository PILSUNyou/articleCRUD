package org.example.dao;

import org.example.container.Container;
import org.example.db.DBConnection;
import org.example.dto.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleDao {
    private DBConnection dbConnection;

    public ArticleDao(){
        dbConnection = Container.getDBConnection();
    }

    public int articleWirte(String title, String body) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("INSERT INTO article "));
        sb.append(String.format("SET regDate = NOW(), "));
        sb.append(String.format("title = '%s', ", title));
        sb.append(String.format("`body` = '%s' ", body));

        return dbConnection.insert(sb.toString());
    }

    public List<Article> getArticles() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("SELECT * "));
        sb.append(String.format("FROM Article "));
        sb.append(String.format("ORDER BY id DESC "));

        List<Article> articles = new ArrayList<>();
        List<Map<String, Object>> rows = dbConnection.selectRows(sb.toString());

        for (Map<String, Object> row : rows){
            articles.add(new Article((row)));
        }

        return articles;
    }

    public List<Article> getArticle(int numberId) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("SELECT * "));
        sb.append(String.format("FROM article "));
        sb.append(String.format("WHERE id = %d ", numberId));

        Map<String, Object> row = dbConnection.selectRow(sb.toString());

        if (row.isEmpty()) {
            return null;
        }

        return (List<Article>) new Article(row);
    }

    public int modify(int id, String modifyTitle, String modifyBody) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("UPDATE article "));
        sb.append(String.format("SET updateDate = NOW(), "));
        sb.append(String.format("title = '%s', ",modifyTitle));
        sb.append(String.format("body = '%s' ", modifyBody));
        sb.append(String.format("WHERE id = %d" ,id));

        return dbConnection.update(sb.toString());
    }

    public int delete(int deleteArticle) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("DELETE FROM article "));
        sb.append(String.format("WHERE id = %d ", deleteArticle));

        return dbConnection.delete((sb.toString()));
    }
}
