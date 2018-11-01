/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CourseSiteGeneratorApp;
import static csg.SchedulePropertyType.*;
import static csg.workspace.style.Style.*;
import static djf.modules.AppGUIModule.ENABLED;
import djf.ui.AppNodesBuilder;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

/**
 *
 * @author bingling.dong
 */
public class Schedule {
    private Tab ScheduleTab; 
    CourseSiteGeneratorApp app;
    
    public Schedule(Tab tab, CourseSiteGeneratorApp app){
        ScheduleTab= tab;
        this.app = app;
    }
    
    public void initLayout(){
        AppNodesBuilder csgBuilder = app.getGUIModule().getNodesBuilder();
        VBox mainPane=  csgBuilder.buildVBox(SCHEDULE_PANE, null, CLASS_PANES_BACKGROUND, ENABLED);
        
      
    }
}
