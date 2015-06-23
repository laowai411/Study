package image;

import excel.ExcelInfoVo;
import excel.ExcelParser;
import global.Global;
import global.Logger;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.sun.image.codec.jpeg.ImageFormatException;
import common.FileUtil;
import common.ImageUtil;

public class ImageRenamer extends Thread {

	/**
	 * 当前运行的线程
	 * */
	private static HashMap<String, ImageRenamer> useMap = new HashMap<String, ImageRenamer>();

	/**
	 * 当前运行的线程数量
	 * */
	private static int instanceCount = 0;

	/**
	 * 线程id自增涨编号
	 * */
	private static long id;

	/**
	 * 开启线程
	 * */
	public static void startThread() {
		synchronized (useMap) {
			if (Global.getState() == Global.STATE_ERROR
					|| Global.getState() == Global.STATE_SUCCESS) {
				return;
			}
			for (int i = 0; i < 10; i++) {
				if (instanceCount >= 10) {
					return;
				}
				ExcelInfoVo excelInfoVo = ExcelParser.getLastExcelInfoVo();
				if (excelInfoVo != null) {
					if (FileUtil.checkIsImage(excelInfoVo.image_url) == false) {
						Global.addErrorSrcImage(excelInfoVo.index,
								excelInfoVo.image_url);
						i--;
						continue;
					}
					ImageRenamer creater = new ImageRenamer();
					id++;
					creater.init(excelInfoVo, id + "");
					creater.start();
					useMap.put(id + "", creater);
					instanceCount++;
					System.out.println(Calendar.getInstance().getTimeInMillis()
							+ "  启动线程" + id + "  当前共有线程数：" + instanceCount);
				} else {
					Global.setState(Global.STATE_SUCCESS);
					stopAll();
					Logger.showProgress(100);
					Logger.showStateMsg("结束");
				}
			}
		}
	}

	/**
	 * 停止所有线程
	 * */
	public static void stopAll() {
		synchronized (useMap) {
			if (useMap.size() > 0) {
				Iterator<ImageRenamer> ite = useMap.values().iterator();
				while (ite.hasNext()) {
					ImageRenamer creater = ite.next();
					creater.stopSelfNoRemove();
					creater = null;
					ite.remove();
				}
			}
			instanceCount = 0;
		}
	}

	/**
	 * 单条excel配置数据
	 * */
	private ExcelInfoVo excelInfoVo;
	/**
	 * 当前线程id
	 * */
	private String gId;

	private void init(ExcelInfoVo cusExcelInfoVo, String cusId) {
		excelInfoVo = cusExcelInfoVo;
		gId = cusId;
	}

	private void stopSelfNoRemove() {
		interrupt();
		if (checkEnd()) {
			return;
		}
		instanceCount--;
		System.out.println(gId + " 线程停止");
		ImageCreater.startThread();
	}

	/**
	 * excel读取到的数据是否已经处理完了
	 * */
	private boolean checkEnd() {
		if (ExcelParser.getOddExcelInfoVoCount() < 1 && useMap.size() < 1) {
			Global.setState(Global.STATE_SUCCESS);
			return true;
		}
		return false;
	}

	/**
	 * 停止当前子线程
	 * */
	private void stopSelf() {
		interrupt();
		if (useMap.containsValue(this)) {
			useMap.remove(gId);
		}
		if (checkEnd()) {
			return;
		}
		instanceCount--;
		System.out.println(gId + " 线程停止");
		ImageCreater.startThread();
	}

	@Override
	public void run() {
		double percent = ((Global.totalCount - ExcelParser
				.getOddExcelInfoVoCount()) / Global.totalCount) * 0.9;
		Logger.showProgress((int) (10 + percent * 100));
		Logger.showStateMsg("剩余" + (ExcelParser.getOddExcelInfoVoCount()));
		File file = new File(excelInfoVo.image_url);
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
			BufferedImage targetImage = ImageUtil.scaleImg(image,
					Integer.parseInt(Global.txtW.getText()),
					Integer.parseInt(Global.txtH.getText()), Color.BLACK);
			ImageUtil.markImageByText(excelInfoVo.src_image_name, targetImage, null, (10), (targetImage.getHeight()-10), 1.0f, 20);
			float qua = Float.parseFloat(Global.txtQua.getText())/100;
			FileUtil.writeImage(
					Global.targetImageURL + excelInfoVo.id + ".jpg",
					targetImage, qua);
		} catch (ImageFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.stopSelf();
			JOptionPane.showMessageDialog(null, "生成的图片格式错误");
			return;
		}  catch (IOException ioE) {
			ioE.printStackTrace();
			JOptionPane.showMessageDialog(null, "读取原始照片失败");
			this.stopSelf();
			return;
		}
		this.stopSelf();
	}

}
