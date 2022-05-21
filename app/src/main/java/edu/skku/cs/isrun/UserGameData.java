package edu.skku.cs.isrun;

public class UserGameData {
    private String userid;
    private int food;
    private int gold;

    private int mcharidx;
    private int mposteridx;

    private int character_list[] = new int[]{0};
    private int poster_list[] = new int[] {0};

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
