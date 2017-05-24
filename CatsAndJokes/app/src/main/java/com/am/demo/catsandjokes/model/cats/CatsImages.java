package com.am.demo.catsandjokes.model.cats;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by malbor806 on 24.05.2017.
 */
@Root(name = "images")
public class CatsImages {
    @ElementList(name = "image", inline = true)
    private List<Cat> imagesList;

    public List<Cat> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<Cat> imagesList) {
        this.imagesList = imagesList;
    }
}
