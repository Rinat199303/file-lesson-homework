package gamedata;

import java.io.Serializable;

public class GameProgress implements Serializable {
    private static final long serialVersionUID = 1452964273837440209L;

    private int health;
    private int weapons;
    private int lvl;
    private double distance;

    private String city;

    public GameProgress(int health, int weapons, int lvl, double distance, String city) {
        this.health = health;
        this.weapons = weapons;
        this.lvl = lvl;
        this.distance = distance;
        this.city = city;
    }

    @Override
    public String toString() {
        return "{" +
                "health=" + health +
                ", weapons=" + weapons +
                ", lvl=" + lvl +
                ", distance=" + distance +
                ", city=" + city +
                '}';
    }
}
