/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package team;

import javafx.scene.paint.Color;

/**
 * A team's nickname (ex: "Tigers", "Eagles")
 * @author RollerSimmer
 */
public class TeamNickname 
    {
    String word;
    boolean colorUsed;
    Color prefcolor;

    public String getWord()
        {
        return word;
        }

    public void setWord(String word)
        {
        this.word = word;
        }

    public boolean isColorUsed()
        {
        return colorUsed;
        }

    public void setColorUsed(boolean colorUsed)
        {
        this.colorUsed = colorUsed;
        }

    public Color getPrefcolor()
        {
        return prefcolor;
        }

    public void setPrefcolor(Color prefcolor)
        {
        this.prefcolor = prefcolor;
        }

    public TeamNickname(String word, boolean colorUsed, Color prefcolor)
        {
        this.word = word;
        this.colorUsed = colorUsed;
        this.prefcolor = prefcolor;
        }
    

    @Override
    public String toString()
        {
        return String.format("{TeamNickname: word=\"%s\",colorUsed=%s,prefcolor=%s}"
                            ,this.word
                            ,this.colorUsed? "true": "false"
                            ,this.prefcolor.toString()
                            );
        }
    }
