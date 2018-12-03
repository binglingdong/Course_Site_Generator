/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import javafx.scene.control.TextArea;
import jtps.jTPS_Transaction;

/**
 *
 * @author bingling.dong
 */
public class All_EditTextArea_Transaction implements jTPS_Transaction{
    String oldValue;
    String newValue;
    TextArea ta;
    
    public All_EditTextArea_Transaction(String oldValue, String newValue, TextArea ta){
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.ta = ta;
    }
    
    @Override
    public void doTransaction() {
        if(newValue==null){
            ta.clear();
        }
        else{
            ta.setText(newValue);
        }
    }

    @Override
    public void undoTransaction() {
        if(oldValue ==null){
            ta.clear();
        }else{
            ta.setText(oldValue);
        }
    }
    
    public String getOldValue(){
        return oldValue;
    }
    
    public TextArea getTA(){
        return ta;
    }
}
