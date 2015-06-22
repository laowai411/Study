package excel;

import global.Global;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

/**
 * 一条excel信息
 * */
public class ExcelInfoVo {
	
	/**
	 * 编号
	 * */
	public String index;
	
	/**
	 * 学历
	 * */
	public String education;

	/**
	 * 学号
	 * */
	public String stu_id;

	/**
	 * 姓名
	 * */
	public String name;
	
	/**
	 * 性别
	 * */
	public String sex;

	/**
	 * 身份证号
	 * */
	public String id;

	/**
	 * 学校类别
	 * */
	public String school_type;
	
	/**
	 * 学校编号
	 * */
	public String school_No;

	/**
	 * 学校名称
	 * */
	public String school_name;

	/**
	 * 照片路径
	 * */
	public String image_url;
	/**
	 * 原始文件名
	 * */
	public String src_image_name;

	public void setData(ArrayList<String> list) {
		index = list.get(0);
		education = list.get(1);
		stu_id = list.get(2);
		name = list.get(3);
		sex = list.get(4);
		id = list.get(5);
		school_type = list.get(6);
		school_No = list.get(7);
		school_name = list.get(8);
		src_image_name = "IMG_"+index + ".jpg";
		image_url = Global.srcImageURL+"\\"+src_image_name;
	}
	
	public void reset()
	{
		index = "";
		stu_id = "";
		name = "";
		id = "";
		school_type = "";
		school_name = "";
		image_url = "";
	}

	private static Queue<ExcelInfoVo> gCache = new ArrayDeque<ExcelInfoVo>();

	public static ExcelInfoVo getExcelInfoVo() {
		synchronized (gCache) {
			if (gCache.size() > 0) {
				return gCache.poll();
			}
			return new ExcelInfoVo();
		}
	}

	public static void cacheExcelInfoVo(ExcelInfoVo cusExcelInfoVo) {
		if (gCache.contains(cusExcelInfoVo) == false) {
			gCache.add(cusExcelInfoVo);
		}
	}
}
