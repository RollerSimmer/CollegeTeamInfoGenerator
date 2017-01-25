/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package placedata;

/**
 * The religious and linguistic makeup of a state.
 * @author RollerSimmer
 */
public class StateDemographics 
    {
    /** the state's religious makeup */
    ReligionData religion;
    /** the state's Hispanic population percentage, directly corresponding to Spanish names */
    short spanish;             

    public void setReligion(ReligionData religion)
        {
        if(religion!=null)
            this.religion = religion;
        }

    public void setSpanish(short spanish)
        {
        this.spanish = (short) Math.max(Math.min(spanish,100),0);
        }

    public ReligionData getReligion()
        {
        return religion;
        }

    public short getSpanish()
        {
        return spanish;
        }

    public StateDemographics()
        {
        }

    public StateDemographics(ReligionData religion, short spanish)
        {
        this.religion = religion;
        this.spanish = spanish;
        }

    public int getReligiousPct(ReligionID affiliation)
        {
        switch(affiliation)
            {
            case mormon: return religion.getMormon();
            case catholic: return religion.getCatholic();
            case methodist: return religion.getMethodist();
            case baptist: return religion.getBaptist();
            case jewish: return religion.getJewish();
            case lutheran: return religion.getLutheran();
            case other: return religion.getOther();
            case none: return religion.getNoReligion();
            default: return 0;            
            }
        }
    }
