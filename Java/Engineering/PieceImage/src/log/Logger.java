package log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;

public class Logger {

	private ArrayList<String> gLogList = new ArrayList<String>();

	private boolean gHasLog;
	
	private boolean createSuccess;
	
	private File logFile;

	private FileWriter writer;
	
	
	public void write(String cusMsg) throws Exception
	{
		gLogList.add(cusMsg);
		if(gHasLog == false)
		{
			if(createSuccess == false)
			{
				if(logFile == null)
				{
					createLogFile();
				}
			}
			return;
		}
		writeLog();
	}
	
	private void createLogFile() throws Exception
	{
		String path = "/log/"+Calendar.YEAR+"-"+Calendar.MONTH+"-0"+Calendar.DATE+".log";
		logFile = new File(path);
		if(logFile.exists() == false)
		{
			if(logFile.createNewFile() == true)
			{
				gHasLog = true;
				createSuccess = true;
			}
			else
			{
				JOptionPane.showMessageDialog(null, "创建Log文件按败");
			}
			gHasLog = false;
		}
		gHasLog = true;
	}
	
	private void writeLog() throws Exception
	{
		int len = gLogList.size();
		for(int i=0; i<len; i++)
		{
			String msg = gLogList.remove(i);
			if(writer == null)
			{
				writer = new FileWriter(logFile);
			}
			writer.append(msg);
		}
		writer.flush();
		writer.close();
	}
}
