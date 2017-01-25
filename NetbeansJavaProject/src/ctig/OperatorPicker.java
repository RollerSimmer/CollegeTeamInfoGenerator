/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ctig;

import team.SchoolOperator;

/**
 * Used to pick a school operator (public, private, military, etc.) from a list of operators.
 * @author RollerSimmer
 */
public class OperatorPicker extends EnumPicker<team.SchoolOperator>
    {
    
    /**
     * automatically initializes list of school operators to default values
     */
    public void autoinit()
        {
        this.clear();
        
        int[] weightlist=
            {
            224, // opublic
            117, // omilitary
            5    // oprivate
            };
        
        int i=0;
        for(SchoolOperator so: SchoolOperator.values())
            {
            this.additem(so,weightlist[i]);
            ++i;            
            }
        }
    
    
    }
