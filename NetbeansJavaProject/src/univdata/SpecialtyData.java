/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package univdata;

import team.CollegeTeamInfo.UnivPos;

/**
 * Contains information about a college specialty, like "A&amp;M", "Tech" or "State".
 * @author RollerSimmer
 */
public class SpecialtyData 
    {    
    /** The specialty's suffix */
    String suffix;
    /** The "University" positioning in the institution name produced with this specialty */
    UnivPos upos;

    public SpecialtyData(String suffix)
        {
        this.suffix=suffix;
        }

    public String getSuffix()
        {
        return suffix;
        }

    public UnivPos getUpos()
        {
        return upos;
        }

    public void setUpos(UnivPos upos)
        {
        this.upos = upos;
        }
    
    public SpecialtyData(String suffix, UnivPos upos)
        {
        this.suffix = suffix;
        this.upos = upos;
        }
    
    @Override
    public String toString()
        {
        return "{suffix=\""+suffix+"\"}";
        }    
    }
