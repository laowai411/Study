import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

/***
 * 可以将线程加入(待优化)
 * 
 * */
@SuppressWarnings("serial")
public class Window extends JFrame {

	private JPanel contentPane;
	/**
	 * 地图块路径
	 * */
	private JTextField gURLCeil;
	/**
	 * 合并后地图保存路径
	 * */
	private JTextField gURLMap;
	/**
	 * 地图块前缀
	 * */
	private JTextField gTxtHeadSign;
	/**
	 * 行列分隔符
	 * */
	private JTextField gTxtSplitSign;
	private JButton gBtnSelectCeil;
	private JButton gBtnSelectPiece;
	private JButton gBtnPiece;
	private JRadioButton gRadioRow;
	private JRadioButton gRadioCol;
	private JTextField gTxtStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 加载计时器
	 * */
	private Timer loadTimer;

	/**
	 * 合成计时器
	 * */
	private Timer pieceTimer;

	/**
	 * 大地图
	 * */
	private BufferedImage map;

	/**
	 * 地图宽度
	 * */
	private int mapWidth;

	/**
	 * 地图高度
	 * */
	private int mapHeigth;

	/**
	 * 列索引
	 * */
	private HashMap<Integer, Integer> colWidthMap = new HashMap<Integer, Integer>();

	/**
	 * 行高度
	 * */
	private HashMap<Integer, Integer> rowHeightMap = new HashMap<Integer, Integer>();

	/**
	 * 当前是否在操作中
	 * */
	private boolean isOping;

	/**
	 * 地图块文件
	 * */
	private LinkedList<File> ceilFileList = new LinkedList<File>();

	/**
	 * 地图块位图数据
	 * */
	private LinkedList<CeilDataVo> ceilDataList = new LinkedList<CeilDataVo>();

	/**
	 * 一个地图块的二进制数据,重复使用(所有地图块中最大的)
	 * */
	private int[] bitmapData;

	/**
	 * 加载计时器回调
	 * */
	private ActionListener onLoadTimerHandler = new ActionListener() {
		// 加载
		public void actionPerformed(ActionEvent e) {
			if (isOping == false) {
				isOping = true;
				loadCeil(ceilFileList.pop());
				isOping = false;
				// 合成
				if (ceilFileList.size() < 1) {
					loadTimer.stop();
					System.out.println("宽度，高度："+mapWidth+", "+mapHeigth);
					try
					{
						map = new BufferedImage(mapWidth, mapHeigth,
								BufferedImage.TYPE_3BYTE_BGR);
					}
					catch(java.lang.OutOfMemoryError error)
					{
						JOptionPane.showMessageDialog(null, "内存不足！请先关闭无用的程序");
						stop(false);
						return;
					}
					copyPixes();
				}
			}
		}
	};

