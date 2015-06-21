import java.awt.Dialog.ModalExclusionType;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class RootView extends JFrame {
	public RootView() {
		getContentPane().setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		setModalExclusionType(ModalExclusionType.TOOLKIT_EXCLUDE);
		setForeground(SystemColor.inactiveCaptionBorder);
		setBackground(SystemColor.inactiveCaptionBorder);
		getContentPane().setBackground(SystemColor.inactiveCaptionBorder);
		setResizable(false);
		setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		setType(Type.UTILITY);
	}


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewProxy.registerView.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
