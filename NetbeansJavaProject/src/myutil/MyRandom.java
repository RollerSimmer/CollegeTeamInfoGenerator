/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package myutil;

import java.util.Random;

/**
 * A custom random integer generator class
 * @author RollerSimmer
 */
public class MyRandom 
    {
    private static MyRandom instance=null;

    Random rand;
    
    protected MyRandom()
        {
        rand=new Random();
        }
    
    public static MyRandom getinstance()
        {
        if(instance==null)
            instance=new MyRandom();
        return instance;        
        }
    
    /**
     * Generate a random integer value in a range
     * @param lo the minimum of the range set
     * @param hi the maximum of the range set
     * @return 
     */
    public static int next(int lo, int hi)
        {
        if(hi<lo) return next(hi,lo);
        int n=getinstance().rand.nextInt(hi-lo+1)+lo;
        return n;
        }

    /**
     * Generate a random letter
     * @return either an uppercase or lowercase letter
     */
    public static char nextLetterOrSpace()
        {
        char nls;
        if(next(1,6)==1)
            nls=' ';
        else
            nls = next(0,1)==1? (char)next((int)'A',(int)'Z')
                              : (char)next((int)'a',(int)'z');
        return nls;
        }

    }
