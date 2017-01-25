/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package team;

import javafx.scene.paint.Color;
import myutil.ColorUtil;
import myutil.MyRandom;

/**
 * A pair of colors, primary and secondary. 
 * @author RollerSimmer
 */
public class ColorPair 
    {
    /** primary color */
    Color cprimary;
    /** secondary color */    
    Color csecondary;
    
    private static boolean debugprint=false;

    public Color getPrimary()
        {
        return cprimary;
        }

    public void setPrimary(Color primary)
        {
        this.cprimary = primary;
        }

    public Color getSecondary()
        {
        return csecondary;
        }

    public void setSecondary(Color secondary)
        {
        this.csecondary = secondary;
        }

    public ColorPair(Color cprimary, Color csecondary)
        {
        this.cprimary = cprimary;
        this.csecondary = csecondary;
        }

    public ColorPair()
        {
        this.cprimary = ColorUtil.genColor();
        this.csecondary = ColorUtil.genColor();
        }    
    
    /**
     * swaps colors
     */
    public void swap()
        {
        Color tmp=cprimary;
        cprimary=csecondary;
        csecondary=tmp;      
        }
    
    /**
     * Gets the brightness sum of a color 
     * @param color the color for which the brightness is wanted
     * @return the brightness sum, from 0.0 to 3.0
     */
    public static double getBrightSum(Color color)    
        {
        // spreadsheet formula : =ROUND((V4*3+W4*10+X4*1)/1190,2)
        // -> (3r+10g+b)/1190
        double wr = color.getRed() * 255.0 * 3.0;
        double wg = color.getGreen() * 255.0 * 10.0;
        double wb = color.getBlue() * 255.0 * 1.0;
        double brightsum = (wr+wg+wb)/1190.0;
        return brightsum;
        }
    
    
    /**
     * Checks to see if a color is too bright
     * @param color the color to check
     * @param threshold the threshold beyond which the color is deemed "too bright"
     * @return true if the color is too bright, false otherwise
     */
    public static boolean isTooBright(Color color,double threshold)
        {
        return getBrightSum(color) > threshold;                
        }
    
    /**
     * swaps the two colors if the secondary color is not considered too bright
     */
    public void smartswap()
        {
        double whiteThreshold=1.8;
        boolean pbright=isTooBright(cprimary,whiteThreshold);
        boolean sbright=isTooBright(csecondary,whiteThreshold);
        
        if(this.lightenSimilarSecondary())
            this.smartDarkenPrimary(whiteThreshold);
        else if(pbright)
            {
            if(!sbright)
                this.swap();            
            else if(sbright)                
                this.smartDarkenPrimary(whiteThreshold);
            }
        }    

    /**
     * Intelligently darkens the primary color to meet a desired darkness
     * @param whiteThreshold the brightness value over which darkening is triggered
     */
    private void smartDarkenPrimary(double whiteThreshold)
        {
        double pb=ColorPair.getBrightSum(cprimary);  // primary brightness
        double factor=1.0;
        if(pb>=whiteThreshold)
            factor=whiteThreshold/pb;
        cprimary=Color.rgb((int)Math.round(cprimary.getRed()*factor*255)
                          ,(int)Math.round(cprimary.getGreen()*factor*255)
                          ,(int)Math.round(cprimary.getBlue()*factor*255)
                          );
        }
    
    /**
     * Makes random offshoots of the current colors
     * @param variance  the amount of variance 1.0=100%
     */    
    public void randomizeColors(double variance)
        {
        if(debugprint)
            System.out.printf("    <!-- before: %s %s -->\n",cprimary.toString(),csecondary.toString());
        cprimary=ColorUtil.randomizeColor(cprimary,variance);
        csecondary=ColorUtil.randomizeColor(csecondary,variance);        
        if(debugprint)
            System.out.printf("    <!-- after: %s %s -->\n",cprimary.toString(),csecondary.toString());
        }

    private boolean tooSimilar()
        {
        double sum;
        double tolerance = 0.2;
        sum = 0;
        sum += Math.abs(cprimary.getRed()-csecondary.getRed());
        sum += Math.abs(cprimary.getGreen()-csecondary.getGreen());
        sum += Math.abs(cprimary.getBlue()-csecondary.getBlue());
        return sum < tolerance;
        }

    private boolean lightenSimilarSecondary()
        {
        boolean lss;
        lss=this.tooSimilar();
        if(lss)
            {            
            double r,rr,g,gg,b,bb;
            r = (csecondary.getRed()+3.0)/4.0;
            g = (csecondary.getGreen()+3.0)/4.0;
            b = (csecondary.getBlue()+3.0)/4.0;
            rr=Math.min(csecondary.getRed()*2.0,1.0);
            gg=Math.min(csecondary.getGreen()*2.0,1.0);
            bb=Math.min(csecondary.getBlue()*2.0,1.0);
            r=Math.max(r,rr);
            g=Math.max(g,gg);
            b=Math.max(b,bb);
            csecondary=Color.rgb((int)Math.round(r*255)
                                ,(int)Math.round(g*255)
                                ,(int)Math.round(b*255));            
            }
        else
            System.out.println();
        return lss;
        }
    }
