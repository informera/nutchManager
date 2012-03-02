package de.informera.dev.nutchManager.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

import de.informera.dev.nutchManager.fileOperations.UrlFilterTest;

@SuppressWarnings("serial")
public class UrlCheckerWindow extends Window {
	public UrlCheckerWindow(String instanceName) {
		this.setModal(true);
		this.setCaption("Check RegEx URL Filter");
		this.setWidth("500px");
		this.setHeight("150px");
		
		HorizontalLayout container = new HorizontalLayout();
		
		String name = "";
		String nutchHome = "";
		String seedsPath = "";
		
		//Resolving paths from cfg
		try {
			FileReader fr = new FileReader( new File( System.getProperty("user.home") + "/.nutchManager/instances/" + instanceName + ".settings" ) );
			BufferedReader br = new BufferedReader(fr);
			
			name = br.readLine();
			nutchHome = br.readLine();
			seedsPath = br.readLine();
			
		} catch (FileNotFoundException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		}
		
		
		
		final TextField url = new TextField();
		url.setWidth("400px");
		final Window that = this;
		final Label result = new Label();
		result.setContentMode(Label.CONTENT_XHTML);
		
		url.setInputPrompt("URL");
		

		final String fnutchHome = nutchHome;
		
		Button check = new Button("Check");
		check.addListener( new ClickListener() {
			public void buttonClick(ClickEvent event) {
				
				UrlFilterTest bla = new UrlFilterTest( new File(fnutchHome + "/conf/regex-urlfilter.txt") );
				if( bla.test( url.getValue().toString() ) != null ){
					result.setValue("<span style='color:#0a0;'>URL passed</span>");
				}else{
					result.setValue("<span style='color:#a00;'>URL filtered</span>");
				}
				
			}
		});
		
		container.addComponent(url);
		container.addComponent(check);
		this.addComponent(container);
		this.addComponent(result);
	}
}
