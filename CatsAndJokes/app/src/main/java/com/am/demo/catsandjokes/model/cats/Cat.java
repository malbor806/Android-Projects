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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
