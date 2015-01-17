import java.io.File;

import javax.swing.filechooser.FileFilter;


public class DirFilter extends FileFilter {

	@Override
	public boolean accept(File file) {
		if(file.isDirectory())
		{
			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		return "文件夹";
	}

}
