package ru.myitschool.finalproject;

public class ReviewsBD {
    private long review_id;
    private long game_id;
    private String text;
    private int mark;

    public ReviewsBD (long review_id, String text, int mark, long game_id) {
        this.review_id = review_id;
        this.game_id = game_id;
        this.text = text;
        this.mark = mark;
    }

    public long getId() {
        return review_id;
    }

    public String getText() {
        return text;
    }

    public long getGame_id() {
        return game_id;
    }

    public int getMark() {
        return mark;
    }
}
