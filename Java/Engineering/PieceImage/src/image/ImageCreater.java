package image;

import excel.ExcelInfoVo;
import excel.ExcelParser;
import fileUtil.FileUtil;
import global.Global;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
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
	public static void startThread() {
		synchronized (useMap) {
			if (Global.getState() == Global.STATE_ERROR
					|| Global.getState() == Global.STATE_SUCCESS) {
				return;
			}
			for(int i=0; i<10; i++)
			{
				if (instanceCount >= 10) {
					return;
				}
				ExcelInfoVo excelInfoVo = ExcelParser.getLastExcelInfoVo();
				if (excelInfoVo != null) {
					ImageCreater creater = new ImageCreater();
					id++;
					creater.init(excelInfoVo, id + "");
					creater.start();
					useMap.put(id + "", creater);
					instanceCount++;
					System.out.println(Calendar.getInstance().getTimeInMillis()
							+ "  启动线程" + id+"  当前共有线程数："+instanceCount);
				}
			}
		}
	}

	/**
	 * 停止所有线程
	 * */
	public static void stopAll() {
		if (useMap.size() > 0) {
			Iterator<ImageCreater> ite = useMap.values().iterator();
			while (ite.hasNext()) {
				ImageCreater creater = ite.next();
				creater.stopSelf();
				creater = null;
			}
		}
		instanceCount = 0;
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
		if (excelInfoVo == null || excelInfoVo.image_url == null) {
			System.out.println("null");
		}
		File file = new File(excelInfoVo.image_url);
		if (FileUtil.checkIsImage(excelInfoVo.image_url) == false) {
			JOptionPane.showMessageDialog(null, excelInfoVo.name
					+ " 的照片路径错误或者格式不正确");
			this.stopSelf();
			Global.setState(Global.STATE_ERROR);
			return;
		}
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
			pieceImage = new BufferedImage(1205, 1795,
					BufferedImage.TYPE_3BYTE_BGR);
		} catch (IOException ioE) {
			ioE.printStackTrace();
			JOptionPane.showMessageDialog(null, "读取原始照片失败");
			this.stopSelf();
			Global.setState(Global.STATE_ERROR);
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
			Global.setState(Global.STATE_ERROR);
			return;
		}
		if (image == null) {
			JOptionPane.showMessageDialog(null, "合成照片失败");
			stopSelf();
			Global.setState(Global.STATE_ERROR);
			return;
		}
		File newImage = new File(new File(excelInfoVo.image_url)
				.getParentFile().getAbsoluteFile()
				+ "\\"
				+ excelInfoVo.name
				+ ".jpg");
		try {
			ImageIO.write(pieceImage, "jpeg", newImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "写入合成后的图片失败");
			this.stopSelf();
			Global.setState(Global.STATE_ERROR);
			return;
		}
		this.stopSelf();
	}

	/**
	 * 绘制大图
	 * */
	private void drawImage(BufferedImage image) throws IOException {
		//背景设置为白色
		Graphics2D graphics = pieceImage.createGraphics();
		graphics.setBackground(Color.WHITE);
		graphics.setPaint(Color.BLACK);
		graphics.clearRect(0, 0, pieceImage.getWidth(), pieceImage.getHeight());
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
		Font font = new Font("黑体", Font.BOLD, 40);
		if (font.canDisplay('a') == true) {
			graphics.setFont(font);
			graphics.drawString(excelInfoVo.school_name, 66, 1651);
			graphics.drawString(excelInfoVo.name, 66, 1726);
			graphics.drawString(excelInfoVo.stu_id + "  "
					+ excelInfoVo.school_No, 673, 1656);
			graphics.drawString(excelInfoVo.id, 710, 1728);
		}
		graphics.dispose();
	}
}
