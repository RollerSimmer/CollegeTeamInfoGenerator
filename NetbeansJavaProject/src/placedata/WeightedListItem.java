/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package placedata;

/**
 * A generic for holding a single data item and its weight in a list.
 * @author RollerSimmer
 * @param <T> the type of object in this list
 */
public class WeightedListItem <T>
    {
    /** the item */
    T item;    
    /** the working sum of weights of items in this list; should be greater than the item before, by a margin of (item-weight) */
    int weightsum;

    public T getItem()
        {
        return item;
        }

    public void setItem(T item)
        {
        this.item = item;
        }

    public int getWeightsum()
        {
        return weightsum;
        }

    public void setWeightsum(int weightsum)
        {
        this.weightsum = weightsum;
        }

    public WeightedListItem(T item, int weightsum)
        {
        this.item = item;
        this.weightsum = weightsum;
        }
    }
