package com.am.demo.catsandjokes.model.cats;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by malbor806 on 24.05.2017.
 */

@Root(name = "image")
public class Cat {
    @Element(name = "url")
    private String url;
    @Element(name = "id")
    private String id;
    @Element(name = "source_url")
    private String sourceURL;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }
}
