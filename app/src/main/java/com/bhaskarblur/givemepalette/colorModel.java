package com.bhaskarblur.givemepalette;

public class colorModel {

    public colorModel() {

    }
    public colorModel(String hex, String name, String rgb, String hsl, String prefsName) {
        this.Hex = hex;
        this.Name = name;
        this.Rgb = rgb;
        this.hsl = hsl;
        this.prefsName=prefsName;
    }

    private String Hex;

    public String getPrefsName() {
        return prefsName;
    }

    public void setPrefsName(String prefsName) {
        this.prefsName = prefsName;
    }

    private String prefsName;
    public String getHex() {
        return Hex;
    }

    public void setHex(String hex) {
        Hex = hex;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRgb() {
        return Rgb;
    }

    public void setRgb(String rgb) {
        Rgb = rgb;
    }

    public String getHsl() {
        return hsl;
    }

    public void setHsl(String hsl) {
        this.hsl = hsl;
    }

    private String Name;
    private String Rgb;
    private String hsl;

}
