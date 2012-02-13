package de.informera.dev.nutchManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Window;

import de.informera.dev.nutchManager.tabs.FilterTab;
import de.informera.dev.nutchManager.tabs.NormalizerTab;
import de.informera.dev.nutchManager.tabs.NutchSiteXmlTab;
import de.informera.dev.nutchManager.tabs.SeedsTab;

@SuppressWarnings("serial")
public class InstanceProperties extends Window {
	public InstanceProperties(String instanceName) {
		this.setWidth("800px");
		this.setHeight("600px");
		this.center();
		this.setCaption(instanceName);
		
		TabSheet tabView = new TabSheet();
		tabView.setWidth("100%");
		tabView.setHeight("100%");
		
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
		
		// Constructing instance tabs
		NutchSiteXmlTab nutchSiteXmlTab = new NutchSiteXmlTab(nutchHome + "/conf/nutch-site.xml");
		SeedsTab seedsTab = new SeedsTab(seedsPath);
		FilterTab filterTab = new FilterTab(nutchHome + "/conf/regex-urlfilter.txt");
		NormalizerTab normalizerTab = new NormalizerTab(nutchHome + "/conf/regex-normalize.xml");
		
		// Adding tabs
		tabView.addTab(nutchSiteXmlTab);
		tabView.getTab(nutchSiteXmlTab).setCaption("nutch-site.xml");
		
		tabView.addTab(seedsTab);
		tabView.getTab(seedsTab).setCaption("seeds");
		
		tabView.addTab(filterTab);
		tabView.getTab(filterTab).setCaption("regEx filter");
		
		tabView.addTab(normalizerTab);
		tabView.getTab(normalizerTab).setCaption("regEx normalizer");
		
		this.setContent(tabView);
		this.getContent().setSizeFull();
	}
}
