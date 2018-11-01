/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CourseSiteGeneratorApp;
import javafx.scene.control.Tab;

/**
 *
 * @author bingling.dong
 */
public class MeetingTimes {
    private Tab MTTab; 
    CourseSiteGeneratorApp app;
    
    public MeetingTimes(Tab tab, CourseSiteGeneratorApp app){
        MTTab= tab;
        this.app = app;
    }
    
    public void initLayout(){
        
    }
    
}
