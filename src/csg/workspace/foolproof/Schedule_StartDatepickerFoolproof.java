/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace.foolproof;

import csg.CourseSiteGeneratorApp;
import static csg.SchedulePropertyType.CALENDAR_BOUNDARIES_ENDING_DATEPICKER;
import static csg.SchedulePropertyType.CALENDAR_BOUNDARIES_STARTING_DATEPICKER;
import djf.ui.foolproof.FoolproofDesign;
import java.time.DayOfWeek;
import java.time.LocalDate;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;

/**
 *
 * @author bingling.dong
 */
public class Schedule_StartDatepickerFoolproof implements FoolproofDesign{
  
    CourseSiteGeneratorApp app;
    
    public Schedule_StartDatepickerFoolproof(CourseSiteGeneratorApp app){
        this.app = app;
    }
    
    @Override
    public void updateControls() {
        enableIfInUse();
    }
    
    private void enableIfInUse() {
        DatePicker startingDate = (DatePicker) app.getGUIModule().getGUINode(CALENDAR_BOUNDARIES_STARTING_DATEPICKER);
        DatePicker endingDate = (DatePicker) app.getGUIModule().getGUINode(CALENDAR_BOUNDARIES_ENDING_DATEPICKER);
        startingDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.getDayOfWeek() == DayOfWeek.TUESDAY            //THIS is for initialize
                        ||date.getDayOfWeek() == DayOfWeek.WEDNESDAY
                        ||date.getDayOfWeek() == DayOfWeek.THURSDAY
                        ||date.getDayOfWeek() == DayOfWeek.FRIDAY
                        ||date.getDayOfWeek() == DayOfWeek.SATURDAY
                        ||date.getDayOfWeek() == DayOfWeek.SUNDAY);
                if(endingDate.getValue()!=null){
                    setDisable(empty || date.getDayOfWeek() == DayOfWeek.TUESDAY        // THIs is for updating 
                        ||date.getDayOfWeek() == DayOfWeek.WEDNESDAY
                        ||date.getDayOfWeek() == DayOfWeek.THURSDAY
                        ||date.getDayOfWeek() == DayOfWeek.FRIDAY
                        ||date.getDayOfWeek() == DayOfWeek.SATURDAY
                        ||date.getDayOfWeek() == DayOfWeek.SUNDAY
                        ||date.compareTo(endingDate.getValue()) > 0);
                }    
            }
        });
        
    }
}
