package edu.skku.cs.isrun;

public class UserData_game {

    private int food;
    private int gold;
    private int character_list[];
    private String userid;
    private int poster_list[];
    private String mainchar;
    private String mainposter;

    public String getMainchar() {
        return mainchar;
    }

    public void setMainchar(String mainchar) {
        this.mainchar = mainchar;
    }

    public String getMainposter() {
        return mainposter;
    }

    public void setMainposter(String mainposter) {
        this.mainposter = mainposter;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int[] getCharacter_list() {
        return character_list;
    }

    public void setCharacter_list(int[] character_list) {
        this.character_list = character_list;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int[] getPoster_list() {
        return poster_list;
    }

    public void setPoster_list(int[] poster_list) {
        this.poster_list = poster_list;
    }
}
