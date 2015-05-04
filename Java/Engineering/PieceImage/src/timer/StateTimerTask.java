package timer;

import global.Global;
import image.ImageCreater;

import java.util.Date;
import java.util.TimerTask;

import javax.swing.JOptionPane;

/**
 * 主线程的时间回调执行逻辑
 * */
public class StateTimerTask extends TimerTask {

	@Override
	public void run() {
		if (Global.getState() == Global.STATE_ERROR) {
			cancel();
            ImageCreater.stopAll();
            System.out.println(new Date().toString());
            Global.setEnable(true);
            JOptionPane.showMessageDialog(null, "合成失败");
        }
        else if(Global.getState() == Global.STATE_SUCCESS)
        {
        	cancel();
            ImageCreater.stopAll();
            System.out.println(new Date().toString());
            Global.setEnable(true);
            JOptionPane.showMessageDialog(null, "合成结束");
        }
	}

}
