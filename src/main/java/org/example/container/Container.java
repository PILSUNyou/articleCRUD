package org.example.container;

import org.example.dao.ArticleDao;
import org.example.db.DBConnection;
import org.example.service.ArticleService;

import java.util.Scanner;

public class Container {
    public static Scanner sc;
    public static DBConnection dbConnection;
    public static ArticleDao articleDao;
    public static ArticleService articleService;

    static {
        articleDao = new ArticleDao();
        articleService = new ArticleService();
    }

    public static DBConnection getDBConnection() {
        if ( dbConnection == null ) {
            dbConnection = new DBConnection();
        }

        return dbConnection;
    }
}
