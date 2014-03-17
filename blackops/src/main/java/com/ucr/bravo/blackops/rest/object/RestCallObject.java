package com.ucr.bravo.blackops.rest.object;

/**
 * Created by cedric on 3/14/14.
 */
public class RestCallObject
{
    private String extension;
    private String getPost;
    String jsonParamsString;
    protected final static String BASE_URL = "http://donkeigy.endofinternet.net:18077/black-ops/rest";

    public RestCallObject( String extension, String getPost, String jsonParamsString)
    {
        this.getPost = getPost;
        this.extension = BASE_URL + extension;
        this.jsonParamsString = jsonParamsString;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getGetPost() {
        return getPost;
    }

    public void setGetPost(String getPost) {
        this.getPost = getPost;
    }

    public String getJsonParamsString() {
        return jsonParamsString;
    }

    public void setJsonParamsString(String jsonParamsString) {
        this.jsonParamsString = jsonParamsString;
    }
}
