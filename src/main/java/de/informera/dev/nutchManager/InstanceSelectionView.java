package de.informera.dev.nutchManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.terminal.ClassResource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
public class InstanceSelectionView extends GridLayout {

	private List<String> instances = new ArrayList<String>();
	
	public InstanceSelectionView() {
		
		// Load configuration for instances
		File settings_folder = new File( System.getProperty("user.home") + "/.nutchManager/instances/" );
		
		this.setColumns( 2 );
		this.setRows( settings_folder.list().length );
		
		for(int item=0; item < settings_folder.list().length; item++){
			try {
				FileReader fr = new FileReader( settings_folder.listFiles()[item].getPath() );
				BufferedReader br = new BufferedReader(fr);
				instances.add( br.readLine() );
				br.close();
				fr.close();
			} catch(IOException e) {
				System.err.println(e);
			}
		}

		// Display instance selection
		int row = 0;
		for (final String name : instances) {
			final int frow = row;
			Button instButton = new Button(name);
			Button instDelButton = new Button();
			instDelButton.setIcon( new ThemeResource( "images/fire-icon.png" ) );
			final GridLayout that = this;
			
			instButton.addListener( new ClickListener() {
				public void buttonClick(ClickEvent event) {
					getApplication().getMainWindow().addWindow( new InstanceProperties(name) );
				}
			} );
			
			instDelButton.addListener( new ClickListener() {
				public void buttonClick(ClickEvent event) {
					if( new File(System.getProperty("user.home") + "/.nutchManager/instances/" + name + ".settings").delete() ) {
						that.removeComponent(0, frow);
						that.removeComponent(1, frow);
						System.out.println("Deleted.");
					}else{
						System.err.println("Fail!");
					}
				}
			} );
			
			this.addComponent(instButton, 0, row);
			this.addComponent(instDelButton, 1, row);
			row++;
		}

		// When clicked on instance load instance view
	}
}
