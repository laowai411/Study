package image;

import excel.ExcelInfoVo;
import excel.ExcelParser;
import fileUtil.FileUtil;
import global.Global;
import global.Logger;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * 图片合成
 * 
 */
public class ImageCreater extends Thread {

	/**
	 * 当前运行的线程
	 * */
	private static HashMap<String, ImageCreater> useMap = new HashMap<String, ImageCreater>();
	/**
	 * 当前运行的线程数量
	 * */
	private static int instanceCount = 0;

	/**
	 * 线程id自增涨编号
	 * */
	private static long id;

	/**
	 * 开始启动至多10个线程进行处理
	 * */
	public static synchronized void startThread() {
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
					ImageCreater creater = new ImageCreater();
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
	public static synchronized void stopAll() {
		synchronized (useMap) {
			if (useMap.size() > 0) {
				Iterator<ImageCreater> ite = useMap.values().iterator();
				while (ite.hasNext()) {
					ImageCreater creater = ite.next();
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
	 * 合成结果Image
	 * */
	private BufferedImage pieceImage;
	/**
	 * 当前线程id
	 * */
	private String gId;

	private void init(ExcelInfoVo cusExcelInfoVo, String cusId) {
		excelInfoVo = cusExcelInfoVo;
		gId = cusId;
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

	public void run() {
		double percent = ((Global.totalCount - ExcelParser
				.getOddExcelInfoVoCount()) / Global.totalCount) * 0.9;
		Logger.showProgress((int) (10 + percent * 100));
		Logger.showStateMsg("剩余" + (ExcelParser.getOddExcelInfoVoCount()));
		File file = new File(excelInfoVo.image_url);
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
			pieceImage = new BufferedImage(1205, 1795,
					BufferedImage.TYPE_3BYTE_BGR);
		} catch (IOException ioE) {
			ioE.printStackTrace();
			JOptionPane.showMessageDialog(null, "读取原始照片失败");
			this.stopSelf();
			return;
		}
		try {
			if (image != null) {
				drawImage(image);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "合成照片失败");
			stopSelf();
			return;
		}
		if (image == null) {
			JOptionPane.showMessageDialog(null, "合成照片失败");
			stopSelf();
			return;
		}
		File oldFile = new File(excelInfoVo.image_url);
		String oldURL = oldFile.getAbsolutePath();
		File newImage = new File(oldURL.substring(0, oldURL.lastIndexOf("."))
				+ " 副本.jpg");
		try {
			ImageIO.write(pieceImage, "jpeg", newImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.stopSelf();
			JOptionPane.showMessageDialog(null, "写入合成后的图片失败");
			return;
		}
		this.stopSelf();
	}

	/**
	 * 绘制大图
	 * */
	private void drawImage(BufferedImage image) throws IOException {
		// 背景设置为白色
		Graphics2D graphics = pieceImage.createGraphics();
		graphics.setBackground(Color.WHITE);
		graphics.clearRect(0, 0, pieceImage.getWidth(), pieceImage.getHeight());
		// 画笔设置为黑色
		graphics.setColor(Color.BLACK);
		// 小照片(由原始照片缩放)
		BufferedImage scaleImage = null;
		scaleImage = ImageUtil.scaleImg(image, 296, 420, Color.BLACK);
		int w = scaleImage.getWidth();
		int h = scaleImage.getHeight();
		int startX = 6;
		int startY = 11;
		int tempX = startX;
		int tempY = startY;
		for (int i = 0; i < 8; i++) {
			tempX = startX + ((i % 4) * (w + 2));
			tempY = startY + ((int) (Math.ceil(i / 4))) * (h + 2);
			graphics.drawImage(scaleImage, tempX, tempY, null);
		}
		// 大照片(由原始照片缩放再旋转)
		scaleImage = ImageUtil.scaleImg(image, 372, 546, Color.BLACK);
		BufferedImage rotaImage = null;
		rotaImage = ImageUtil.rotateImg(scaleImage, 90, null);
		startX = 6;
		startY = 857;
		w = rotaImage.getWidth();
		h = rotaImage.getHeight();
		tempX = startX;
		tempY = startY;
		for (int i = 0; i < 4; i++) {
			tempX = startX + ((i % 2) * (w + 92));
			tempY = startY + ((int) (Math.ceil(i / 2))) * (h + 2);
			graphics.drawImage(rotaImage, tempX, tempY, null);
		}
		// 文字
		drawString(graphics);
		graphics.dispose();
	}

	/**
	 * Graphics渲染字符串
	 * */
	private void drawString(Graphics2D graphics) {
		Font font = new Font("黑体", Font.BOLD, 40);
		if (font.canDisplay('a') == false) {
			GraphicsEnvironment ge = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			String fontName[] = ge.getAvailableFontFamilyNames();
			font = Font.getFont(fontName[0]);
			if (font == null) {
				JOptionPane.showMessageDialog(null, "无法获取计算机的字体库");
				ImageCreater.stopAll();
				Global.setState(Global.STATE_ERROR);
				return;
			}
		}
		if (font.canDisplay('a') == true) {
			graphics.setFont(font);
			graphics.drawString(excelInfoVo.school_name, 66, 1651);
			graphics.drawString(excelInfoVo.name, 66, 1726);
			graphics.drawString(excelInfoVo.stu_id + "  "
					+ excelInfoVo.school_No, 673, 1656);
			graphics.drawString(excelInfoVo.id, 710, 1728);
			graphics.setColor(new Color(41, 97, 234));
			graphics.drawString("贵州高校信息采集组制作", 720, 1770);
		} else {
			JOptionPane.showMessageDialog(null, "无法获取计算机的字体库");
			ImageCreater.stopAll();
			Global.setState(Global.STATE_ERROR);
		}
	}
}
