
package com.elh2ny.model.oneArticleResponse;


import com.elh2ny.model.articlesResponseModel.Datum;

public class OneArticleResponse {

    private String error;
    private Datum article;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Datum getArticle() {
        return article;
    }

    public void setArticle(Datum article) {
        this.article = article;
    }

}
