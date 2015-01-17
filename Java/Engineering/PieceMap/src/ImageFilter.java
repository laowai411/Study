import java.io.File;
import java.io.FileFilter;

public class ImageFilter implements FileFilter {

	private boolean isJpg(String fileName) {
		return fileName.toLowerCase().endsWith(".jpg");
	}

	private boolean isPng(String fileName) {
		return fileName.toLowerCase().endsWith(".png");
	}

	@Override
	public boolean accept(File pathname) {
		return isJpg(pathname.getName()) || isPng(pathname.getName());
	}

}
