package com.example.workouthistory;

public class CardViewModel {

    private  String muscleName;
    private int image;

    public CardViewModel(String muscleName, int image) {
        this.muscleName = muscleName;
        this.image = image;
    }

    public String getMuscleName() {
        return muscleName;
    }

    public void setMuscleName(String muscleName) {
        this.muscleName = muscleName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
