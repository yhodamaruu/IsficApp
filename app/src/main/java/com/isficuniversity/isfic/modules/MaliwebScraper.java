package com.isficuniversity.isfic.modules;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MaliwebScraper {

    public static List<Article> getArticles() {
        String url = "https://malijet.com/";
        List<Article> articles = new ArrayList<>();

        try {
            Document document = Jsoup.connect(url).get();

            Elements cards = document.select("div.card.text-bg-dark.mb-3");

            for (Element card : cards) {
                String articleLink = card.select("a[href]").attr("href");

                if (!articleLink.startsWith("https://malijet.com/")) {
                    articleLink = "https://malijet.com/" + articleLink;
                }

                String imageLink = card.select("img").attr("src");

                 String title = card.select("h5.card-title a").text();

                 Article article = new Article();
                article.setTitle(title);
                article.setUrl(articleLink);
                article.setImage(imageLink);

                articles.add(article);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return articles;
    }
}
