package global;

import java.util.Collection;
import java.util.Date;

import javax.swing.JOptionPane;

public class Logger {

	/**
	 * 运行结束报告
	 * */
	public static void endReport() {
		Collection<String> errorList = Global.getErrorList();
		if (errorList != null) {
			Object[] msgs = errorList.toArray();
			int len = msgs.length;
			String str = "";
			for (int i = 0; i < len; i++) {
				str += msgs[i].toString() + "\n";
				if (i > 20) {
					str += "...";
					break;
				}
			}
			long time = (new Date().getTime() - Global.startTime.getTime()) / 1000;
			int hour = (int) (time / 3600);
			int minutes = (int) (time % 3600 / 60);
			int seconds = (int) (time % 60);
			String msg = "本次共处理 耗时" + hour + "小时" + minutes + "分钟" + seconds
					+ "秒  共 " + (int)Global.totalCount + " 张照片\n" + "成功 "
					+ (int)(Global.totalCount - len) + " 张\n";
			if (len > 0) {
				msg += "失败 " + len + " 张\n" + str;
			}
			JOptionPane.showMessageDialog(null, msg);
		}
	}

	public static synchronized void showStateMsg(String msg) {
		Global.txtState.setText(msg);
	}

	public static synchronized void showProgress(int cusProgress) {
		Global.stateBar.setValue(cusProgress);
	}
}
