/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import djf.components.AppWorkspaceComponent;
import csg.CourseSiteGeneratorApp;

/**
 *
 * @author bingling.dong
 */
public class MainWorkspace extends AppWorkspaceComponent{
    public MainWorkspace(CourseSiteGeneratorApp app) {
        super(app);

        // LAYOUT THE APP
        initLayout();

        // INIT THE EVENT HANDLERS
        initControllers();

        // SETUP FOOLPROOF DESIGN FOR THIS APP
        initFoolproofDesign();
        
    }

    @Override
    public void showNewDialog() {
    }

    private void initLayout() {
    }

    private void initControllers() {
    }

    private void initFoolproofDesign() {
    }
}
