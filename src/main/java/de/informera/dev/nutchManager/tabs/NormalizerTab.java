package de.informera.dev.nutchManager.tabs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.vaadin.ui.Button;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.informera.dev.nutchManager.fileOperations.SecureFile;

@SuppressWarnings("serial")
public class NormalizerTab extends VerticalLayout {
	public NormalizerTab(String path) {
		this.setHeight("100%");
		final String finalPath = path;
		final TextArea regExFilterEditor = new TextArea();
		this.addComponent( regExFilterEditor );
		this.setExpandRatio(regExFilterEditor, 1.0f);
		regExFilterEditor.setSizeFull();
		
		try {
			String content = FileUtils.readFileToString( new File(path) );
			regExFilterEditor.setValue( content );
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
				if( sf.saveStringBackupFile( new File(finalPath), regExFilterEditor.getValue().toString() ) ) {
					try {	
						String content = FileUtils.readFileToString( new File(finalPath) );
						regExFilterEditor.setValue( content );
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
