import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class CeilDataVo {
	
	public BufferedImage image;
	
	public int col;
	
	public int row;
	
	public String url;
	
	public CeilDataVo()
	{
		
	}
	
	private static LinkedList<CeilDataVo> pool = new LinkedList<CeilDataVo>();
	
	public static CeilDataVo getCeilDataVo(BufferedImage cusImage, int cusCol, int cusRow, String cusUrl)
	{
		CeilDataVo vo;
		if(pool.isEmpty())
		{
			vo =  new CeilDataVo();
		}
		else
		{
			vo = pool.pop();
		}
		vo.image = cusImage;
		vo.col = cusCol;
		vo.row = cusRow;
		vo.url = cusUrl;
		return vo;
	}
	
	public static void cacheCeilDataVo(CeilDataVo cusData)
	{
		cusData.col = 0;
		cusData.row = 0;
		cusData.url = null;
		cusData.image = null;
		pool.add(cusData);
	}
}
