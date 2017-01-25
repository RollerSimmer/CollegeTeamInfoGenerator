/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package team;

import java.util.ArrayList;

/**
 * A list of collegiate teams for the purpose of uniqueness and sorting,
 * among other things
 * @author RollerSimmer
 */
public class CollegeTeamList 
    {
    /** the list of teams */
    ArrayList<CollegeTeamInfo> thelist;
    /** the minimum school enrollment allowed in this list*/
    int minEnrollment;
    /** the maximum school enrollment allowed in this list*/
    int maxEnrollment;    
      
    private static final boolean debugprint=false;

    public void setMinEnrollment(int minEnrollment)
        {
        this.minEnrollment = minEnrollment;
        }

    public void setMaxEnrollment(int maxEnrollment)
        {
        this.maxEnrollment = maxEnrollment;
        }

    public ArrayList<CollegeTeamInfo> getList()
        {
        return thelist;
        }

    public CollegeTeamList(int minEnrollment, int maxEnrollment)
        {
        this.minEnrollment = minEnrollment;
        this.maxEnrollment = maxEnrollment;
        this.thelist = new ArrayList<>();
        }
    
    public int listsize()
        {
        return thelist.size();
        }    
    
    public CollegeTeamInfo getteam(int i)
        {
        return thelist.get(i);
        }

    /**
     * Finds out whether or not school name already exists on list
     * in: assumes list is already alphabetically sorted by school name.
     * out: no changes to the list.
     * @param team the team containing the new school name
     * @return true if the school already exists on the list.
     */    
    private boolean schoolNameAlreadyOnList(CollegeTeamInfo team)
        {   
        // if the list is empty, then do this
        if(thelist.size()==0)
            // return false
            return false;
        // else, do this
        else
            {
            // do a binary search to find the proper place
            int index,redge,ledge;
            ledge=0;
            redge=thelist.size()-1;
            index=(ledge+redge)/2;
            // init done and found to true
            boolean done=false;
            boolean found=false;
            
            boolean isCaliA=false;
            boolean isCaliB=false;
            if(debugprint)
                {
                isCaliA=team.getSchool().equals("California");
                if(isCaliA)
                    System.out.print("Team A is California.\n");
                }
            
            // binary search: while not found, do this
            while(!done)
                {

                if(debugprint && isCaliA)
                    {
                    String snB=thelist.get(index).getSchool();
                    isCaliB=snB.equalsIgnoreCase("California");
                    if(isCaliB)
                        System.out.printf("Team B (at index #%d) is \"California\". \n",index);
                    else
                        System.out.printf("Team B (at index #%d) is \"%s\" instead of \"California\". \n",index,snB);
                    }
                
                // get comparison result
                String fnA=team.getCommonSchoolName();
                String fnB=thelist.get(index).getCommonSchoolName();
                int compres=fnA.compareToIgnoreCase(fnB);
                // if school name equals the current school, then do this                 
                if(compres==0)
                    {
                    // set found and done to true
                    found=true;
                    done=true;
                    }
                // elif edges overlap, then do this
                else if(ledge>=redge)
                    {
                    // set done to true
                    done=true;
                    }
                // else, do this                 
                else
                    {
                    // if school name < the current school, then do this                 
                    if(compres<0)
                        {
                        // go left
                        redge=index-1;                    
                        if(debugprint && isCaliA)
                            System.out.print("<- ");
                        }
                    // elif school name > the current school, then do this                 
                    else if(compres>0)
                        {
                        // go right
                        ledge=index+1;                    
                        if(debugprint && isCaliA)
                            System.out.print("-> ");
                        }
                    // update index
                    index=(ledge+redge)/2;                    
                    }
                } // end of while(!found) block
            return found;
            }
        }
    
    /**
     * Finds the slot where a new team fits alphabetically.
     * in: assumes list is already alphabetically sorted by school name.
     * out: no changes to the list.
     * @param team the team that needs a slot
     * @return the slot index of the proper place for the team in the sorted list.
     */
    private int findAlphaSlot(CollegeTeamInfo team)
        {
        // if the list is empty, then do this
        if(thelist.size()==0)
            // return zero
            return 0;
        // else, do this
        else
            {
            // do a binary search to find the proper place
            int index,redge,ledge;
            ledge=0;
            redge=thelist.size()-1;
            index=(ledge+redge)/2;
            boolean done=false;
            boolean found=false;
            // binary search: while not found, do this
            while(!done)
                {
                // get comparison result
                String cnA=team.getCommonSchoolName();
                String cnB=thelist.get(index).getCommonSchoolName();
                int compres=cnA.compareToIgnoreCase(cnB);
                // if school name equals the current school, then do this                 
                if(compres==0)
                    // set found and done to true
                    found=done=true;
                // elif edges overlap, then do this
                else if(ledge>=redge)
                    {
                    // if school name > the current school, then do this                 
                    if(compres>0)
                        // increment index by 1
                        index+=1;                    
                    // set found and done to true
                    found=true;
                    done=true;
                    }                    
                // else, do this                 
                else 
                    {
                    // if school name < the current school, then do this                 
                    if(compres<0)
                        // go left
                        redge=index-1;                    
                    // elif school name > the current school, then do this                 
                    else if(compres>0)
                        // go right
                        ledge=index+1;                    
                    // update index
                    index=(ledge+redge)/2;                    
                    }
                } // end of while(!found) block
            return index;
            }
        }
    
    /**
     * Adds a school's team to the list.
     * in: assumes list is already alphabetically sorted by school name
     * out: the new school will be added to the list.
     * @param team the school and team to add
     * @return true if the team was added, false if not
     */
    public boolean add(CollegeTeamInfo team)
        {
        // if already on list, then do this
        if(schoolNameAlreadyOnList(team))
            // return without adding
            return false;
        // if enrollment is out of range, then do this
        int enrollment=team.getEnrollment();        
        if(enrollment < minEnrollment || enrollment > maxEnrollment )
            // return without adding
            return false;
        // find the right slot alphabetically
        int index=this.findAlphaSlot(team);        
        // add team to slot and then return
        thelist.add(index,team);
        return true;
        }    

    }
