package com.am.demo.catsandjokes.model.cats;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by malbor806 on 24.05.2017.
 */

@Root(name = "response")
public class CatsResponse {
    @Element(name = "data")
    private CatsDataImages catsImages;

    public CatsDataImages getCatsDataImages() {
        return catsImages;
    }

    public void setCatsDataImages(CatsDataImages catsImages) {
        this.catsImages = catsImages;
    }
}
