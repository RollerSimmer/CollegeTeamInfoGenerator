/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package placedata;

/**
 * Information about a school's location in the world.
 * @author RollerSimmer
 */
public class SchoolLocation 
    {
    /** ID of the state, -1 means no state **/ 
    int stateId;
    /** ID of the city, -1 means no city **/ 
    int cityId;

    public int getStateId()
        {
        return stateId;
        }

    public int getCityId()
        {
        return cityId;
        }

    public SchoolLocation(int stateId, int cityId)
        {
        this.stateId = stateId;
        this.cityId = cityId;
        }
    }
