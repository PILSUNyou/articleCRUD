package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Article extends Dto{
    public String title;
    public String body;
    public int hit;

    public Article(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Article(Map<String, Object> row){
        super(row);
        this.title = (String) row.get("title");
        this.body = (String) row.get("body");
        this.hit = (int) row.get("hit");
    }


}