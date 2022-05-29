package edu.skku.cs.isrun;

import java.util.ArrayList;
import java.util.Arrays;

public class RunResult {

    Integer[] NewPosterIdx;
    String[] NewAchivs;
    Integer gold;
    Integer food;
    Integer love;

    public RunResult(Integer[] newPosterIdx, String[] newAchivs, Integer gold, Integer food, Integer love) {
        NewPosterIdx = newPosterIdx;
        NewAchivs = newAchivs;
        this.gold = gold;
        this.food = food;
        this.love = love;
    }

    // attach to current NewAchivs
    public void attachNewAchi(String[] add){
        ArrayList<String> oldAchList = new ArrayList<>(Arrays.asList(NewAchivs));
        ArrayList<String> newAchList = new ArrayList<>(Arrays.asList(add));
        newAchList.addAll(oldAchList);
        NewAchivs = newAchList.toArray(new String[newAchList.size()]);
    }

    public Integer[] getNewPosterIdx() {
        return NewPosterIdx;
    }

    public void setNewPosterIdx(Integer[] newPosterIdx) {
        NewPosterIdx = newPosterIdx;
    }

    public String[] getNewAchivs() {
        return NewAchivs;
    }

    public void setNewAchivs(String[] newAchivs) {
        NewAchivs = newAchivs;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public Integer getFood() {
        return food;
    }

    public void setFood(Integer food) {
        this.food = food;
    }

    public Integer getLove() {
        return love;
    }

    public void setLove(Integer love) {
        this.love = love;
    }
}
