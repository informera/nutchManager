package de.informera.dev.nutchManager.tabs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import de.informera.dev.nutchManager.fileOperations.SecureFile;

@SuppressWarnings("serial")
public class NutchSiteXmlTab extends VerticalLayout {

	public NutchSiteXmlTab(String path) {
		final String finalPath = path;
		final TextArea nutchSiteEditor = new TextArea();
		this.addComponent( nutchSiteEditor );
		nutchSiteEditor.setSizeFull();
		nutchSiteEditor.setHeight("420px");
		
		try {
			String content = FileUtils.readFileToString( new File(path) );
			nutchSiteEditor.setValue( content );
		} catch (FileNotFoundException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		}
		
		Button saveButton = new Button("Save");
		this.addComponent( saveButton );
		
		saveButton.addListener( new ClickListener() {
			public void buttonClick(ClickEvent event) {
				SecureFile sf = new SecureFile();
				if( sf.saveStringBackupFile( new File(finalPath), nutchSiteEditor.getValue().toString() ) ) {
					try {	
						String content = FileUtils.readFileToString( new File(finalPath) );
						nutchSiteEditor.setValue( content );
					} catch (FileNotFoundException e) {
						System.err.println(e);
					} catch (IOException e) {
						System.err.println(e);
					}
				}else{
					System.err.println("ERROR saving file!");
				}
			}
		} );
	}
}
