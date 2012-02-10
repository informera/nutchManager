package de.informera.dev.nutchManager.sysGui;

import java.io.File;
import java.util.Arrays;

import javax.swing.plaf.metal.MetalIconFactory.FolderIcon16;

import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Tree.ExpandEvent;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class FileChooser extends VerticalLayout implements Tree.ExpandListener {

	private final Panel explorerPanel = new Panel();
	private final Tree tree = new Tree();
	private final File rootDir;
	private String choosenFile = null;
	private Boolean folderOnly = false;
	
	public String getChoosenFile(){
		return this.choosenFile;
	}
	
	public void setChoosenFile(String choosenFile){
		this.choosenFile = choosenFile;
	}
	
	/**
	 * 
	 * @param instanceRoot
	 * @param caption
	 * @param folder
	 * 
	 * folder == true: select only folders
	 * folder == false: select only files
	 */
	public FileChooser(String instanceRoot, String caption, Boolean folder) {
		final Boolean finalFolder = folder;
		this.folderOnly = folder;
//		this.setCaption("Choose...");
		this.setHeight("500px");
		this.setWidth("350px");
		
		explorerPanel.setCaption(caption);

		// configure file structure panel
		this.addComponent(explorerPanel);
		explorerPanel.addComponent(tree);
		explorerPanel.setHeight("400px");

		// "this" handles tree's expand event
		tree.addListener(this);

		// populate tree's root node with root directory
		rootDir = new File(instanceRoot);
		if (rootDir != null) {
			populateNode(rootDir.getAbsolutePath(), null);
		}
		tree.addListener( new ItemClickListener() {
			public void itemClick(ItemClickEvent event) {
				File tmpFile = new File( event.getItemId().toString() );
				if( !finalFolder ){
					if( !tmpFile.isDirectory() ){
						setChoosenFile( event.getItemId().toString() );
					}
				} else {
					setChoosenFile( event.getItemId().toString() );
				}
			}
		});
	}

	/**
	 * Handle tree expand event, populate expanded node's childs with new files
	 * and directories.
	 */
	public void nodeExpand(ExpandEvent event) {
		final Item i = tree.getItem(event.getItemId());
		
		if (!tree.hasChildren(i)) {
			// populate tree's node which was expanded
			populateNode(event.getItemId().toString(), event.getItemId());
		}
	}
	

	/**
	 * Populates files to tree as items. In this example items are of String
	 * type that consist of file path. New items are added to tree and item's
	 * parent and children properties are updated.
	 *
	 * @param file
	 *            path which contents are added to tree
	 * @param parent
	 *            for added nodes, if null then new nodes are added to root node
	 */
	private void populateNode(String file, Object parent) {
		final File subdir = new File(file);
		final File[] files = subdir.listFiles();
		Arrays.sort(files);
		for (int x = 0; x < files.length; x++) {
			if( !files[x].getName().toString().startsWith(".") ) {
				try {
					// add new item (String) to tree
					final String path = files[x].getCanonicalPath().toString();
					final String caption = files[x].getName().toString();
					tree.addItem(path);
					tree.setItemCaption(path, caption);
					// set parent if this item has one
					if (parent != null) {
						tree.setParent(path, parent);
					}
					if( !this.folderOnly ) {
						// check if item is a directory and read access exists
						if (files[x].isDirectory() && files[x].canRead()) {
							// yes, childrens therefore exists
							tree.setChildrenAllowed(path, true);
						} else {
							// no, childrens therefore do not exists
							tree.setChildrenAllowed(path, false);
						}
					} else {
						// check if item is a directory and read access exists
						if (files[x].isDirectory() && files[x].canRead()) {
							// yes, childrens therefore exists
							tree.setChildrenAllowed(path, true);
						}
					}
				} catch (final Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}