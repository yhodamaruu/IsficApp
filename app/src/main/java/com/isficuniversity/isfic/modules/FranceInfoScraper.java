package com.isficuniversity.isfic.modules;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FranceInfoScraper {

    public static List<Article> getArticles() {
        String url = "https://www.francetvinfo.fr/";
        List<Article> articles = new ArrayList<>();

        try {
            Document document = Jsoup.connect(url).get();

            // Sélectionner les articles majeurs
            Elements cards = document.select("article.card-article-majeure");

            for (Element card : cards) {
                // Extraire le lien de l'article
                String articleLink = card.select("a.card-article-majeure__link").attr("href");

                // Compléter l'URL si nécessaire
                if (!articleLink.startsWith("https://")) {
                    articleLink = "https://www.francetvinfo.fr" + articleLink;
                }

                // Extraire le lien de l'image
                String imageLink = card.select("img.card-article-majeure__img").attr("src");

                // Extraire le titre de l'article
                String title = card.select("h2.card-article-majeure__title").text();

                // Créer l'objet Article
                Article article = new Article();
                article.setTitle(title);
                article.setUrl(articleLink);
                article.setImage(imageLink);

                // Ajouter l'article à la liste
                articles.add(article);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return articles;
    }
}
