/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fileUtil;

import java.io.File;

/**
 * 
 * @author AngryPotato
 */
public class FileUtil {

	public static boolean checkIsDir(String cusURL) {
		File file = new File(cusURL);
		if (file == null || file.exists() == false
				|| file.isDirectory() == false || file.canRead() == false) {
			return false;
		}
		return true;
	}

	public static boolean checkIsExcel(String cusURL) {
		File file = new File(cusURL);
		if (file == null || file.exists() == false || file.isFile() == false
				|| file.canRead() == false) {
			return false;
		}
		if (file.getName().toLowerCase().endsWith(".xls")
				|| file.getName().toLowerCase().endsWith(".xlsx")) {
			return true;
		}
		return true;
	}

	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	public static boolean checkIsImage(String cusURL) {
		File file = new File(cusURL);
		if (file == null || file.exists() == false || file.isFile() == false
				|| file.canRead() == false) {
			return false;
		}
		if (file.getName().toLowerCase().endsWith(".jpg")
				|| file.getName().toLowerCase().endsWith(".png")) {
			return true;
		}
		return true;
	}
}
