import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

/**
 * Created by pooja.sharma on 18/01/18.
 */
public class UpdatedReadAndWriteDom {

    String inputXMLPath = "/Users/pooja.sharma/project/untitled/FinalList.xlsx";
    //"/Users/pooja.sharma/project/untitled/dummy.xlsx"
    String outputXLPath = "/Users/pooja.sharma/project/untitled/UpdatedResultAllTest1.xlsx";


    public void readXLSXFile() throws IOException, ParserConfigurationException, SAXException {
        NewParser abc = new NewParser();
        //InputStream ExcelFileToRead = new FileInputStream("/Users/pooja.sharma/project/untitled/dummy.xlsx");
        InputStream ExcelFileToRead = new FileInputStream(inputXMLPath);

        XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

        XSSFWorkbook test = new XSSFWorkbook();

        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;
        XSSFCell cell;

        Iterator rows = sheet.rowIterator();

        int count = 10;
        while (rows.hasNext())
        {
            row=(XSSFRow) rows.next();
            Iterator cells = row.cellIterator();
//            while (cells.hasNext())
//            {
            //cells.next();
            cell=(XSSFCell) cells.next();
            String filePath = cell.getStringCellValue();
            Path p = Paths.get(filePath);
            String fileName = p.getFileName().toString();


            if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
            {
                //System.out.print(fileName);
                fileName = fileName.substring(0, (fileName.length()-5));  //hack- .java
                String teamname = abc.returnTeamOwnerPerClass(fileName);
                if(teamname==null){
                    System.out.print("NUll for filename"+fileName);
                    //break;
                }else {
                    //cells.next();
                    //cells.next();

                    row.createCell(3);
                    row.getCell(3).setCellValue(teamname);
                    count--;
                    //System.out.print(teamname);
                }

            }
            else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
            {
                System.out.print(cell.getNumericCellValue()+" ");
            }
            else
            {
                //U Can Handel Boolean, Formula, Errors
            }
            // }
            if(count==0) {
                FileOutputStream fos =new FileOutputStream(new java.io.File(outputXLPath));
                wb.write(fos);
                fos.close();
            }
            System.out.println();
        }
        //test.save
        ExcelFileToRead.close();

        FileOutputStream fos =new FileOutputStream(new java.io.File(outputXLPath));
        wb.write(fos);
        fos.close();

        System.out.println("Done");
    }

    public static void writeXLSXFile() throws IOException {

        String excelFileName = "/Users/pooja.sharma/project/untitled/UpdatedResult.xlsx";//name of excel file

        String sheetName = "Sheet1";//name of sheet

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName) ;

        //iterating r number of rows
        for (int r=0;r < 5; r++ )
        {
            XSSFRow row = sheet.createRow(r);

            //iterating c number of columns
            for (int c=0;c < 5; c++ )
            {
                XSSFCell cell = row.createCell(c);

                cell.setCellValue("Cell "+r+" "+c);
            }
        }

        FileOutputStream fileOut = new FileOutputStream(excelFileName);

        //write this workbook to an Outputstream.
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();
    }

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {

        // writeXLSFile();
        //readXLSFile();

        // writeXLSXFile();
        UpdatedReadAndWriteDom prw = new UpdatedReadAndWriteDom();
        prw.readXLSXFile();

    }

}

