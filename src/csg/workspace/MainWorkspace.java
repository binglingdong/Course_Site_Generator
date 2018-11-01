/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import djf.components.AppWorkspaceComponent;
import csg.CourseSiteGeneratorApp;
import static djf.modules.AppGUIModule.ENABLED;
import djf.ui.AppNodesBuilder;
import javafx.scene.control.TabPane;
import properties_manager.PropertiesManager;
import static csg.workspace.style.Style.*;
import static csg.CourseSiteGeneratorPropertyType.*;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

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
        // FIRST LOAD THE FONT FAMILIES FOR THE COMBO BOX
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // THIS WILL BUILD ALL OF OUR JavaFX COMPONENTS FOR US
        AppNodesBuilder csgBuilder = app.getGUIModule().getNodesBuilder();
        
        TabPane mainPane = csgBuilder.buildTabPane(TAB_PANE, null, CLASS_MAIN_PANE, ENABLED);
        
        Tab SiteTab= csgBuilder.buildTab(TAB_SITE,mainPane,CLASS_TABS, ENABLED);
        Tab SyllabusTab= csgBuilder.buildTab(TAB_SYLLABUS,mainPane,CLASS_TABS, ENABLED);
        Tab MTTab= csgBuilder.buildTab(TAB_MEETING_TIMES,mainPane,CLASS_TABS, ENABLED);
        Tab OHTab= csgBuilder.buildTab(TAB_OFFICE_HOURS,mainPane,CLASS_TABS, ENABLED);
        Tab ScheduleTab= csgBuilder.buildTab(TAB_SCHEDULE,mainPane,CLASS_TABS, ENABLED);
        
        SiteTab.setClosable(false);
        SyllabusTab.setClosable(false);
        MTTab.setClosable(false);
        OHTab.setClosable(false);
        ScheduleTab.setClosable(false);
        
        
        workspace = new BorderPane();
        // AND PUT EVERYTHING IN THE WORKSPACE
        ((BorderPane)workspace).setCenter(mainPane);
        mainPane.tabMinWidthProperty().bind((workspace.widthProperty().divide(mainPane.getTabs().size()).subtract(20)));
        MeetingTimes mt= new MeetingTimes(MTTab, (CourseSiteGeneratorApp) app);
        Site site= new Site(SiteTab, (CourseSiteGeneratorApp) app);
        OfficeHours oh= new OfficeHours(OHTab, (CourseSiteGeneratorApp) app);
        Syllabus syl= new Syllabus(SyllabusTab, (CourseSiteGeneratorApp) app);
        Schedule sch= new Schedule(ScheduleTab, (CourseSiteGeneratorApp) app);
        
        mt.initLayout();
        site.initLayout();
        oh.initLayout();
        syl.initLayout();
        sch.initLayout();
    }

    private void initControllers() {
    }

    private void initFoolproofDesign() {
    }
}
