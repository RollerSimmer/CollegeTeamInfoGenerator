/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ctig;

import java.util.Locale;
import javafx.scene.paint.Color;
import placedata.WeightedListItem;

/**
 * A list of colors that match the given primary color
 * @author RollerSimmer
 */
public class ColorMatcher
    {
    Color primary;
    ListPicker<Color> matchlist;
    /** the color lookup map - should be the same one in the parent table */
    ColorLookup clookup;

    public ColorLookup getLookup()
        {
        return clookup;
        }

    public void setLookup(ColorLookup lookup)
        {
        this.clookup = lookup;
        }
        
    public Color getPrimary()
        {
        return primary;
        }

    public void setPrimary(Color primary)
        {
        this.primary = primary;
        }

    public ListPicker<Color> getMatchlist()
        {
        return matchlist;
        }

    public ColorMatcher(Color primary,ColorLookup clookup)
        {
        this.primary = primary;
        matchlist=new ListPicker<>();
        this.clookup=clookup;
        }
    
    public void addmatch(String colorname,int weight)
        {
        Color mc=clookup.get(colorname.toUpperCase(Locale.ROOT));
        matchlist.additem(mc,weight);
        }
    
    public Color pickmatch()
        {
        return matchlist.pickItem();
        }
    
    @Override
    public String toString()
        {
        StringBuilder ts=new StringBuilder("");
        ts.append("{");            
        ts.append(String.format("primary=%s, ",this.primary.toString()));
        ts.append("matches={");
        int i=0;
        for(WeightedListItem<Color> match: this.matchlist.getList())            
            {
            if(i>0)
                ts.append(",");
            
            ts.append(String.format("{secondary=%s, weight=%d}"
                                   ,match.getItem().toString()
                                   ,match.getWeightsum()
                                   )
                     );            
            
            ++i;            
            }
        ts.append("}");        
        ts.append("}");
        return ts.toString();
        }    
    }
