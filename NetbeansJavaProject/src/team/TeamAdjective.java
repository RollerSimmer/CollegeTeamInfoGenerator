/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package team;

import javafx.scene.paint.Color;

/**
 * A team's adjective (ex:"Running", "Screaming".)
 * @author RollerSimmer
 */
public class TeamAdjective 
    {
    public enum Type
        {
        action,
        color,
        other
        }    
    String word;
    Type adjtype;
    Color color;    

    public String getWord()
        {
        return word;
        }

    public void setWord(String word)
        {
        this.word = word;
        }

    public Type getAdjtype()
        {
        return adjtype;
        }

    public void setAdjtype(Type adjtype)
        {
        this.adjtype = adjtype;
        }
    
    public Color getColor()
        {
        return color;
        }

    public void setColor(Color color)
        {
        this.color = color;
        }

    public TeamAdjective(String word, Type adjtype, Color color)
        {
        this.word = word;
        this.adjtype = adjtype;
        this.color = color;
        }

    public static TeamAdjective.Type getTypeVal(String typetext)
        {
        if(typetext.equals("action"))
            return TeamAdjective.Type.action;
        if(typetext.equals("color"))
            return TeamAdjective.Type.color;
        // default
        return TeamAdjective.Type.other;
        }
    
    @Override
    public String toString()
        {
        return String.format("{TeamAdjective: word=\"%s\",adjtype=%s,color=%s"
                            ,word
                            ,adjtype.toString()
                            ,color.toString()
                            );
        }
    
    }
