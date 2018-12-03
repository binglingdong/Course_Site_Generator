/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import csg.data.Lecture;
import jtps.jTPS_Transaction;

/**
 *
 * @author bingling.dong
 */
public class MT_EditLecture_Transaction implements jTPS_Transaction {
    String oldValue;
    String newValue;
    Lecture lec;
    String col;
    
    public MT_EditLecture_Transaction(String oldValue, String newValue,Lecture lec, String col){
        this.oldValue= oldValue;
        this.newValue= newValue;
        this.lec = lec;
        this.col = col;
    }
    
    @Override
    public void doTransaction() {
        switch(col){
            case "section":lec.setSection(newValue);
                break;
            case "days":lec.setDays(newValue);
                break;
            case "time":lec.setTime(newValue);
                break;
            case "room":lec.setRoom(newValue);
                break;
        }
    }

    @Override
    public void undoTransaction() {
        switch(col){
            case "section":lec.setSection(oldValue);
                break;
            case "days":lec.setDays(oldValue);
                break;
            case "time":lec.setTime(oldValue);
                break;
            case "room":lec.setRoom(oldValue);
                break;
        }
    }
    
}
