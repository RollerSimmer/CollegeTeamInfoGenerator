/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package placedata;

import java.util.Random;

/**
 * Data describing the religious makeup of a state.
 * @author RollerSimmer
 */
public final class ReligionData 
    {
    
    short mormon;
    short catholic;
    short baptist;
    short methodist;
    short jewish;
    short lutheran;    
    short sum;

    public short getMormon()
        {
        return mormon;
        }

    public short getCatholic()
        {
        return catholic;
        }

    public short getBaptist()
        {
        return baptist;
        }

    public short getMethodist()
        {
        return methodist;
        }

    public short getJewish()
        {
        return jewish;
        }

    public short getLutheran()
        {
        return lutheran;
        }

    int getOther()
        {
        int go=100;
        go=Math.min(catholic,Math.min(mormon,Math.min(baptist,go)));
        go=Math.min(lutheran,Math.min(jewish,Math.min(methodist,go)));
        return go;
        }

    int getNoReligion()
        {
        return 100-Math.max(0,sum+getOther());        
        }

    public void setMormon(short mormon)
        {
        sum-=this.mormon;
        sum+=mormon;
        this.mormon = mormon;
        }

    public void setCatholic(short catholic)
        {
        sum-=this.catholic;
        sum+=catholic;
        this.catholic = catholic;
        }

    public void setBaptist(short baptist)
        {
        sum-=this.baptist;
        sum+=baptist;
        this.baptist = baptist;
        }

    public void setMethodist(short methodist)
        {
        sum-=this.methodist;
        sum+=methodist;
        this.methodist = methodist;
        }

    public void setJewish(short jewish)
        {
        sum-=this.jewish;
        sum+=jewish;
        this.jewish = jewish;
        }

    public void setLutheran(short lutheran)
        {
        sum-=this.lutheran;
        sum+=lutheran;
        this.lutheran = lutheran;
        }

    public ReligionData()
        {
        setData((short)0,(short)0,(short)0,(short)0,(short)0,(short)0);
        this.sum=0;
        }

    public ReligionData(short mormon, short catholic, short baptist, short methodist, short jewish, short lutheran)
        {
        this.setData(mormon,catholic,baptist,methodist,jewish,lutheran);        
        }
    
    public void setData(short mormon, short catholic, short baptist, short methodist, short jewish, short lutheran)
        {
        this.setMormon(mormon);
        this.setCatholic(catholic);
        this.setBaptist(baptist);
        this.setMethodist(methodist);        
        this.setJewish(jewish);
        this.setLutheran(lutheran);
        }
    
    public ReligionID pick()
        {
        Random rand=new Random();
        int pickval=rand.nextInt(this.sum+1);
        int rangeval=this.mormon;
        if(pickval<=rangeval)  return ReligionID.mormon;
        rangeval+=this.catholic;
        if(pickval<=rangeval)  return ReligionID.catholic;
        rangeval+=this.baptist;
        if(pickval<=rangeval)  return ReligionID.baptist;
        rangeval+=this.methodist;
        if(pickval<=rangeval)  return ReligionID.methodist;
        rangeval+=this.jewish;
        if(pickval<=rangeval)  return ReligionID.jewish;
        rangeval+=this.lutheran;
        if(pickval<=rangeval)  return ReligionID.lutheran;
        // default
        return ReligionID.catholic;  // since it is usually the largest
        }

    }
