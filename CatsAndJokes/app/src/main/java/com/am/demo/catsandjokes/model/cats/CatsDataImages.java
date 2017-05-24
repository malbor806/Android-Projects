package com.am.demo.catsandjokes.model.cats;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by malbor806 on 24.05.2017.
 */

@Root(name = "data")
public class CatsDataImages {
    @Element(name = "images")
    private CatsImages catsImages;

    public CatsImages getCatsImages() {
        return catsImages;
    }

    public void setCatsImages(CatsImages catsImages) {
        this.catsImages = catsImages;
    }
}
