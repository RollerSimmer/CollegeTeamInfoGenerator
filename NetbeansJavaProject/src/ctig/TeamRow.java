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
import team.CollegeTeamInfo;
import team.ColorPair;

/**
 * Represents the row of a team in the GUI - grid version.
 * @author RollerSimmer
 */
public class TeamRow
    {    
    Label index;
    Canvas primary;
    Canvas secondary;
    Group helmet;
    Label full;
    Label common;
    Label abrv;
    Label tnick;
    Label operator;
    Label affiliation;
    Label flagshipStatus;
    Label enrollment;
    Label city;
    Label state;        

    private ColorPair colors;

    public Label getIndex()
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

    public Label getFull()
        {
        return full;
        }

    public Label getCommon()
        {
        return common;
        }

    public Label getAbrv()
        {
        return abrv;
        }

    public Label getTnick()
        {
        return tnick;
        }

    public Label getOperator()
        {
        return operator;
        }

    public Label getAffiliation()
        {
        return affiliation;
        }

    public Label getFlagshipStatus()
        {
        return flagshipStatus;
        }

    public Label getEnrollment()
        {
        return enrollment;
        }

    public Label getCity()
        {
        return city;
        }

    public Label getState()
        {
        return state;
        }

    public Group getHelmet()
        {
        return helmet;
        }

    public TeamRow(int index, CollegeTeamInfo team)
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

        this.index=new Label(Integer.toString(index));
        this.full=new Label(team.getFullSchoolName());
        this.full.setWrapText(true);
        this.common=new Label(team.getCommonSchoolName());
        this.common.setWrapText(true);
        this.abrv=new Label(team.getSchoolInitialism());
        this.tnick=new Label(team.getNickname());
        this.tnick.setWrapText(true);
        this.operator=new Label(team.getSoperator().toString());
        this.operator.setWrapText(true);
        this.affiliation=new Label(team.getAffiliation().toString());
        this.affiliation.setWrapText(true);
        this.flagshipStatus=new Label(team.isStateFlagship()? "Yes": "No");
        this.enrollment=new Label(Integer.toString(team.getEnrollment()));
        this.city=new Label(String.format("%s (%s)",team.getCityName()
                            ,Integer.toString(team.getcity().getPopulation())));            
        this.city.setWrapText(true);
        this.state=new Label(String.format("%s (%s)",team.getStateName()
                            ,Integer.toString(team.getstate().getPopulation())));            
        this.state.setWrapText(true);
        this.state.setPrefWidth(capwidth);
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
        addColoredHelmetLayer("facemask_back",colors.getSecondary());
        addHelmetLayer("shell_back");
        addColoredHelmetLayer("shell_front",colors.getPrimary());
        addHelmetLayer("straps");
        addColoredHelmetLayer("facemask_front",colors.getSecondary());
        }        
    }
