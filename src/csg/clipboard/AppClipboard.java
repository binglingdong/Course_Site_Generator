/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.clipboard;

import djf.components.AppClipboardComponent;
import csg.CourseSiteGeneratorApp;

/**
 *
 * @author bingling.dong
 */
public class AppClipboard implements AppClipboardComponent {
    CourseSiteGeneratorApp app;
    
    public AppClipboard(CourseSiteGeneratorApp app){
        this.app= app;
    }
    @Override
    public void cut() {
    }

    @Override
    public void copy() {
    }

    @Override
    public void paste() {
    }

    @Override
    public boolean hasSomethingToCut() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hasSomethingToCopy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hasSomethingToPaste() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
