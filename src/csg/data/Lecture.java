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
public class Lecture {
    private StringProperty section;
    private StringProperty days;
    private StringProperty time;
    private StringProperty room;
    
    public Lecture(String sec, String day, String time, String room){
        section = new SimpleStringProperty(sec);
        this.days = new SimpleStringProperty(day);
        this.time= new SimpleStringProperty(time);
        this.room= new SimpleStringProperty(room);
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
    
    public String getDays() {
        return days.get();
    }

    public void setDays(String initDays) {
        days.set(initDays);
    }
    
    public StringProperty daysProperty() {
        return days;
    }
    
    public String getTime() {
        return time.get();
    }

    public void setTime(String initTime) {
        time.set(initTime);
    }
    
    public StringProperty timeProperty() {
        return time;
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
    
    
}
