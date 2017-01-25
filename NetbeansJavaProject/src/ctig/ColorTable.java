/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ctig;

import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import myutil.ColorUtil;
import placedata.WeightedListItem;

/**
 * A table of color matches for selecting 2-color schemes
 * @author RollerSimmer
 */
public class ColorTable 
    {
    ListPicker<ColorMatcher> rowlist; 
    ColorLookup colorlookup;
    HashMap<String,Integer> rowlookup;

    public ColorLookup getLookup()
        {
        return colorlookup;
        }

    public ListPicker<ColorMatcher> getRowlist()
        {
        return rowlist;
        }

    public ColorTable()
        {
        rowlist=new ListPicker<>();
        colorlookup=new ColorLookup();
        rowlookup=new HashMap<>();
        }
    
    public void defineColor(String colorname,Color color)
        {
        colorlookup.put(colorname.toUpperCase(Locale.ROOT),color);
        }
    
    public void addrow(String colorname, int weight)
        {
        try
            {
            String ucname=colorname.toUpperCase(Locale.ROOT);
            Color primary=colorlookup.get(ucname);
            if(primary == null)
                throw new Exception("Color name was not in the lookup table");
            ColorMatcher cm=new ColorMatcher(primary,colorlookup);
            Integer index=rowlist.listsize();
            this.rowlookup.put(ucname,index);
            rowlist.additem(cm,weight);
            } 
        catch (Exception ex)
            {
            Logger.getLogger(ColorTable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
    
    public ColorMatcher getrow(String colorname)
        {
        return rowlist.getitem(rowlookup.get(colorname.toUpperCase(Locale.ROOT)));
        }

    ColorMatcher pickrow()
        {
        return this.rowlist.pickItem();
        }

    ColorMatcher getClosestColor(Color color)
        {
        try
            {
            // closest color index
            int ccindex=-1;
            // closest color distance
            double ccdist=3.1; // init to just over the max return value of color distance method
            
            for(int i=0;i<this.rowlist.listsize();i++)
                {
                Color cc=this.rowlist.getitem(i).primary; // current color
                // if current list color is closer to target color than previous, then do this
                double curdist=ColorUtil.colorDistance(color,cc);
                if(curdist<ccdist)
                    {                                                           
                    // replace closest color index
                    ccindex=i;
                    // replace closest color distance
                    ccdist=curdist;
                    }
                }
            if(ccindex!=-1)
                return rowlist.getitem(ccindex);
            else
                throw new Exception("No closest color found");
            }
        catch (Exception ex)
            {
            Logger.getLogger(ColorTable.class.getName()).log(Level.SEVERE, null, ex);
            return null;
            }
        }
    
    public void print()
        {
        System.out.printf("** ctig.ColorTable contents **\n");
        System.out.printf("  color lookup table:\n");
        System.out.printf("    %s\n",this.colorlookup.toString());
        System.out.printf("  row lookup table:\n");
        System.out.printf("    %s\n",this.rowlookup.toString());
        System.out.printf("  color table:\n");        
        int i=0;
        for(WeightedListItem<ColorMatcher> row: rowlist.getList())
            System.out.printf("    row[%d]=%s\n",i++,row.getItem().toString());
        System.out.printf("    weightsum=%d\n",rowlist.getWeightsum());
        }    

    }
