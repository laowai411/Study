package timer;

import global.Global;
import global.Logger;
import image.ImageCreater;

import java.util.TimerTask;

import javax.swing.JOptionPane;

public class StateTimerTask extends TimerTask {

	@Override
	public void run() {
		if (Global.getState() == Global.STATE_ERROR) {
			cancel();
            ImageCreater.stopAll();
            Global.setEnable(true);
            JOptionPane.showMessageDialog(null, "合成失败");
            Logger.endReport();
        }
        else if(Global.getState() == Global.STATE_SUCCESS)
        {
        	cancel();
            ImageCreater.stopAll();
            Global.setEnable(true);
            JOptionPane.showMessageDialog(null, "合成结束");
            Logger.endReport();
        }
	}

}
