package de.informera.dev.nutchManager.sysGui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

import de.informera.dev.nutchManager.InstanceSelectionView;

@SuppressWarnings("serial")
public class AddInstanceDialog extends Window {
	
	String instanceNameVal = "";
	String nutchHome = "";
	
	public AddInstanceDialog() {
		this.setCaption("Add nutch instance");
		this.setClosable(false);
		this.setModal(true);
		this.setWidth("700px");
		this.setHeight("100px");

		final Window that = this;

		final HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);
		this.addComponent(layout);
		
		final TextField instanceName = new TextField();
		Button nextStep = new Button("Next");

		nextStep.addListener( new Button.ClickListener() {	
			public void buttonClick(ClickEvent event) {
				instanceNameVal = instanceName.getValue().toString();
				try {
					if( !instanceNameVal.equals("") ){
						System.out.println( "Writing to: " + System.getProperty("user.home") + "/.nutchManager/instances/" + instanceNameVal + ".settings" );
						FileWriter fw = new FileWriter( new File( System.getProperty("user.home") + "/.nutchManager/instances/" + instanceNameVal + ".settings") , true );
						BufferedWriter bw = new BufferedWriter(fw);

						bw.write( instanceNameVal + "\n" );
						bw.close();
						fw.close();

						layout.removeAllComponents();
						
						that.setWidth("500px");
						that.setHeight("500px");
						
						final FileChooser nutchHomeChooser = new FileChooser(System.getProperty("user.home"), "Choose nutch home-directory", true);
						Button nextStep = new Button("Next"); 

						layout.addComponent(nutchHomeChooser);
						layout.addComponent(nextStep);
						
						nextStep.addListener( new Button.ClickListener() {
							public void buttonClick(ClickEvent event) {
								try {
									FileWriter fw = new FileWriter( new File( System.getProperty("user.home") + "/.nutchManager/instances/" + instanceNameVal + ".settings") , true );
									BufferedWriter bw = new BufferedWriter(fw);

									bw.write( nutchHomeChooser.getChoosenFile() + "\n" );
									nutchHome = nutchHomeChooser.getChoosenFile();
									bw.close();
									fw.close();
								} catch(IOException e){
									System.err.println(e);
								}
								layout.removeAllComponents();

								final FileChooser seedsChooser = new FileChooser(nutchHome, "Choose seeds", false);
								Button nextStep = new Button("Next"); 

								layout.addComponent(seedsChooser);
								layout.addComponent(nextStep);
								
								nextStep.addListener( new Button.ClickListener() {
									public void buttonClick(ClickEvent event) {
										try {
											FileWriter fw = new FileWriter( new File( System.getProperty("user.home") + "/.nutchManager/instances/" + instanceNameVal + ".settings") , true );
											BufferedWriter bw = new BufferedWriter(fw);

											bw.write( seedsChooser.getChoosenFile() + "\n" );
											bw.close();
											fw.close();
										} catch(IOException e){
											System.err.println(e);
										}

										getApplication().getMainWindow().addComponent( new InstanceSelectionView() );
										getApplication().getMainWindow().removeWindow(that);
									}
								} );
							}
						} );
					}else{
						getApplication().getMainWindow().showNotification("ERROR", "You have to fill in a name for the nutch instance", Notification.TYPE_ERROR_MESSAGE);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} );

		layout.addComponent( new Label("Instance Name:") );
		layout.addComponent( instanceName );
		layout.addComponent( nextStep );
	}
}
