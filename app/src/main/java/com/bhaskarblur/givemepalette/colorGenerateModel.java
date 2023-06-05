package com.bhaskarblur.givemepalette;

public class colorGenerateModel {

    public colorGenerateModel() {

    }
    public colorGenerateModel(int hex) {
        this.Hex = hex;

    }

    private int Hex;
    private String rand;

    public String getRand() {
        return rand;
    }

    public void setRand(String rand) {
        this.rand = rand;
    }

    public colorGenerateModel(int parseColor, String rand) {
        this.Hex=parseColor;
        this.rand =rand;
    }

    public int getHex() {
        return Hex;
    }

    public void setHex(int hex) {
        Hex = hex;
    }
}
