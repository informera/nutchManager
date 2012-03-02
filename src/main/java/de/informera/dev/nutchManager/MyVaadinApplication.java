/*
 * Copyright 2009 IT Mill Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.informera.dev.nutchManager;

import java.io.File;

import com.vaadin.Application;
import com.vaadin.terminal.ClassResource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

import de.informera.dev.nutchManager.fileOperations.UrlFilterTest;
import de.informera.dev.nutchManager.helpers.UrlCheckerWindow;
import de.informera.dev.nutchManager.sysGui.AddInstanceDialog;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class MyVaadinApplication extends Application
{
	private Window window;


	@Override
	public void init()
	{
		File settings_folder = new File( System.getProperty("user.home") + "/.nutchManager" );
		if( !settings_folder.isDirectory() ){
			settings_folder.mkdir();
			new File(settings_folder.getAbsolutePath() + "/backups").mkdir();
			new File(settings_folder.getAbsolutePath() + "/instances").mkdir();
		}
		
		window = new Window("nutchManager");
		setMainWindow(window);
		this.setTheme("nutchManager");

		final HorizontalLayout technologies = new HorizontalLayout();
		technologies.setSpacing(true);
		technologies.addComponent( new Embedded( "", new ThemeResource("images/nutch_logo_tm.gif") ) );
		technologies.addComponent( new Embedded( "", new ThemeResource("images/vaadin.png") ) );
		
		final MenuBar mainMenu = new MenuBar();

		mainMenu.setWidth("100%");
		MenuItem menuFile = mainMenu.addItem("File", null, null);
		MenuItem subMenuFile = menuFile.addItem("Add new instance", null, null);

		// Define a common menu command for all the menu items.
		MenuBar.Command addNewInstanceFromMenu = new MenuBar.Command() {
		    public void menuSelected(MenuItem selectedItem) {
		        window.removeAllComponents();
		        window.addComponent( technologies );
		        window.addComponent( mainMenu );
		        window.addWindow( new AddInstanceDialog() );
		    }  
		};
		
		subMenuFile.setCommand( addNewInstanceFromMenu );
		
		window.addComponent( technologies );
		
		window.addComponent(mainMenu);

		// Begin the checkout of an old configuration
		// Check waether configuration has entries for nutch instances or not
//		File settings_folder = new File( this.getClass().getClassLoader().getResource("/instance_conf").getPath() );
		
		File instances_folder = new File( settings_folder + "/instances" );
		
		// If not: prompt to add an instance
		if(instances_folder.list().length < 1){
			window.showNotification("ERROR", "No previous configuration found. Please add a nutch instance.", Notification.TYPE_ERROR_MESSAGE);
			Window addInstance = new AddInstanceDialog();
			window.addWindow(addInstance);
		}else{
			window.addComponent( new InstanceSelectionView() );
		}

	}

}
