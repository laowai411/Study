package global;

import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class Global {

    private static String state = "no_start";
    public static String STATE_NO_START = "no_start";
    public static String STATE_LOAD_EXCEL = "load_excel";
    public static String STATE_PIECE_IMAGE = "piece_image";
    public static String STATE_ERROR = "error";
    public static String STATE_SUCCESS = "success";

    /**
     * 程序运行状态标记
     * */
    public static String getState() {
        synchronized (state) {
            return state;
        }
    }

    /**
     * 程序运行状态标记
     * */
    public static void setState(String cusState) {
        synchronized (state) {
            state = cusState;
        }
    }
    
    /**
     * excel路径文本
     * */
    public static JTextField gTxtExcel;
    /**
     * excel路径文本
     * */
    public static JTextField gTxtSave;
    /**
     * excel路径文本
     * */
    public static JButton gBtnExcel;
    /**
     * excel路径文本
     * */
    public static JButton gBtnSave;
    /**
     * excel路径文本
     * */
    public static JButton gBtnPiece;
    /**
     * 当前状态
     * */
    public static JTextField gTxtState;
    /**
     * 进度条
     * */
    public static JProgressBar gStateBar;
    /**
     * 设置界面元件是否可操作
     * */
    public static void setEnable(boolean cusEnable)
    {
    	 gTxtExcel.setEnabled(cusEnable);
//         gTxtSave.setEnabled(cusEnable);
         gBtnExcel.setEnabled(cusEnable);
//         gBtnSave.setEnabled(cusEnable);
         gBtnPiece.setEnabled(cusEnable);
    }
}
