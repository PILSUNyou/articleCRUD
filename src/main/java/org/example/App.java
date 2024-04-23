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
            for (int i = 0; i < 2; i++) {
                System.out.println("");
                System.out.flush();
            }
            System.out.println("게시글 작성 → create");
            System.out.println("게시글 읽기 → read");
            System.out.println("게시글 수정 → update");
            System.out.println("게시글 삭제 → delete");
            System.out.println("종료하기 → exit");
            System.out.print("명령어를 입력해 주세요 : ");
            String cmd = sc.nextLine();
            for (int i = 0; i < 4; i++) {
                System.out.println("");
                System.out.flush();
            }
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
                        System.out.printf("%d번 글이 작성되었습니다.\n", newArticle);
                        continue;
                    case "read":
                        List<Article> forPrintArticles = articleService.getArticles();

                        if (forPrintArticles.size() == 0){
                            System.out.println("현재 등록되어 있는 게시글이 없습니다.");
                            continue;
                        }

                        System.out.println("순번 |       게시 날짜       |   제목");
                        for (int i = forPrintArticles.size() - 1; i >= 0 ; i-- ) {
                            Article articles = forPrintArticles.get(i);

                            System.out.printf("%4d |  %5s  |  %s\n", articles.id, articles.regDate, articles.title);
                        }

                        while (true) {
                            System.out.println("어떤 게시물을 확인하겠습니까 ? (게시물 번호 입력)");
                            System.out.println("나가시려면 \' 0 \'를 입력해주세요.");
                            System.out.print("입력 : ");
                            int detailId = sc.nextInt();
                            sc.nextLine();

                            if (detailId == 0) {
                                break;
                            }
                            Article detailIdArticle = articleService.getAritcle(detailId);

                            if (detailIdArticle == null) {
                                System.out.printf("%s번 게시물은 존재하지 않습니다.\n", detailId);
                                continue;
                            }

                            Article detailArticle = articleService.getdetailArticle(detailId);

                            System.out.println(" 제목  |  내용");
                            System.out.printf(" %s  |  %s\n", detailArticle.title, detailArticle.body);
                            break;
                        }
                        continue;
                    case "update":
                        System.out.println("수정할 게시물의 번호를 입력해 주세요.");
                        System.out.print("입력 : ");
                        int modifyId = sc.nextInt();
                        sc.nextLine();

                        if (modifyId == 0){
                            System.out.println("게시물이 존재하지 않습니다.");
                            continue;
                        }

                        Article modifyArticle = articleService.getAritcle(modifyId);
                        if (modifyArticle == null) {
                            System.out.printf("%s번 게시물은 존재하지 않습니다.\n", modifyId);
                            continue;
                        }

                        System.out.printf("제목 : ");
                        String modifyTitle = sc.nextLine();
                        System.out.printf("내용 : ");
                        String modifyBody = sc.nextLine();

                        articleService.modify(modifyArticle.id, modifyTitle, modifyBody);
                        System.out.printf("%s번 게시물이 삭제되었습니다.\n", modifyId);
                        continue;
                    case "delete":
                        System.out.println("삭제할 게시물의 번호를 입력해주세요 !");
                        System.out.print("입력 : ");
                        int deleteId = sc.nextInt();
                        sc.nextLine();

                        if (deleteId == 0){
                            System.out.println("게시물이 존재하지 않습니다.");
                            continue;
                        }

                        Article deleteArticle = articleService.getAritcle(deleteId);

                        if (deleteArticle == null){
                            System.out.printf("%s번 게시물은 존재하지 않습니다.\n", deleteId);
                            continue;
                        }

                        articleService.delete(deleteArticle.id);

                        System.out.printf("%s번 게시물이 삭제되었습니다.\n", deleteId);
                }
            }
            else {
                System.out.println("잘못된 명령어 입니다.");
            }
        }
    }
}