package com.example.agenceimmo;

public class Piece {
    private int id;
    private int surface;




    public int getId(){
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public int getSurface() {
        return this.surface;
    }
    public void setSurface(int surface) {
        this.surface = surface;
    }


    public Piece(int unId, int uneSurface){
        this.id = unId;
        this.surface = uneSurface;
    }
}
