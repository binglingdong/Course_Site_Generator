/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import csg.data.Lab;
import jtps.jTPS_Transaction;

/**
 *
 * @author bingling.dong
 */
public class MT_EditLab_Transaction implements jTPS_Transaction {

    String oldValue;
    String newValue;
    Lab lab;
    String col;
    
    public MT_EditLab_Transaction(String oldValue, String newValue, Lab lab, String col){
        this.oldValue= oldValue;
        this.newValue= newValue;
        this.lab = lab;
        this.col = col;
    }
    
    @Override
    public void doTransaction() {
        switch(col){
            case "section":lab.setSection(newValue);
                break;
            case "day":lab.setDayAndTime(newValue);
                break;
            case "room":lab.setRoom(newValue);
                break;
            case "ta1":lab.setTA1(newValue);
                break;
            case "ta2":lab.setTA2(newValue);
                break;
        }
    }

    @Override
    public void undoTransaction() {
        switch(col){
            case "section":lab.setSection(oldValue);
                break;
            case "day":lab.setDayAndTime(oldValue);
                break;
            case "room":lab.setRoom(oldValue);
                break;
            case "ta1":lab.setTA1(oldValue);
                break;
            case "ta2":lab.setTA2(oldValue);
                break;
        }
    }
    
}

    
