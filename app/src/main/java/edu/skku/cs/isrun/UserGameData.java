package edu.skku.cs.isrun;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UserGameData {
    private String userid;
    private int food;
    private int gold;
    private int mcharidx;
    private int mposteridx;
    public int a=0;
    private InnerData UserChars[]=new InnerData[]{};
    private InnerData2 UserPosters[]=new InnerData2[]{};

    private int character_list[] = new int[]{};
    private int poster_list[] = new int[] {0};

    public InnerData[] getUserChars() {        return UserChars;    }

    public void setUserChars(String[] userData) {
        Gson gson = new GsonBuilder().create();
        for (String i:userData) {
            this.UserChars[a] = gson.fromJson(i,InnerData.class);
            //this.character_list[a]=this.UserChars[a].getCharidx();
            a++;
        }
    }
    public InnerData2[] getUserPosters() {        return UserPosters;    }
    public void setUserPosters(String[] userData) {
        Gson gson = new GsonBuilder().create();
        for (String i:userData) {
            this.UserPosters[a] = gson.fromJson(i,InnerData2.class);
            //this.character_list[a]=this.UserChars[a].getCharidx();
            a++;
        }
    }

    public void setUserPosters(InnerData2[] userPosters) {
        UserPosters = userPosters;
    }

    public void setUserChars(InnerData[] userData) {
        this.UserChars = userData;
    }


    public void Feed() { this.food-=1; }
    public String getUserid() {        return userid;    }
    public void setUserid(String userid) {        this.userid = userid;    }
    public int getFood() {        return food;    }
    public void setFood(int food) {        this.food = food;    }
    public int getGold() {        return gold;    }
    public void setGold(int gold) {        this.gold = gold;    }
    public int getMcharidx() {        return mcharidx;    }
    public void setMcharidx(int mcharidx) {        this.mcharidx = mcharidx;    }
    public int getMposteridx() {        return mposteridx;    }
    public void setMposteridx(int mposteridx) {        this.mposteridx = mposteridx;    }
    public int[] getCharacter_list() {
        return character_list;
    }
    public void setCharacter_list(int[] character_list) {
        this.character_list = character_list;
    }
    public int[] getPoster_list() {
        return poster_list;
    }
    public void setPoster_list(int[] poster_list) {
        this.poster_list = poster_list;
    }
}
