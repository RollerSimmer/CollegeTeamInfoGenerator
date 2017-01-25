/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ctig;

import java.util.ArrayList;
import myutil.MyRandom;
import placedata.WeightedListItem;

/**
 * Generic class used to pick an item from the list;  most pickers in CTIG use this base class
 * @author RollerSimmer
 */
public class ListPicker <T>
    {
    ArrayList<WeightedListItem<T>> list;
    int weightsum;

    private static boolean debugprint=false;

    public ArrayList<WeightedListItem<T>> getList()
        {
        return list;
        }

    public int getWeightsum()
        {
        return weightsum;
        }
    
    public void additem(T item,int weight)
        {
        this.weightsum+=weight;
        WeightedListItem<T> witem=new WeightedListItem<>(item,weightsum);
        this.list.add(witem);        
        }    

    public ListPicker()
        {
        this.list=new ArrayList<>();
        this.weightsum = 0;
        }
    
    public T getitem(int i)
        {
        return this.list.get(i).getItem();
        }
    
    public int getItemWeight(int i)
        {
        int weight;
        weight=this.list.get(i).getWeightsum();
        if(i!=0)
            weight-=this.list.get(i-1).getWeightsum();
        return weight;
        }
    
    public void clear()
        {
        this.list.clear();
        this.weightsum=0;
        }   
    
    public Integer pick()
        {
        // implementation uses a binary search on the weightsum
        
        // if there are no entries, return empty string
        if (this.list.size()==0) return null;            
            
        int pickval; // the pick value
        pickval = MyRandom.next(0,this.weightsum);        
        int leftedge=0;
        int rightedge=this.list.size()-1;
        int i=(leftedge+rightedge)/2;      // the index for the binary search
        int lw = i==0? 0: list.get(i-1).getWeightsum()+1;
        int rw = list.get(i).getWeightsum();
        
        boolean found=false;
        
        if(debugprint)
            {
            System.out.printf("ListPicker.find(): list.size()=%d, target=%d, sequence={{%d,%d(%d..%d),%d}",list.size(),pickval,leftedge,i,lw,rw,rightedge);
            }
        
        // while not found
        while(!found)
            {            
            // if left egde >= right edge, then do this
            if(leftedge>=rightedge)
                {
                // set found to true
                found=true;
                }            
            // elif pick value is in the range [lwgt,rwgt], then do this
            else if(pickval>=lw && pickval<=rw)
                {
                // set found to true
                found=true;
                }
            // elif pick value < left weight, then do this
            else if (pickval<lw)
                {
                // if current index == left edge, then do this
                if(i==leftedge)
                    // set found to true
                    found=true;
                // else, do this
                else
                    {
                    // go left and continue
                    rightedge=i-1;
                    i=(leftedge+rightedge)/2;
                    }                    
                }
            // elif pick value > right weight, then do this
            else if (pickval>rw)
                {
                // if current index == right edge, then do this
                if(i==rightedge)                    
                    // set found to true
                    found=true;
                // else, do this
                else
                    {
                    // go right and continue
                    leftedge=i+1;
                    i=(leftedge+rightedge)/2;
                    }
                }
            
            lw = i==0? 0: list.get(i-1).getWeightsum()+1;
            rw = list.get(i).getWeightsum();            
            
            if(debugprint && !found)
                System.out.printf("{%d,%d(%d..%d),%d}",leftedge,i,lw,rw,rightedge);
            } // end of while(!found) loop

        if(debugprint)
            System.out.printf("} actually-found? %s\n",(pickval>=lw && pickval<=rw?"yes":"no"));
        
        return i;
        }

    T pickItem()
        {
        Integer i=pick();
        if(i==null)
            return null;
        else
            return this.list.get(i).getItem();
        }

    public String getClassName()
        {
        Class<?> enclosingClass = getClass().getEnclosingClass();
        String sn;
        if (enclosingClass != null) 
            sn=enclosingClass.getName()+" object"; 
        else 
            sn=getClass().getName()+" object";         
        return sn;
        }
    
    public void print(String objectname)
        {
        System.out.printf("** %s contents **\n",objectname);
        System.out.printf("  item list:\n");
        for (WeightedListItem<T> litem: list)
            {
            System.out.printf("    item=%s, weightSum=%d\n"
                ,litem.getItem().toString(),litem.getWeightsum());       
            }            
        System.out.printf("  total weightsum=%d\n",this.weightsum);
        }    

    public void print()
        {
        print(getClassName());
        }
    
    public int listsize()
        {
        return list.size();
        }    
    }
