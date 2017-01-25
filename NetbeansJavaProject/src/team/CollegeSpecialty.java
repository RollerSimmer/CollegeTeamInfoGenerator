/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package team;

/**
 * Identifies the specialty of a university
 * @author RollerSimmer
 */
public enum CollegeSpecialty 
    {
    state
    ,agromech
    ,tech
    ,polytech
    ,agrotech
    ,mining
    ,none;

    public static CollegeSpecialty fromString(String s)
        {
        CollegeSpecialty fs=CollegeSpecialty.none;
        String ls=s.toLowerCase();
        if(ls=="state") fs=CollegeSpecialty.state;
        else if(ls=="a&m") fs=CollegeSpecialty.agromech;
        else if(ls=="tech") fs=CollegeSpecialty.tech;
        else if(ls=="poly") fs=CollegeSpecialty.polytech;
        else if(ls=="tech") fs=CollegeSpecialty.tech;
        else if(ls=="a&t") fs=CollegeSpecialty.agrotech;
        else if(ls=="mines"||ls=="school of mines")
             fs=CollegeSpecialty.mining;
        else fs=CollegeSpecialty.none;
        return fs;
        }
}