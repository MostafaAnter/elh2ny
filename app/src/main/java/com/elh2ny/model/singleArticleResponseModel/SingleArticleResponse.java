
package com.elh2ny.model.singleArticleResponseModel;


import com.elh2ny.model.articlesResponseModel.Datum;

public class SingleArticleResponse {

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