	/**
	 * copy地图块计时器回调
	 * */
	private ActionListener onCopyTimerHandler = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (isOping == false) {
				isOping = true;
				CeilDataVo data = ceilDataList.pop();
				Raster raster = data.image.getData();
				if (bitmapData == null
						|| bitmapData.length < raster.getWidth()
								* raster.getHeight()) {
					bitmapData = new int[raster.getWidth() * raster.getHeight()];
				}
				raster.getPixel(0, 0, bitmapData);
				int tempWidth = 0;
				int tempHeight = 0;
				for (int i = 0; i < data.row; i++) {
					tempHeight += rowHeightMap.get(i);
				}
				for (int i = 0; i < data.col; i++) {
					tempWidth += colWidthMap.get(i);
				}
				map.getGraphics().drawImage(data.image, tempWidth, tempHeight,
						null);
				showMsg("拷贝地图块数据:" + data.url + ", 剩余" + ceilDataList.size());
				// 释放内存?
				CeilDataVo.cacheCeilDataVo(data);
				data = null;
				isOping = false;
				if (ceilDataList.size() < 1) {
					pieceTimer.stop();
					showMsg("开始生成地图文件");
					createMapFile();
				}
			}
		}
	};

	/**
	 * 开始
	 * */
	private void startLoad() {
		if (checkCeilPath() == false) {
			this.setEnable(true);
			JOptionPane.showMessageDialog(null, "地图块路径错误!");
			stop(false);
			return;
		}
		if (checkSavePath() == false) {
			this.setEnable(true);
			JOptionPane.showMessageDialog(null, "保存路径错误");
			stop(false);
			return;
		}
		fixURL();
		bitmapData = null;
		map = null;
		isOping = false;
		mapWidth = 0;
		mapHeigth = 0;
		File[] files = new File(gURLCeil.getText())
				.listFiles(new ImageFilter());
		int len = files != null ? files.length : 0;
		for (int i = 0; i < len; i++) {
			ceilFileList.add(files[i]);
		}
		colWidthMap = new HashMap<Integer, Integer>();
		rowHeightMap = new HashMap<Integer, Integer>();

		if (loadTimer != null) {
			loadTimer.stop();
			loadTimer.removeActionListener(onLoadTimerHandler);
			loadTimer = null;
		}

		loadTimer = new Timer(10, onLoadTimerHandler);
		loadTimer.start();
	}

	/**
	 * 检测地图块路径
	 * */
	private boolean checkCeilPath() {
		File file = new File(gURLCeil.getText());
		if (file.exists() == false || file.isDirectory() == false
				|| file.canRead() == false) {
			return false;
		}
		return true;
	}

	/**
	 * 检测保存路径是
	 * */
	private boolean checkSavePath() {
		File file = new File(gURLMap.getText());
		if (file.exists() == false || file.isDirectory() == false
				|| file.canWrite() == false) {
			return false;
		}
		return true;
	}

	/**
	 * 修正地图块和地图保存路径 最后加上\
	 * */
	private void fixURL() {
		if (gURLCeil.getText().endsWith("\\") == false) {
			gURLCeil.setText(gURLCeil.getText() + "\\");
		}
		if (gURLMap.getText().endsWith("\\") == false) {
			gURLMap.setText(gURLMap.getText() + "\\");
		}
	}

	/**
	 * 载入小地图到内存
	 * */
	private void loadCeil(File file) {
		try {
			BufferedImage image = ImageIO.read(file);
			if (file.getName().toLowerCase()
					.startsWith(gTxtHeadSign.getText().toLowerCase())
					&& file.getName().toLowerCase()
							.indexOf(gTxtSplitSign.getText().toLowerCase()) > -1) {
				String name = file.getName().toLowerCase()
						.replace(gTxtHeadSign.getText().toLowerCase(), "")
						.replace(".jpg", "").replace(".png", "");
				String[] inds = name.split(gTxtSplitSign.getText()
						.toLowerCase());
				int row = 0;
				int col = 0;
				if (gRadioCol.isSelected()) {
					try
					{
						col = Integer.parseInt(inds[0]);
					}
					catch(Error error)
					{
						JOptionPane.showMessageDialog(null, "请检查前缀和分隔符是否正确!");
						stop(false);
						return;
					}
					row = Integer.parseInt(inds[1]);
				} else {
					col = Integer.parseInt(inds[1]);
					row = Integer.parseInt(inds[0]);
				}
				if (colWidthMap.get(col) == null) {
					colWidthMap.put(col, image.getWidth());
					mapWidth += colWidthMap.get(col);
				}
				if (rowHeightMap.get(row) == null) {
					rowHeightMap.put(row, image.getHeight());
					mapHeigth += rowHeightMap.get(row);
				}
				ceilDataList.add(CeilDataVo.getCeilDataVo(image, col, row,
						file.getPath()));
				showMsg("载入地图块:" + file.getPath() + ", 剩余"
						+ ceilFileList.size());
				file = null;
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "载入地图块失败!");
			e.printStackTrace();
		}
	}

	/**
	 * 循环给大地图copypixes
	 * */
	private void copyPixes() {
		if (pieceTimer != null) {
			pieceTimer.stop();
			pieceTimer.removeActionListener(onCopyTimerHandler);
			pieceTimer = null;
		}
		pieceTimer = new Timer(10, onCopyTimerHandler);
		pieceTimer.start();
	}

	/**
	 * 生成地图文件
	 * */
	private void createMapFile() {
		File file = new File(gURLMap.getText() + "piece.jpg");
		if (file.exists()) {
			file.delete();
		}
		try {
			ImageIO.write(map, "jpeg", file);
			stop(true);
		} catch (IOException e) {
			e.printStackTrace();
			stop(false);
		}
	}
	
	/**
	 * 停止程序
	 * */
	private void stop(Boolean success)
	{
		if(success == true)
		{
			JOptionPane.showMessageDialog(null, "创建大地图成功!");
		}
		else
		{
			JOptionPane.showMessageDialog(null, "创建大地图失败!");
		}
		showMsg("合并结束");
		// 合成完成
		setEnable(true);
	}

	/**
	 * 当前状态
	 * */
	private void showMsg(String msg) {
		gTxtStatus.setText(msg);
	}

	// /////////////////////////////////////////////////////////
	// 显示
	// ////////////////////////////////////////////////////////

	private void setEnable(boolean cusValue) {
		gTxtSplitSign.setEnabled(cusValue);
		gTxtHeadSign.setEnabled(cusValue);
		gURLCeil.setEnabled(cusValue);
		gURLMap.setEnabled(cusValue);
		gBtnPiece.setEnabled(cusValue);
		gBtnSelectCeil.setEnabled(cusValue);
		gBtnSelectPiece.setEnabled(cusValue);
		gRadioCol.setEnabled(cusValue);
		gRadioRow.setEnabled(cusValue);
	}

	/**
	 * Create the frame.
	 */
	public Window() {
		setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						Window.class
								.getResource("/javax/swing/plaf/metal/icons/ocean/hardDrive.gif")));
		setFont(new Font("Courier New", Font.PLAIN, 12));
		setResizable(false);
		setTitle("地图块合成工具1.0.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 464, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("地图块合成工具");

		JLabel lblNewLabel_1 = new JLabel("地图块路径");

		gURLCeil = new JTextField();
		gURLCeil.setText("D:\\wssb\\地图块\\");
		gURLCeil.setColumns(10);

		gBtnSelectCeil = new JButton("...");
		gBtnSelectCeil.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(new DirFilter());
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				chooser.setDialogTitle("选择地图块目录:");
				chooser.showOpenDialog(null);
				gURLCeil.setText(chooser.getSelectedFile().getPath() + "\\");
			}
		});

		JLabel lblNewLabel_2 = new JLabel("合成保存至");

		gURLMap = new JTextField();
		gURLMap.setText("D:\\wssb\\地图\\");
		gURLMap.setColumns(10);

		gBtnSelectPiece = new JButton("...");
		gBtnSelectPiece.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(new DirFilter());
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				chooser.setDialogTitle("选择合成后保存目录:");
				chooser.showOpenDialog(null);
				gURLMap.setText(chooser.getSelectedFile().getPath() + "\\");
			}
		});

		gBtnPiece = new JButton("开始合成");
		gBtnPiece.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setEnable(false);
				startLoad();
			}
		});

		gRadioRow = new JRadioButton("行在前");
		gRadioRow.setSelected(true);

		JLabel label = new JLabel("前缀");

		gTxtHeadSign = new JTextField();
		gTxtHeadSign.setColumns(10);

		gRadioCol = new JRadioButton("列在前");

		JLabel label_1 = new JLabel("分隔符");

		gTxtSplitSign = new JTextField();
		gTxtSplitSign.setText("_");
		gTxtSplitSign.setColumns(10);

		gTxtStatus = new JTextField();
		gTxtStatus.setEditable(false);
		gTxtStatus.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addGap(166)
																		.addComponent(
																				lblNewLabel,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addGap(174))
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addContainerGap()
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								gl_contentPane
																										.createSequentialGroup()
																										.addComponent(
																												lblNewLabel_2)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												gURLMap,
																												292,
																												292,
																												292))
																						.addGroup(
																								gl_contentPane
																										.createSequentialGroup()
																										.addComponent(
																												lblNewLabel_1)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												gURLCeil,
																												GroupLayout.PREFERRED_SIZE,
																												292,
																												GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								gl_contentPane
																										.createSequentialGroup()
																										.addGap(21)
																										.addGroup(
																												gl_contentPane
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addGroup(
																																gl_contentPane
																																		.createSequentialGroup()
																																		.addComponent(
																																				label)
																																		.addPreferredGap(
																																				ComponentPlacement.RELATED)
																																		.addComponent(
																																				gTxtHeadSign,
																																				GroupLayout.PREFERRED_SIZE,
																																				GroupLayout.DEFAULT_SIZE,
																																				GroupLayout.PREFERRED_SIZE))
																														.addGroup(
																																gl_contentPane
																																		.createSequentialGroup()
																																		.addComponent(
																																				label_1,
																																				GroupLayout.PREFERRED_SIZE,
																																				38,
																																				Short.MAX_VALUE)
																																		.addPreferredGap(
																																				ComponentPlacement.RELATED)
																																		.addComponent(
																																				gTxtSplitSign,
																																				GroupLayout.PREFERRED_SIZE,
																																				52,
																																				GroupLayout.PREFERRED_SIZE)))
																										.addGap(150)
																										.addGroup(
																												gl_contentPane
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addComponent(
																																gRadioCol)
																														.addComponent(
																																gRadioRow))
																										.addGap(30)))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								gBtnSelectPiece,
																								0,
																								0,
																								Short.MAX_VALUE)
																						.addComponent(
																								gBtnSelectCeil,
																								GroupLayout.DEFAULT_SIZE,
																								55,
																								Short.MAX_VALUE)))
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addGap(177)
																		.addComponent(
																				gBtnPiece,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addGap(166)))
										.addGap(24))
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(gTxtStatus,
												GroupLayout.DEFAULT_SIZE, 428,
												Short.MAX_VALUE)
										.addContainerGap()));
		gl_contentPane
				.setVerticalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblNewLabel)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addGap(8)
																		.addComponent(
																				lblNewLabel_1,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE))
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addGap(2)
																		.addComponent(
																				gURLCeil,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																gBtnSelectCeil))
										.addGap(18)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addGap(8)
																		.addComponent(
																				lblNewLabel_2,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE))
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addGap(2)
																		.addComponent(
																				gURLMap,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																gBtnSelectPiece))
										.addGap(26)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																gRadioRow,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(label)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addGap(2)
																		.addComponent(
																				gTxtHeadSign,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																gRadioCol,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(label_1)
														.addComponent(
																gTxtSplitSign,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(31)
										.addComponent(gBtnPiece,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(gTxtStatus,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGap(9)));
		ButtonGroup group = new ButtonGroup();
		group.add(gRadioCol);
		group.add(gRadioRow);
		contentPane.setLayout(gl_contentPane);
	}
}
