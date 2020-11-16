package MainJAVAMethodClasses;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.app.qa.resources.Utils;

public class dataDrivenExcel {
	
	public ArrayList<String> getExcelData(String desiredFirstColumnCellValue) throws IOException {
		
		//Identify the Column "TestCase"
		//"Identify the "Purchase" row from the cloumn
		//Extract all data of the "Purchase" row
		XSSFSheet xsheet;		
		FileInputStream fis= new FileInputStream(Utils.getGlobalValue("excelTestDataPath"));
		XSSFWorkbook xwbook = new XSSFWorkbook(fis);
		
		int sheetCount = xwbook.getNumberOfSheets();
		ArrayList<String> aList = new ArrayList<String>();
        //	System.out.println(sheetCount);
		
		for(int i=0;i<sheetCount;i++) {
			
			if(xwbook.getSheetAt(i).getSheetName().equalsIgnoreCase(Utils.getGlobalValue("sheetName")))
			{
				xsheet= xwbook.getSheetAt(i);
				System.out.println(xwbook.getSheetAt(i).getSheetName());
				
				//Identify the column
				
				Iterator<Row> xRows=xsheet.iterator();// for rows
				Row firstRow=xRows.next(); //for first row
				Iterator<Cell> firstRowCells=firstRow.cellIterator();
				int k=0;
				int column =0;
				
				while(firstRowCells.hasNext()) {
					Cell ce=firstRowCells.next();
					if(ce.getStringCellValue().equalsIgnoreCase(Utils.getGlobalValue("columnName")))
					{
						System.out.println(ce.getStringCellValue());
						column =k;
					}
					
					k++;
					
				}
				
				System.out.println(column);
				
				while(xRows.hasNext()) {
					
					Row r = xRows.next();
					
					if(r.getCell(column).getStringCellValue().contains(desiredFirstColumnCellValue)) {
						Iterator<Cell> cv= r.cellIterator();
						
						while(cv.hasNext()){
							
							Cell cvc = cv.next();
							
							if(cvc.getCellTypeEnum()==CellType.STRING) {
								aList.add(cvc.getStringCellValue());
							}
							else{
								aList.add(NumberToTextConverter.toText(cvc.getNumericCellValue()));
								
							}
							
						}
					}
				}
				
				
			}
		}

		return aList;

	}

}
