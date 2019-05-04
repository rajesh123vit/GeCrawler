package com.ge.crawler.model;

public class Page {

	private String address;
    private String[] links;

    public Page() {
    }

    public String getAddress() {
        return this.address;
    }

    public String[] getLinks() {
        return this.links;
    }

    public void setAddress(String a) {
        this.address = a;
    }

    public void setLinks(String[] l) {
        this.links = l;
}

}
