/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ctig;

import placedata.WeightedListItem;

/**
 * Type used to pick enumerated values from a list.
 * @author RollerSimmer
 */
class EnumPicker<T> extends ListPicker<T>
    {   

    public void print()
        {
        System.out.printf("** %s object contents **\n",getClassName());
        System.out.printf("  item list:\n",this.weightsum);
        for (WeightedListItem<T> litem: list)
            {
            System.out.printf("    item=%s, weight=%d\n"
                ,litem.getItem().toString(),litem.getWeightsum());       
            }            
        System.out.printf("  total weightsum=%d\n",this.weightsum);
        }
    
    }
