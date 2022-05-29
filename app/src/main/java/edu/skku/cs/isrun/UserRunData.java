package edu.skku.cs.isrun;

public class UserRunData {

    String userid;
    int mcharidx;
    int run;
    int food;
    int gold;

    public UserRunData() {
        this.userid = "";
        this.mcharidx = 0;
        this.run = 0;
        this.food = 0;
        this.gold = 0;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getMcharidx() {
        return mcharidx;
    }

    public void setMcharidx(int mcharidx) {
        this.mcharidx = mcharidx;
    }

    public int getRun() {
        return run;
    }

    public void setRun(int run) {
        this.run = run;
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

}
