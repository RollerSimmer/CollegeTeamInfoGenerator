/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package placedata;

/**
 * Encapsulates the index for a single city among a list of cities.
 * @author RollerSimmer
 */
public class CityIndex
    {
    int index;

    public int getIndex()
        {
        return index;
        }

    public void setIndex(int index)
        {
        this.index = index;
        }

    public CityIndex(int index)
        {
        this.index = index;
        }
    }