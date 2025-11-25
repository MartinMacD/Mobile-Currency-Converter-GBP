//
// Name                 Martin MacDonald
// Student ID           s2425191
// Programme of Study   BSc (Hons) Software Development
//
package com.example.MacDonald_Martin_s2425191;

import android.util.Log;

public class Item {
    private String title;
    private String link;
    private String guid;
    private String pubDate;
    private String description;
    private String category;


    //Getters and Setters.
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
        Log.d("Item",String.format("Title set: %s",this.title));
    }
    public String getLink(){
        return link;
    }
    public void setLink(String link){
        this.link = link;
        Log.d("Item",String.format("Link set: %s",this.link));
    }
    public String getPubDate() {
        return pubDate;
    }
    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
        Log.d("Item",String.format("pubDate set: %s",this.pubDate));
    }
    public String getGuid() {
        return guid;
    }
    public void setGuid(String guid) {
        this.guid = guid;
        Log.d("Item",String.format("setGuid set: %s",this.guid));
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
        Log.d("Item",String.format("description set: %s",this.description));
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
        Log.d("Item",String.format("category set: %s",this.category));
    }


}


