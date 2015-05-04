package excel;

import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import fileUtil.FileUtil;

/**
 * excel解析器
 * */
public class ExcelParser {

	/**
	 * excel读取的信息
	 * */
	private static ArrayList<ExcelInfoVo> gStudentList = new ArrayList<ExcelInfoVo>();
	
	/**
	 * excel信息总条数
	 * */
	public static int totalCount;

	public static ExcelInfoVo getLastExcelInfoVo() {
		synchronized (gStudentList) {
			int len = gStudentList.size();
			if (len > 0) {
				return gStudentList.remove(len - 1);
			}
			return null;
		}
	}

	/**
	 * 剩余excel配置条目
	 * */
	public static int getOddExcelInfoVoCount() {
		return gStudentList.size();
	}

	public void init() {
		int len = gStudentList.size();
		for (int i = 0; i < len; i++) {
			ExcelInfoVo excelinfoVo = gStudentList.remove(len - 1 - i);
			excelinfoVo.reset();
			ExcelInfoVo.cacheExcelInfoVo(excelinfoVo);
		}
	}

	/**
	 * 读取excel
	 * */
	public void readExcel(String excelURL) throws Exception {
		String extName = FileUtil.getExtensionName(excelURL);
		if (extName.equalsIgnoreCase("xls")) {
			read97_2003(gStudentList, excelURL);
		} else {
			read07_13(gStudentList, excelURL);
		}
		totalCount = gStudentList.size();
	}

	/**
	 * 97-03格式的excel
	 * 
	 * @throws Exception
	 * */
	private void read97_2003(ArrayList<ExcelInfoVo> list, String filePath)
			throws Exception {
		FileInputStream fis = null;
		fis = new FileInputStream(filePath);
		HSSFWorkbook wookbook = new HSSFWorkbook(fis); // 创建对Excel工作簿文件的引用
		HSSFSheet sheet = wookbook.getSheetAt(0); // 在Excel文档中，第一张工作表的缺省索引是0
		int rows = sheet.getPhysicalNumberOfRows(); // 获取到Excel文件中的所有行数­
		// 遍历行­（第1行 表头）
		int cells = 0;
		HSSFRow firstRow = sheet.getRow(0);
		if (firstRow != null) {
			// 获取到Excel文件中的所有的列
			cells = firstRow.getPhysicalNumberOfCells();
		}
		// 遍历行­（从第二行开始）
		for (int i = 1; i < rows; i++) {
			// 读取左上端单元格(从第二行开始)
			HSSFRow row = sheet.getRow(i);
			// 行不为空
			if (row != null) {
				boolean isValidRow = false;
				// 遍历列
				ArrayList<String> valueList = new ArrayList<String>();
				for (int j = 0; j < cells; j++) {
					// 获取到列的值­
					try {
						HSSFCell cell = row.getCell(j);
						String cellValue = getCellValue(cell);
						valueList.add(j, cellValue);
						if (!isValidRow && cellValue != null
								&& cellValue.trim().length() > 0) {
							isValidRow = true;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// 第I行所有的列数据读取完毕，放入valuelist
				if (isValidRow && checkValue(valueList)) {
					ExcelInfoVo excelInfoVo = ExcelInfoVo.getExcelInfoVo();
					excelInfoVo.setData(valueList);
					list.add(excelInfoVo);
				}
			}
		}
		fis.close();
	}

	/**
	 * 检测某条数据是否为无效值
	 * */
	private boolean checkValue(ArrayList<String> valueList) {
		for (int i = 0; i < 10; i++) {
			if (valueList.get(i) == null
					|| valueList.get(i).trim().length() < 1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 07-13格式的excel
	 * */
	private void read07_13(ArrayList<ExcelInfoVo> list, String filePath)
			throws Exception {
		FileInputStream fis = null;
		fis = new FileInputStream(filePath);
		XSSFWorkbook xwb = new XSSFWorkbook(fis); // 构造 XSSFWorkbook
													// 对象，strPath 传入文件路径
		XSSFSheet sheet = xwb.getSheetAt(0); // 读取第一章表格内容
		// 定义 row、cell
		XSSFRow row;
		// 循环输出表格中的从第二行开始内容
		for (int i = sheet.getFirstRowNum() + 1; i <= sheet
				.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row != null) {
				boolean isValidRow = false;
				ArrayList<String> valueList = new ArrayList<String>();
				for (int j = row.getFirstCellNum(); j <= row
						.getPhysicalNumberOfCells(); j++) {
					XSSFCell cell = row.getCell(j);
					if (cell != null) {
						String cellValue = null;
						if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
							if (DateUtil.isCellDateFormatted(cell)) {
								cellValue = new DataFormatter()
										.formatRawCellContents(
												cell.getNumericCellValue(), 0,
												"yyyy-MM-dd HH:mm:ss");
							} else {
								cellValue = String.valueOf(cell
										.getNumericCellValue());
							}
						} else {
							cellValue = cell.toString();
						}
						if (cellValue != null && cellValue.trim().length() <= 0) {
							cellValue = null;
						}
						valueList.add(j, cellValue);
						if (!isValidRow && cellValue != null
								&& cellValue.trim().length() > 0) {
							isValidRow = true;
						}
					}
				}

				// 第I行所有的列数据读取完毕，放入valuelist
				if (isValidRow && checkValue(valueList)) {
					ExcelInfoVo excelInfoVo = ExcelInfoVo.getExcelInfoVo();
					excelInfoVo.setData(valueList);
					list.add(excelInfoVo);
				}
			}
		}
		fis.close();
	}

	/**
	 * 获取单元格数据
	 * */
	private static String getCellValue(HSSFCell cell) {
		DecimalFormat df = new DecimalFormat("#");
		String cellValue = null;
		if (cell == null)
			return null;
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				cellValue = sdf.format(HSSFDateUtil.getJavaDate(cell
						.getNumericCellValue()));
				break;
			}
			cellValue = df.format(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_STRING:
			cellValue = String.valueOf(cell.getStringCellValue());
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			cellValue = String.valueOf(cell.getCellFormula());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			cellValue = null;
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			cellValue = String.valueOf(cell.getErrorCellValue());
			break;
		}
		if (cellValue != null && cellValue.trim().length() <= 0) {
			cellValue = null;
		}
		return cellValue;
	}
}
