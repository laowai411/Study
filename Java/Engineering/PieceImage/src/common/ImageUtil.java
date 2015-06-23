package common;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageUtil {

	/**
	 * 缩放
	 * */
	public static BufferedImage scaleImg(BufferedImage image, int w, int h,
			Color bgColor) throws IOException {
		Image scaleInstance = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		BufferedImage scaleImage = new BufferedImage(w, h, image.getType());
		Graphics2D graphics = scaleImage.createGraphics();
		graphics.setColor(bgColor);
		graphics.fillRect(0, 0, w, h);
		graphics.drawImage(scaleInstance, 0, 0, null);
		graphics.dispose();
		return scaleImage;
	}

	/**
	 * 旋转
	 * */
	public static BufferedImage rotateImg(BufferedImage image, int degree,
			Color bgcolor) throws IOException {

		int iw = image.getWidth();// 原始图象的宽度
		int ih = image.getHeight();// 原始图象的高度
		int w = 0;
		int h = 0;
		int x = 0;
		int y = 0;
		degree = degree % 360;
		if (degree < 0)
			degree = 360 + degree;// 将角度转换到0-360度之间
		double ang = Math.toRadians(degree);// 将角度转为弧度

		// 确定旋转后的图象的高度和宽度

		if (degree == 180 || degree == 0 || degree == 360) {
			w = iw;
			h = ih;
		} else if (degree == 90 || degree == 270) {
			w = ih;
			h = iw;
		} else {
			int d = iw + ih;
			w = (int) (d * Math.abs(Math.cos(ang)));
			h = (int) (d * Math.abs(Math.sin(ang)));
		}

		x = (w / 2) - (iw / 2);// 确定原点坐标
		y = (h / 2) - (ih / 2);
		BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
		Graphics2D gs = (Graphics2D) rotatedImage.getGraphics();
		if (bgcolor == null) {
			rotatedImage = gs.getDeviceConfiguration().createCompatibleImage(w,
					h, Transparency.TRANSLUCENT);
		} else {
			gs.setColor(bgcolor);
			gs.fillRect(0, 0, w, h);// 以给定颜色绘制旋转后图片的背景
		}

		AffineTransform at = new AffineTransform();
		at.rotate(ang, w / 2, h / 2);// 旋转图象
		at.translate(x, y);
		AffineTransformOp op = new AffineTransformOp(at,
				AffineTransformOp.TYPE_BICUBIC);
		rotatedImage.getGraphics().setColor(bgcolor);
		op.filter(image, rotatedImage);
		gs.dispose();
		return rotatedImage;
	}

	/**
	 * 给图片添加文字水印
	 * 
	 * @param logoText
	 *            水印文字
	 * @param srcImage
	 *            图片
	 * @param degree
	 *            水印文字旋转角度
	 * @param cusX
	 *            水印坐标
	 * @param cusY
	 *            水印坐标
	 * @param cusAlpha
	 *            水印透明度
	 * @param cusSize
	 *            字号
	 * */
	public static void markImageByText(String logoText, BufferedImage srcImage,
			Integer degree, int cusX, int cusY, float cusAlpha, int cusSize) {
		Graphics2D g = srcImage.createGraphics();
		// 设置对线段的锯齿边缘处理
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(
				srcImage.getScaledInstance(srcImage.getWidth(null),
						srcImage.getHeight(null), Image.SCALE_SMOOTH), 0, 0,
				null);
		// 设置旋转
		// if(degree != null)
		// {
		// g.rotate(Math.toRadians(degree),(double) srcImage.getWidth(),
		// (double) srcImage.getHeight());
		// }

		// 设置水印文字Font
		Font font = new Font(FontUtil.SONG_40.getFamily(), Font.BOLD, cusSize);
		g.setFont(font);
		// 设置水印文字透明度
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
				cusAlpha));
		int tempWidth = getWatermarkLength(logoText, g);
		g.setColor(Color.WHITE);
		g.fillRect(cusX, cusY - cusSize, tempWidth, g.getFontMetrics()
				.getHeight());
		// 设置水印文字颜色
		g.setColor(Color.BLACK);
		// 第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y)
		g.drawString(logoText, cusX, cusY);
		// 释放资源
		g.dispose();
	}

	/**
	 * 获取水印文字总长度
	 * 
	 * @param str
	 *            文字内容
	 * @param g
	 * */
	private static int getWatermarkLength(String str, Graphics2D g) {
		if (str != "" && str.trim().length() > 0) {
			return g.getFontMetrics(g.getFont()).charsWidth(str.toCharArray(),
					0, str.length());
		} else {
			return 0;
		}
	}
}
