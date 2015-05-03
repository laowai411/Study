
import image.ImageCreater;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import excel.ExcelParser;
import fileUtil.DirectoriesFilter;
import fileUtil.ExcelFilter;
import global.Global;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author AngryPotato
 */
public class PieceImage extends javax.swing.JFrame {

    /**
     * Creates new form PieceImage
     */
    public PieceImage() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        gTxtExcel = new javax.swing.JTextField();
        gBtnExcel = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        gTxtSave = new javax.swing.JTextField();
        gBtnSave = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        gTxtState = new javax.swing.JTextField();
        jProgressBar1 = new javax.swing.JProgressBar();
        gBtnPiece = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("照片合成 v1.0 beta");
        setAutoRequestFocus(false);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImages(null);
        setName(""); // NOI18N

        jLabel1.setText("照片信息excel");

        gTxtExcel.setBackground(new java.awt.Color(204, 204, 204));
        gTxtExcel.setText("照片配置的excel的路径...");
        gTxtExcel.setToolTipText("");

        gBtnExcel.setText("选择...");
        gBtnExcel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onBtnExcelMouseClicked(evt);
            }
        });

        jLabel2.setText("合成后保存至");

        gTxtSave.setBackground(new java.awt.Color(204, 204, 204));
        gTxtSave.setText("保存在原始照片目录，目前不支持设置");
        gTxtSave.setEnabled(false);

        gBtnSave.setText("选择...");
        gBtnSave.setEnabled(false);
        gBtnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onBtnSaveMouseClicked(evt);
            }
        });

        jLabel3.setText("当前状态");

        gTxtState.setText("未开始...");
        gTxtState.setEnabled(false);

        gBtnPiece.setText("合成");
        gBtnPiece.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onBtnPieceMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(254, 254, 254)
                .addComponent(gBtnPiece)
                .addContainerGap(259, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(gTxtSave))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(gTxtExcel)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(gBtnExcel)
                                    .addComponent(gBtnSave)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(gTxtState, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(gTxtExcel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gBtnExcel))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(gTxtSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gBtnSave))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(gTxtState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(gBtnPiece)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * 选择excel配置文件按钮被点击
     *
     */
    private void onBtnExcelMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_onBtnExcelMouseClicked
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new ExcelFilter());
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setDialogTitle("选择excel信息文件:");
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        if (file != null && file.exists() && file.isFile()) {
            gTxtExcel.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }// GEN-LAST:event_onBtnExcelMouseClicked

    /**
     * 生成图片保存路径按钮被点击
     *
     */
    private void onBtnSaveMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_onBtnSaveMouseClicked
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new DirectoriesFilter());
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setDialogTitle("合成后保存至:");
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        if (file != null && file.exists()) {
            gTxtSave.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }// GEN-LAST:event_onBtnSaveMouseClicked

    /**
     * 合成按钮被点击
     *
     */
    private void onBtnPieceMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_onBtnPieceMouseClicked
        // TODO add your handling code here:
        if (checkURL() == false) {
            return;
        }
        setAbled(false);
        if (excelParser == null) {
            excelParser = new ExcelParser();
        }
        excelParser.init();
        try {
            excelParser.readExcel(gTxtExcel.getText());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            setAbled(true);
            e.printStackTrace();
            return;
        }
        ImageCreater.startThread();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (Global.getState() == Global.STATE_ERROR) {
                    ImageCreater.stopAll();
                    JOptionPane.showMessageDialog(null, "合成失败");
                }
                else if(Global.getState() == Global.STATE_SUCCESS)
                {
                    ImageCreater.stopAll();
                    setAbled(true);
                    JOptionPane.showMessageDialog(null, "合成结束");
                }
            }
        }, 1000, 500);
    }// GEN-LAST:event_onBtnPieceMouseClicked
    private ExcelParser excelParser;

    /**
     * 检测URL是否有问题
     *
     */
    private boolean checkURL() {
        if (fileUtil.FileUtil.checkIsExcel(gTxtExcel.getText()) == false) {
            JOptionPane.showMessageDialog(null, "照片excel信息路径错误");
            return false;
        }
//        if (fileUtil.FileUtil.checkIsDir(gTxtSave.getText()) == false) {
//            JOptionPane.showMessageDialog(null, "保存路径设置错误");
//            return false;
//        }
        return true;
    }

    private void setAbled(boolean enabled) {
        gTxtExcel.setEnabled(enabled);
        gTxtSave.setEnabled(enabled);
        gBtnExcel.setEnabled(enabled);
        gBtnSave.setEnabled(enabled);
        gBtnPiece.setEnabled(enabled);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed"
        // desc=" Look and feel setting code (optional) ">
		/*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase
         * /tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                    .getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PieceImage.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PieceImage.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PieceImage.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PieceImage.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PieceImage().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton gBtnExcel;
    private javax.swing.JButton gBtnPiece;
    private javax.swing.JButton gBtnSave;
    private javax.swing.JTextField gTxtExcel;
    private javax.swing.JTextField gTxtSave;
    private javax.swing.JTextField gTxtState;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
}
