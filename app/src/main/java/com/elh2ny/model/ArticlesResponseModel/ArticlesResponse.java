
package com.elh2ny.model.ArticlesResponseModel;


public class ArticlesResponse {

    private String error;
    private Articles articles;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Articles getArticles() {
        return articles;
    }

    public void setArticles(Articles articles) {
        this.articles = articles;
    }

}
