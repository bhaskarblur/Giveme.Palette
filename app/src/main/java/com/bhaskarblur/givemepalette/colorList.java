package com.bhaskarblur.givemepalette;

import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class colorList {

    private List<String> colorList_;

    public String getRandomColor() {
        return colorList_.get(new Random().nextInt(colorList_.size()));
    }
    public colorList() {
        colorList_=new ArrayList<>();
        colorList_.add(String.valueOf("#2D2727"));
        colorList_.add(String.valueOf("#E4A5FF"));
        colorList_.add(String.valueOf("#FFAAC9"));
        colorList_.add(String.valueOf("#FFE7CE"));
        colorList_.add(String.valueOf("#D8C4B6"));
        colorList_.add(String.valueOf("#0079FF"));
        colorList_.add(String.valueOf("#00DFA2"));
        colorList_.add(String.valueOf("#F6FA70"));
        colorList_.add(String.valueOf("#884A39"));
        colorList_.add(String.valueOf("#C38154"));
        colorList_.add(String.valueOf("#FF0060"));
        colorList_.add(String.valueOf("#9376E0"));
        colorList_.add(String.valueOf("#E893CF"));
        colorList_.add(String.valueOf("#00C4FF"));
        colorList_.add(String.valueOf("#CD1818"));
        colorList_.add(String.valueOf("#116D6E"));
        colorList_.add(String.valueOf("#27374D"));
        colorList_.add(String.valueOf("#E55807"));
        colorList_.add(String.valueOf("#025464"));
        colorList_.add(String.valueOf("#DB005B"));
        colorList_.add(String.valueOf("#B799FF"));
        colorList_.add(String.valueOf("#E4A5FF"));
        colorList_.add(String.valueOf("#FFB84C"));
        colorList_.add(String.valueOf("#2CD3E1"));
        colorList_.add(String.valueOf("#1B9C85"));
        colorList_.add(String.valueOf("#F0EDD4"));
        colorList_.add(String.valueOf("#537188"));
        colorList_.add(String.valueOf("#D25380"));
        colorList_.add(String.valueOf("#C07F00"));
        colorList_.add(String.valueOf("#E06469"));
        colorList_.add(String.valueOf("#F97B22"));
        colorList_.add(String.valueOf("#00FFCA"));
        colorList_.add(String.valueOf("#088395"));
        colorList_.add(String.valueOf("#F3E99F"));
        colorList_.add(String.valueOf("#263A29"));
        colorList_.add(String.valueOf("#D14D72"));
        colorList_.add(String.valueOf("#D21312"));
        colorList_.add(String.valueOf("#FF8400"));
        colorList_.add(String.valueOf("#5C469C"));
        colorList_.add(String.valueOf("#1B9C85"));
        colorList_.add(String.valueOf("#884A39"));
        colorList_.add(String.valueOf("#482121"));
        colorList_.add(String.valueOf("#4942E4"));
        colorList_.add(String.valueOf("#0079FF"));
        colorList_.add(String.valueOf("#9336B4"));
        colorList_.add(String.valueOf("#3A1078"));
        colorList_.add(String.valueOf("#D5CEA3"));
        colorList_.add(String.valueOf("#000000"));
        colorList_.add(String.valueOf("#C84B31"));
        colorList_.add(String.valueOf("#C69749"));
        colorList_.add(String.valueOf("#03C988"));
        colorList_.add(String.valueOf("#F806CC"));
        colorList_.add(String.valueOf("#8B9A46"));
        colorList_.add(String.valueOf("#FFD95A"));
        colorList_.add(String.valueOf("#FF6000"));
        colorList_.add(String.valueOf("#E4A5FF"));
        colorList_.add(String.valueOf("#FF577F"));
        colorList_.add(String.valueOf("#42032C"));
        colorList_.add(String.valueOf("#87CBB9"));
        colorList_.add(String.valueOf("#C4B0FF"));
        colorList_.add(String.valueOf("#AA77FF"));
        colorList_.add(String.valueOf("#97DEFF"));
        colorList_.add(String.valueOf("#F9F54B"));
        colorList_.add(String.valueOf("#F6FA70"));
        colorList_.add(String.valueOf("#FBC252"));
        colorList_.add(String.valueOf("#54B435"));
        colorList_.add(String.valueOf("#FDFF00"));
        colorList_.add(String.valueOf("#277BC0"));
        colorList_.add(String.valueOf("#E64848"));
        colorList_.add(String.valueOf("#C7E8F0"));
        colorList_.add(String.valueOf("#97DEFF"));
        colorList_.add(String.valueOf("#F6812B"));
        colorList_.add(String.valueOf("#9C1B05"));
        colorList_.add(String.valueOf("#E7D9C8"));
        colorList_.add(String.valueOf("#298F9D"));
        colorList_.add(String.valueOf("#324847"));
        colorList_.add(String.valueOf("#EC926A"));
        colorList_.add(String.valueOf("#2F1320"));
        colorList_.add(String.valueOf("#171B1D"));
        colorList_.add(String.valueOf("#A83950"));
        colorList_.add(String.valueOf("#657F69"));

    }

    public List<String> getColorList() {
        return colorList_;
    }

    public void addItems(String color) {
        colorList_.add(color);
    }
}
