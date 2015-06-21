import global.RegisterUtil;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Window.Type;
import java.awt.SystemColor;
import java.awt.Dialog.ModalExclusionType;


@SuppressWarnings("serial")
public class RegisterView extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

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

	/**
	 * Create the frame.
	 */
	public RegisterView() {
		setModalExclusionType(ModalExclusionType.TOOLKIT_EXCLUDE);
		setForeground(SystemColor.inactiveCaptionBorder);
		setResizable(false);
		setBackground(SystemColor.inactiveCaptionBorder);
		setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("请输入注册码");
		lblNewLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
		lblNewLabel.setBounds(124, 54, 184, 37);
		lblNewLabel.setEnabled(false);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("验证");
		btnNewButton.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				onClickRegisterHandler(e);
			}
		});
		btnNewButton.setBounds(179, 203, 93, 23);
		contentPane.add(btnNewButton);
		
		textField = new JTextField();
		textField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
		textField.setBounds(98, 103, 243, 74);
		contentPane.add(textField);
		textField.setColumns(10);
	}

	/**
	 * 点击了注册按钮
	 * */
	private void onClickRegisterHandler(MouseEvent e)
	{
		if(RegisterUtil.checkRegister())
		{
			ViewProxy.registerView.setVisible(false);
			ViewProxy.pieceView.setVisible(true);
		}
	}
}
