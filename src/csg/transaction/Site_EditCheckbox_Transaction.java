/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import javafx.scene.control.CheckBox;
import jtps.jTPS_Transaction;

/**
 *
 * @author bingling.dong
 */
public class Site_EditCheckbox_Transaction implements jTPS_Transaction{
    boolean oldValue;
    boolean newValue;
    CheckBox cb;
    
    public Site_EditCheckbox_Transaction(boolean oldValue,boolean newValue,CheckBox cb){
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.cb = cb;
    }
    @Override
    public void doTransaction() {
        cb.setSelected(newValue);
    }

    @Override
    public void undoTransaction() {
        cb.setSelected(oldValue);
    }
    
}
