package image;

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

import excel.ExcelInfoVo;
import excel.ExcelParser;
import fileUtil.FileUtil;
import global.Global;

/**
 * 图片合成
 *
 */
public class ImageCreater extends Thread {

    private static HashMap<String, ImageCreater> useMap = new HashMap<String, ImageCreater>();
    private static int instanceCount = 0;

    public static void startThread() {
        if (Global.getState() == Global.STATE_ERROR || Global.getState() == Global.STATE_SUCCESS) {
            return;
        }
        ExcelInfoVo excelInfoVo = ExcelParser.getLastExcelInfoVo();
        if (excelInfoVo != null) {
            ImageCreater creater = new ImageCreater();
            creater.init(excelInfoVo, instanceCount + "");
            creater.start();
            useMap.put(instanceCount + "", creater);
            System.out.println(Calendar.getInstance().getTimeInMillis() + "  启动线程" + instanceCount);
            instanceCount++;
        }
    }

    public static void stopAll() {
        if (useMap.size() > 0) {
            Iterator<ImageCreater> ite = useMap.values().iterator();
            while (ite.hasNext()) {
                ImageCreater creater = ite.next();
                creater.stopSelf();
                creater = null;
            }
        }
    }
    private ExcelInfoVo excelInfoVo;
    private BufferedImage pieceImage;
    private String gId;

    private void init(ExcelInfoVo cusExcelInfoVo, String cusId) {
        excelInfoVo = cusExcelInfoVo;
        gId = cusId;
    }

    private void stopSelf() {
        interrupt();
        if (useMap.containsValue(this)) {
            useMap.remove(gId);
        }
        if (checkEnd()) {
            return;
        }
        ImageCreater.startThread();
    }

    private boolean checkEnd() {
        if (ExcelParser.getOddExcelInfoVoCount() < 1 && useMap.size() < 1) {
            Global.setState(Global.STATE_SUCCESS);
            return true;
        }
        return false;
    }

    public String getThreadId() {
        return gId;

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

    private void drawImage(BufferedImage image) throws IOException {
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
            tempX = startX + ((i % 2) * (w + 2));
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
