/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

/**
 *
 * @author bingling.dong
 */
public class Lecture {
    private String section;
    private String days;
    private String time;
    private String room;
    
    public Lecture(String sec, String day, String time, String room){
        section = sec;
        this.days= day;
        this.time= time;
        this.room= room;
    }
}
