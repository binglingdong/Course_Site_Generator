/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author bingling.dong
 */
public class Recitation {
    private StringProperty section;
    private StringProperty dayAndTime;
    private StringProperty room;
    private StringProperty TA1;
    private StringProperty TA2;
    
    public Recitation(String sec, String time, String room, TeachingAssistantPrototype TA1, TeachingAssistantPrototype TA2){
        section = new SimpleStringProperty(sec);
        this.dayAndTime= new SimpleStringProperty(time);
        this.room= new SimpleStringProperty(room);
        this.TA1= new SimpleStringProperty(TA1.getName());
        this.TA2= new SimpleStringProperty(TA2.getName());
    }
    
    public String getSection() {
        return section.get();
    }

    public void setSection(String initSection) {
        section.set(initSection);
    }
    
    public StringProperty sectionProperty() {
        return section;
    }
    
    public String getDayAndTime() {
        return dayAndTime.get();
    }

    public void setDayAndTime(String initDayAndTime) {
        dayAndTime.set(initDayAndTime);
    }
    
    public StringProperty dayAndTimeProperty() {
        return dayAndTime;
    }
    
    public String getRoom() {
        return room.get();
    }

    public void setRoom(String initRoom) {
        room.set(initRoom);
    }
    
    public StringProperty roomProperty() {
        return room;
    }
    
    public String getTA1() {
        return TA1.get();
    }

    public void setTA1(String initTA1) {
        TA1.set(initTA1);
    }
    
    public StringProperty TA1Property() {
        return TA1;
    }
    
     public String getTA2() {
        return TA2.get();
    }

    public void setTA2(String initTA2) {
        TA2.set(initTA2);
    }
    
    public StringProperty TA2Property() {
        return TA2;
    }
}
