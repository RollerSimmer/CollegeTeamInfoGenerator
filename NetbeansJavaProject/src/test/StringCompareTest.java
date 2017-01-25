/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import myutil.MyRandom;

/**
 * Tests the comparison of two strings in Java.
 * @author RollerSimmer
 */
public class StringCompareTest 
    {
    public static void main(String[] args)
        {
        StringBuilder a,b;
        a=new StringBuilder("");
        b=new StringBuilder("");
        if(false)
            {
            for(int i=0;i<100;i++)
                {
                a.setLength(0);
                b.setLength(0);
                for(int j=0;j<MyRandom.next(5,10);j++)
                    a.append(MyRandom.nextLetterOrSpace());
                for(int j=0;j<MyRandom.next(5,10);j++)
                    b.append(MyRandom.nextLetterOrSpace());
                String as=a.toString().toLowerCase();
                String bs=b.toString().toLowerCase();
                int compres=as.compareToIgnoreCase(bs);
                System.out.printf("\"%s\" compared with \"%s\" returns %d. ",as,bs,compres);
                System.out.printf("(\"%s\" %s \"%s\")\n",as,(compres<0?"<":(compres>0?">":"=")),bs);
                }
            }
        else
            {
            String as="Central New Jersey";
            String bs="California";
            int compres=as.compareToIgnoreCase(bs);
            System.out.printf("\"%s\" compared with \"%s\" returns %d. ",as,bs,compres);
            System.out.printf("(\"%s\" %s \"%s\")\n",as,(compres<0?"<":(compres>0?">":"=")),bs);
            }
        }
    }
