/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import javafx.scene.control.TextField;
import jtps.jTPS_Transaction;

/**
 *
 * @author bingling.dong
 */
public class Site_EditTextField_Transaction implements jTPS_Transaction{
    String oldValue;
    String newValue;
    TextField tf;
    
    public Site_EditTextField_Transaction(String oldValue, String newValue, TextField tf){
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.tf = tf;
    }
    @Override
    public void doTransaction() {
        tf.setText(newValue);
    }

    @Override
    public void undoTransaction() {
        tf.setText(oldValue);
    }
    
}
