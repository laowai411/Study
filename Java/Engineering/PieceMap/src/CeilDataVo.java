import java.awt.image.BufferedImage;


public class CeilDataVo {
	
	public BufferedImage image;
	
	public int col;
	
	public int row;
	
	public String url;
	
	public CeilDataVo(BufferedImage cusImage, int cusCol, int cusRow, String cusUrl)
	{
		image = cusImage;
		col = cusCol;
		row = cusRow;
		url = cusUrl;
	}
}
