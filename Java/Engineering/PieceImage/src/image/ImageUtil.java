package image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
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

		//确定旋转后的图象的高度和宽度

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
}
