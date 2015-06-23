package common;

import java.io.File;
import java.io.IOException;

/**
 * 注册管理
 * */
public class RegisterUtil {

	/**
	 * 当前用户目录
	 * */
	private static final String HOME = "user.home";

	/**
	 * 注册文件目录名
	 * */
	private static final String KEY_FILE_DIR = "PieceImage";

	/**
	 * 注册码
	 * */
	private static final String KEY_CODE = "b072fded8a9de0f4500faebdeb261e78";

	public static boolean checkRegister() throws IOException {
		String keyDir = System.getProperties().getProperty(HOME) + "\\"
				+ KEY_FILE_DIR;
		if (FileUtil.checkIsDir(keyDir)) {
			String keyPath = keyDir + "\\key";
			File keyFile = new File(keyPath);
			if (keyFile.exists() && keyFile.canRead()) {
				String result = FileUtil.readFileByLines(keyFile
						.getAbsolutePath());
				return result.equals(KEY_CODE);
			}
		}
		return false;
	}

	public static boolean registe(String cusKeyStr) throws IOException {
		if (cusKeyStr != null && cusKeyStr.equals(KEY_CODE)) {
			String keyDir = System.getProperties().getProperty(HOME) + "\\"
					+ KEY_FILE_DIR;
			FileUtil.createFile(keyDir + "\\key");
			String[] keys = new String[] { KEY_CODE };
			FileUtil.writeFileByLine(keyDir + "\\key", keys);
			return true;
		}
		return false;
	}

}
