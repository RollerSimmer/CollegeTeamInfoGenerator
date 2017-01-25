/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ctig;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import myutil.ColorUtil;
import myutil.MyRandom;
import team.CollegeTeamInfo;
import team.ColorPair;

/**
 * Represents the row of a team in the GUI - table version.
 * @author RollerSimmer
 */
public class TeamTableRow
    {    
    Integer index;
    Canvas primary;
    Canvas secondary;
    Group helmet;
    String full;
    String common;
    String abrv;
    String tnick;
    String operator;
    String affiliation;
    String flagshipStatus;
    Integer enrollment;
    String city;
    Integer cityPop;
    String state;   
    Integer statePop;

    private ColorPair colors;

    public Integer getIndex()
        {
        return index;
        }

    public Canvas getPrimary()
        {
        return primary;
        }

    public Canvas getSecondary()
        {
        return secondary;
        }

    public Group getHelmet()
        {
        return helmet;
        }

    public String getFull()
        {
        return full;
        }

    public String getCommon()
        {
        return common;
        }

    public String getAbrv()
        {
        return abrv;
        }

    public String getTnick()
        {
        return tnick;
        }

    public String getOperator()
        {
        return operator;
        }

    public String getAffiliation()
        {
        return affiliation;
        }

    public String getFlagshipStatus()
        {
        return flagshipStatus;
        }

    public Integer getEnrollment()
        {
        return enrollment;
        }

    public String getCity()
        {
        return city;
        }

    public Integer getCityPop()
        {
        return cityPop;
        }

    public String getState()
        {
        return state;
        }

    public Integer getStatePop()
        {
        return statePop;
        }

    public ColorPair getColors()
        {
        return colors;
        }

    public TeamTableRow(int index, CollegeTeamInfo team)
        {
        int cw=30;
        int ch=20;
        GraphicsContext gc;
        this.colors=team.getColors();
        this.primary=new Canvas(cw,ch);
        gc=this.primary.getGraphicsContext2D();
        gc.setFill(colors.getPrimary());
        gc.fillRect(0,0,cw,ch);
        this.secondary=new Canvas(cw,ch);
        gc=this.secondary.getGraphicsContext2D();
        gc.setFill(colors.getSecondary());
        gc.fillRect(0,0,cw,ch);

        makeHelmet();
        
        int capwidth=75;

        this.index=index;
        this.full=team.getFullSchoolName();
        this.common=team.getCommonSchoolName();
        this.abrv=team.getSchoolInitialism();
        this.tnick=team.getNickname();
        this.operator=team.getSoperator().toString();
        this.affiliation=team.getAffiliation().toString();
        this.flagshipStatus=team.isStateFlagship()? "Yes": "No";
        this.enrollment=team.getEnrollment();
        this.city=team.getCityName();
        this.cityPop=team.getcity().getPopulation();
        this.state=team.getStateName();
        this.statePop=team.getstate().getPopulation();
        }

    private void addColoredHelmetLayer(String partname,Color color)
        {
        Color shadecolor=ColorUtil.makeShadeable(color);           

        Image alphaimg = HelmetMaker.getAlphaImage(partname);
        Rectangle colorview=new Rectangle(alphaimg.getWidth(),alphaimg.getHeight(),shadecolor);
        ImageView alphaview=new ImageView(alphaimg);  
        Image light=HelmetMaker.getLightImage(partname);
        ImageInput lightinput=new ImageInput(light);  

        Blend blend=new Blend(BlendMode.SOFT_LIGHT);
        blend.setTopInput(lightinput);

        colorview.setClip(alphaview);
        colorview.setEffect(blend);

        this.helmet.getChildren().add(colorview);
        }

    private void addHelmetLayer(String partname)
        {
        Image img = HelmetMaker.getHelmetImage(partname);;
        ImageView imgview=new ImageView(img);  
        this.helmet.getChildren().add(imgview);
        }

    private void makeHelmet()
        {
        //double scale=1.0/1.0;
        this.helmet=new Group();
        int pickval=MyRandom.next(0,1);
        Color facemaskColor=pickval==1? colors.getSecondary(): colors.getPrimary();
        Color shellcolor=pickval==1? colors.getPrimary(): colors.getSecondary();
        addColoredHelmetLayer("facemask_back",facemaskColor);
        addHelmetLayer("shell_back");
        addColoredHelmetLayer("shell_front",shellcolor);
        addHelmetLayer("straps");
        addColoredHelmetLayer("facemask_front",facemaskColor);
        }        
    }
