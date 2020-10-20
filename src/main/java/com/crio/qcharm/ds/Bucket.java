package com.crio.qcharm.ds;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class Bucket {

    private List<Page> pages;

    Bucket() {
        this.pages = new ArrayList<>();
    }

    Bucket(List<Page> pages) {
        this.pages = pages;
    }

    public List<Page> getPages() {
        return this.pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }
}