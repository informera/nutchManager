package de.informera.dev.nutchManager.fileOperations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class SecureFile {
	public Boolean saveStringBackupFile(File destination, String content) {
		//Backup erstellen
		File bkp = new File( System.getProperty("user.home") + "/.nutchManager/backups/" + destination.getName() + "_" + System.currentTimeMillis() );
		
		try {
			FileReader originalFile = new FileReader(destination);
			FileWriter backupFile = new FileWriter( bkp );
			
			int ch = 0;
			while( (ch = originalFile.read()) != -1 ){
				backupFile.write(ch);
			}
			backupFile.flush();
			backupFile.close();
			originalFile.close();
			
			//Checking if files are equal
			if( destination.length() != bkp.length() ) {
				System.err.println("File could not be backed up! (" + destination.length() + ":" + bkp.length() + ")");
				return false;
			}
			
			//Saving new Contents to File
			FileUtils.writeStringToFile( destination , content);
//			System.out.println("Success!");
			return true;
		} catch (FileNotFoundException e) {
			System.err.println(e);
			return false;
		} catch (IOException e) {
			System.err.println(e);
			return false;
		}
		
		
//		File newFile = new File( destination.getAbsolutePath().toString() );
//		System.out.println( System.getProperty("user.home") + "/.nutchmanager/backups/" + destination.getName() + "_" + System.currentTimeMillis() );
//		if( destination.renameTo( new File( System.getProperty("user.home") + "/.nutchmanager/backups/" + destination.getName() + "_" + System.currentTimeMillis() ) ) ){
//			try {
//				FileUtils.writeStringToFile( newFile , content);
//			} catch (IOException e) {
//				System.err.println(e);
//				if( destination.renameTo( new File( destination.getAbsolutePath() ) ) ){
//					System.err.println("Backup restored!");
//				}else{
//					System.err.println("Could not restore backup from " + destination.getAbsolutePath());
//				}
//				return false;
//			}
//		}else{
//			System.err.println("File could not be backed up!");
//			return false;
//		}
//		System.out.println("Success!");
//		return true;
	}
}
