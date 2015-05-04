package global;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class Global {

	// ///////////////////////////////////////////////////////////////////////////////////////////////////
	// 程序运行状态相关
	// /////////////////////////////////////////////////////////////////////////////////////////////////

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
	public static synchronized void setState(String cusState) {
		synchronized (state) {
			state = cusState;
		}
	}

	/**
	 * 开始时间
	 * */
	public static Date startTime;

	// ///////////////////////////////////////////////////////////////////////////////////////////////////
	// 全局需要用到的其他参数
	// /////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 照片原始目录
	 * */
	public static String srcImageURL;

	/**
	 * 路径错误的照片列表
	 * */
	private static HashMap<String, String> error_src_list = new HashMap<String, String>();

	/**
	 * 路径错误的照片
	 * */
	public static void addErrorSrcImage(String cusIndex, String cusURL) {
		error_src_list.put(cusIndex, cusURL);
	}

	/**
	 * 错误列表
	 * */
	public static Collection<String> getErrorList() {
		return error_src_list.values();
	}

	/**
	 * 本次处理照片总数量
	 * */
	public static double totalCount;

	// ///////////////////////////////////////////////////////////////////////////////////////////////////
	// 界面组件控制相关
	// /////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * excel路径文本
	 * */
	public static JTextField txtExcel;
	/**
	 * 原始照片路径
	 * */
	public static JTextField txtSrcImage;
	/**
	 * excel路径选取按钮
	 * */
	public static JButton btnExcel;
	/**
	 * 原始照片目录选取按钮
	 * */
	public static JButton btnSrcImage;
	/**
	 * excel路径文本
	 * */
	public static JButton btnPiece;
	/**
	 * 当前状态
	 * */
	public static JTextField txtState;
	/**
	 * 进度条
	 * */
	public static JProgressBar stateBar;

	/**
	 * 设置界面元件是否可操作
	 * */
	public static void setEnable(boolean cusEnable) {
		txtExcel.setEnabled(cusEnable);
		txtSrcImage.setEnabled(cusEnable);
		btnExcel.setEnabled(cusEnable);
		btnSrcImage.setEnabled(cusEnable);
		btnPiece.setEnabled(cusEnable);
	}

}
