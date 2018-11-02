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
public class Lab {
    private String section;
    private String dayAndTime;
    private String room;
    private TeachingAssistantPrototype TA1;
    private TeachingAssistantPrototype TA2;
    
    public Lab(String sec, String time, String room, TeachingAssistantPrototype TA1, TeachingAssistantPrototype TA2){
        section= sec;
        this.dayAndTime= time;
        this.room= room;
        this.TA1= TA1;
        this.TA2= TA2;
    }
}
