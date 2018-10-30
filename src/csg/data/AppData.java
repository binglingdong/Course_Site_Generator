/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import djf.components.AppDataComponent;
import csg.CourseSiteGeneratorApp;

/**
 *
 * @author bingling.dong
 */
public class AppData implements AppDataComponent{
    CourseSiteGeneratorApp App;
    public AppData(CourseSiteGeneratorApp App){
        this.App= App;
    }
    @Override
    public void reset() {
    }
    
}
