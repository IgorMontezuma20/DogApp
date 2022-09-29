package com.example.dogsapp.view.view.model;

public class DogBreed {

    public String breedId;
    public String dogBredd;
    public String lifeSpan;
    public String breedGroup;
    public String bredFor;
    public String temperament;
    public String imageUrl;
    public int uui;

    public DogBreed(String breedId, String dogBredd, String lifeSpan, String breedGroup, String bredFor, String temperament, String imageUrl) {
        this.breedId = breedId;
        this.dogBredd = dogBredd;
        this.lifeSpan = lifeSpan;
        this.breedGroup = breedGroup;
        this.bredFor = bredFor;
        this.temperament = temperament;
        this.imageUrl = imageUrl;
    }
}
