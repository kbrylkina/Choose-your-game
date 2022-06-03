package ru.myitschool.finalproject;

public class GamesBD {
    private long id;
    private String game_name;

    public GamesBD (long id, String game_name) {
        this.id = id;
        this.game_name = game_name;
    }

    public long getId() {
        return id;
    }

    public String getGame_name() {
        return game_name;
    }

}
