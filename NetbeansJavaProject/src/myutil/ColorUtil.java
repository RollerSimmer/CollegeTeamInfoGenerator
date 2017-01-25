/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package myutil;

import javafx.scene.paint.Color;
import myutil.MyRandom;

/**
 * An encapsulation of static color generation functions
 * @author RollerSimmer
 */
public class ColorUtil 
    {
    public static Color genColor()
        {
        return genColor(Color.rgb(128,128,128),128);
        }
    
    public static Color genColor(Color base,int variance)
        {
        int rbase=Math.round((float)base.getRed()*255);
        int gbase=Math.round((float)base.getGreen()*255);
        int bbase=Math.round((float)base.getBlue()*255);
        int r=MyRandom.next(rbase-variance,rbase+variance);
        int g=MyRandom.next(gbase-variance,gbase+variance);
        int b=MyRandom.next(bbase-variance,bbase+variance);
        r=Math.max(Math.min(r,255),0);
        g=Math.max(Math.min(g,255),0);
        b=Math.max(Math.min(b,255),0);
        int m=Math.min(r,Math.min(g,b));
        int M=Math.max(r,Math.max(g,b));                
        return Color.rgb(r,g,b);        
        }
    
    /**
     * Gets the "distance" between color values using taxicab metric
     * @param a the first color
     * @param b the second color
     * @return The taxicab distance, in the range 0.0 to 3.0 
     *         (each color goes from 0.0 to 1.0)
     */
    public static double colorDistance(Color a,Color b)
        {
        double dr=Math.abs(a.getRed()-b.getRed());
        double dg=Math.abs(a.getGreen()-b.getGreen());
        double db=Math.abs(a.getBlue()-b.getBlue());
        double cd = dr+dg+db;
        return cd;
        }

    /**
     * Generates a random variation of a base color
     * @param color the base color
     * @param variance the variance percentage (+/-,1.0=100%)
     * @return the generated color
     */
    public static Color randomizeColor(Color color,double variance)
        {
        Color rc;
        int ivariance=(int)Math.round(variance*255);
        int r = (int)Math.round(color.getRed()*255);
        int g = (int)Math.round(color.getGreen()*255);
        int b = (int)Math.round(color.getBlue()*255);
        int rnew = r + MyRandom.next(-ivariance,ivariance);
        int gnew = g + MyRandom.next(-ivariance,ivariance);
        int bnew = b + MyRandom.next(-ivariance,ivariance);
        rnew = Math.max(Math.min(rnew,255),0);
        gnew = Math.max(Math.min(gnew,255),0);
        bnew = Math.max(Math.min(bnew,255),0);
        rc=Color.rgb(rnew,gnew,bnew);
        return rc;
        }    

    /**
     * Gets the static variance % from base for random colors
     * @return the static variance percentage (1.0=100%, 0.0=0%)
     */
    public static double getStaticVariance()
        {
        return 0.04;
        }

    public static Color makeShadeable(Color color)
        {
        Color sc; // shadeable color
        double range=0.7;
        double hi=0.55+range/2.0;
        double lo=0.55-range/2.0;
        int r=(int)Math.round((color.getRed()*range+lo)*255.0);
        int g=(int)Math.round((color.getGreen()*range+lo)*255.0);
        int b=(int)Math.round((color.getBlue()*range+lo)*255.0);
        sc=Color.rgb(r,g,b); 
        return sc;
        }
    }
