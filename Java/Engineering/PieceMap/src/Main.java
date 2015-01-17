import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import javax.imageio.ImageIO;


public class Main {

	
	private static Window window;
	
	private static Timer timer;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		window = new Window();
		
		
		Global.ceilHeadSign = "";
		Global.ceilSpiltSign = "_";
		Global.isColHead = false;
		Global.mapCeilPath = "D:/wssb/地图块/";
		Global.mapPiecePath = "D:/wssb/地图/";
		
		timer = new Timer();
		init();
	}
	
	private static int mapWidth;
	
	private static int mapHeigth;
	
	private static ArrayList colWidthList;
	
	private static ArrayList rowHeightList;
	
	private static boolean isOping;
	
	private static File[] ceilFileList;
	
	private static BufferedImage[] ceilDataList;
	
	private static int index = 0;
	
	private static void init()
	{
		index = 0;
		mapWidth = 0;
		mapHeigth = 0;
		colWidthList = new ArrayList();
		rowHeightList = new ArrayList();
		ceilFileList = null;
		getCeilFileList();
		ceilDataList = new BufferedImage[ceilFileList.length];
		
		//10ms后每10ms一次循环
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				if(isOping == false)
				{
					isOping = true;
					if(index<ceilFileList.length)
					{
						File file = (File) ceilFileList[index];
						try {
							BufferedImage imageBuff = ImageIO.read(file);
							ceilDataList[index] = imageBuff;
							index++;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					else
					{
						timer.purge();
					}
				}
			}
		}, 10, 10);
	}
	
	/**
	 * 获取所有的地图块
	 * */
	private static void getCeilFileList()
	{
		File file = new File(Global.mapCeilPath);
		if(file.exists() == false || file.isDirectory()==false || file.canRead()==false)
		{
			return;
		}
		ceilFileList = file.listFiles(new ImageFilter());
	}
	
	/**
	 * 载入小地图到内存
	 * */
	private static void load()
	{
		
	}

	/**
	 * 创建大地图
	 * */
	private static void createMap()
	{
		
	}
	
	/**
	 * 循环给大地图copypixes
	 * */
	private static void copyPixes()
	{
		
	}
	
	/**
	 * 生成地图文件
	 * */
	private static void createMapFile()
	{
		
	}
	
}
