package org.example;

import org.example.container.Container;
import org.example.db.DBConnection;
import org.example.dto.Article;
import org.example.service.ArticleService;

import java.util.List;
import java.util.Scanner;

public class App {
    public App(){
        DBConnection.DB_NAME = "articleCRUD";
        DBConnection.DB_USER = "sbsst";
        DBConnection.DB_PASSWORD = "sbs123414";
        DBConnection.DB_PORT = 3306;

        Container.getDBConnection().connect();
    }
    public void start() {
        Scanner sc = new Scanner(System.in);

        ArticleService articleService = new ArticleService();

        while(true){
            System.out.println("게시글 작성 → create");
            System.out.println("게시글 읽기 → read");
            System.out.println("게시글 수정 → update");
            System.out.println("게시글 삭제 → delete");
            System.out.print("명령어를 입력해 주세요 : ");
            String cmd = sc.nextLine();

            if (cmd.equals("exit")){
                System.out.println("프로그램을 종료합니다.");
                break;
            }

            else if(cmd.equals("create") || cmd.equals("read") || cmd.equals("update") || cmd.equals("delete")) {
                switch (cmd) {
                    case "create":
                        System.out.println("게시물 제목을 입력해 주세요 !");
                        System.out.print("제목 입력 : ");
                        String title = sc.nextLine();

                        System.out.println("게시물의 내용을 입력해 주세요 !");
                        System.out.print("내용 입력 : ");
                        String body = sc.nextLine();

                        int newArticle = articleService.articleWirte(title, body);
                        System.out.printf("%d번 글이 작성되었습니다.", newArticle);

                    case "read":
                        List<Article> forPrintArticles = articleService.getArticles();

                        if (forPrintArticles.size() == 0){
                            System.out.println("현재 등록되어 있는 게시글이 없습니다.");
                            return;
                        }

                        System.out.println("순번 |       게시 날짜       | 조회 |  제목  |  내용  ");
                        for (int i = forPrintArticles.size() - 1; i >= 0 ; i-- ) {
                            Article articles = forPrintArticles.get(i);

                            System.out.printf("%4d | %5s | %d | %s | %s\n", articles.id, articles.regDate, articles.hit, articles.title,articles.body);
                        }

                    case "update":
                        System.out.println("수정할 게시물의 번호를 입력해 주세요.");
                        System.out.print("입력 : ");
                        int modifyId = sc.nextInt();

                        if (modifyId == 0){
                            System.out.println("게시물이 존재하지 않습니다.");
                            return;
                        }

                        Article modifyArticle = (Article) articleService.getAritcle(modifyId);
                        if (modifyArticle == null) {
                            System.out.printf("%s번 게시물은 존재하지 않습니다.\n", modifyId);
                            return;
                        }

                        System.out.printf("제목 : ");
                        String modifyTitle = sc.nextLine();
                        System.out.printf("내용 : ");
                        String modifyBody = sc.nextLine();

                        articleService.modify(modifyArticle.id, modifyTitle, modifyBody);

                    case "delete":
                        System.out.println("삭제할 게시물을 입력해주세요 !");
                        System.out.print("입력 : ");
                        int deleteId = sc.nextInt();

                        if (deleteId == 0){
                            System.out.println("게시물이 존재하지 않습니다.");
                            return;
                        }

                        Article deleteArticle = (Article) articleService.getAritcle(deleteId);

                        if (deleteArticle == null){
                            System.out.printf("%s번 게시물은 존재하지 않습니다.\n", deleteId);
                            return;
                        }

                        articleService.delete(deleteArticle.id);

                        System.out.printf("%s번 게시물이 삭제되었습니다.\n", deleteArticle.id);
                }
            }
            else {
                System.out.println("잘못된 명령어 입니다.");
            }
        }
    }
}
