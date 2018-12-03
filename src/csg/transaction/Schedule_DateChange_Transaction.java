/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import java.time.LocalDate;
import javafx.scene.control.DatePicker;
import jtps.jTPS_Transaction;

/**
 *
 * @author bingling.dong
 */
public class Schedule_DateChange_Transaction implements jTPS_Transaction{
    LocalDate oldValue;
    LocalDate newValue;
    DatePicker dp;
    
    public Schedule_DateChange_Transaction(LocalDate oldValue, LocalDate newValue, DatePicker dp){
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.dp = dp;
    }
    
    @Override
    public void doTransaction() {
        dp.setValue(newValue);
    }

    @Override
    public void undoTransaction() {
        dp.setValue(oldValue);
    }
}
