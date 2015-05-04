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

	/**
	 * 检测是否为一个目录
	 * */
	public static boolean checkIsDir(String cusURL) {
		File file = new File(cusURL);
		if (file == null || file.exists() == false
				|| file.isDirectory() == false || file.canRead() == false) {
			return false;
		}
		return true;
	}

	/**
	 * 检测是否为一个excel
	 * */
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

	/**
	 * 获取文件后缀名
	 * */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/**
	 * 检测是否为一个jpg或者png图片
	 * */
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
