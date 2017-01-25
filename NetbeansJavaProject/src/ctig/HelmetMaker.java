/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ctig;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.image.Image;

/**
 * Used to make a helmet graphic to display in the CTIG team table.
 * @author RollerSimmer
 */
public class HelmetMaker 
    {
    private static HelmetMaker theinstance=null;
    
    HashMap<String,Integer> helmmap;
    HashMap<String,Integer> alphamap;
    ArrayList<Image> imglist;
    
    protected HelmetMaker()
        {
        this.helmmap=new HashMap<>();
        this.alphamap=new HashMap<>();
        this.imglist=new ArrayList<>();        
        this.addColoredStream("shell_front");
        this.addColoredStream("facemask_front");
        this.addColoredStream("facemask_back");
        this.addStream("straps");
        this.addStream("shell_back");
        }
    
    public HashMap<String,Integer> getHelmetMap()
        {
        return helmmap;
        }
    
    public HashMap<String,Integer> getAlphaMap()
        {
        return alphamap;        
        }    
    
    private void addColoredStream(String partname)
        {
        try {
            FileInputStream fis;
            Image i;
            
            fis = new FileInputStream("graphics/helm_"+partname+".png");
            i = new Image(fis);
            if(this.imglist.add(i))           
                helmmap.put(partname,this.imglist.size()-1);  
            
            fis = new FileInputStream("graphics/alpha_"+partname+".png");
            i = new Image(fis);
            if(this.imglist.add(i))
                alphamap.put(partname,this.imglist.size()-1);  
            }
        catch(Exception ex)
            {  
            System.err.println(ex);  
            System.out.printf("Exception in addColoredStream(\"%s\")\n",partname);
            return;
            }
        }
    
    private void addStream(String partname)
        {
        try {
            FileInputStream fis=new FileInputStream("graphics/helm_"+partname+".png");
            Image i=new Image(fis);
            if(this.imglist.add(i))
                helmmap.put(partname,this.imglist.size()-1);  
            }
        catch(Exception ex)
            {  
            System.err.println(ex);  
            System.out.printf("Exception in addStream(\"%s\")\n",partname);
            return;
            }
        }

    public static HelmetMaker getinstance()
        {
        if(theinstance==null)
            theinstance=new HelmetMaker();
        return theinstance;        
        }

    public static Image getLightImage(String partname)
        {
        HelmetMaker hm=getinstance();        
        return hm.imglist.get(hm.helmmap.get(partname));
        }

    public static Image getHelmetImage(String partname)
        {
        return getLightImage(partname);
        }
        
    public static Image getAlphaImage(String partname)
        {
        HelmetMaker hm=getinstance();        
        return hm.imglist.get(hm.alphamap.get(partname));
        }
    }
