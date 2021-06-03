package com.emsi.ecommerce.service;

import com.emsi.ecommerce.domaine.AchatConverter;
import com.emsi.ecommerce.domaine.AchatVo;
import com.emsi.ecommerce.domaine.ArticleConverter;
import com.emsi.ecommerce.domaine.ArticleVo;
import com.emsi.ecommerce.domaine.UserVo;
import com.emsi.ecommerce.service.model.Article;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExcelServiceImpl implements IExcelService {

	public static final String EXCEL_PATH= "C:\\ecommerceApp\\ExcelFiles\\";
	@Autowired
	IUserService userService;
	
	@Autowired
	IAchatService achatService;
	@Autowired
	IArticleService articleService;
	
	
	
	private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    
    @Transactional
	@Override
	public void panierToExcel(UserVo clientVo) {
    	workbook = new XSSFWorkbook();
		
        try {
		
			List<AchatVo> list = achatService.getAchatsByClient(clientVo.getId());
			writeHeaderLine();
			writeDataLines(list);
			
			for (AchatVo achatVo : list) {
				ArticleVo articleVo=achatVo.getArticle();
				articleVo.setQtEnStock(articleVo.getQtEnStock()-achatVo.getQuantiteAchat());
				articleService.save(articleVo);
				
			}
			achatService.deleteByClient(clientVo.getId());
			File file=new File(EXCEL_PATH+clientVo.getUsername()+".xlsx");
			FileOutputStream outputStream = new FileOutputStream(file);
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

	}

	  private void writeHeaderLine() {
	        sheet = workbook.createSheet("Achats"+ZonedDateTime.now().toInstant().toEpochMilli());
	         
	        Row row = sheet.createRow(0);
	         
	        CellStyle style = workbook.createCellStyle();
	        XSSFFont font = workbook.createFont();
	        font.setBold(true);
	        font.setFontHeight(16);
	        style.setFont(font);
	         
	        createCell(row, 0, "Référence", style);      
	        createCell(row, 1, "Description", style);       
	        createCell(row, 2, "Prix Unitaire", style);    
	        createCell(row, 3, "Quantité", style);
	        createCell(row, 4, "Prix", style);
	         
	    }
	     
	    private void writeDataLines(List<AchatVo> listAchats) {
	        int rowCount = 1;
	 
	        CellStyle style = workbook.createCellStyle();
	        XSSFFont font = workbook.createFont();
	        font.setFontHeight(14);
	        style.setFont(font);
	                 
	        for (AchatVo achatVo : listAchats) {
	            Row row = sheet.createRow(rowCount++);
	            int columnCount = 0;
	             
	            createCell(row, columnCount++, achatVo.getArticle().getReference(), style);
	            createCell(row, columnCount++, achatVo.getArticle().getDesignation(), style);
	            createCell(row, columnCount++, achatVo.getArticle().getPrixUnitaire(), style);
	            createCell(row, columnCount++, achatVo.getQuantiteAchat(), style);
	            createCell(row, columnCount++, achatVo.getPrixAchat(), style);
	             
	        }
	    }
	    
		  private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		        sheet.autoSizeColumn(columnCount);
		        Cell cell = row.createCell(columnCount);
		        if (value instanceof Integer) {
		            cell.setCellValue((Integer) value);
		        } else if (value instanceof Boolean) {
		            cell.setCellValue((Boolean) value);
		        }
		        else if (value instanceof Double) {
		            cell.setCellValue((Double) value);
		        }
		        else {
		            cell.setCellValue((String) value);
		        }
		        cell.setCellStyle(style);
		    }
}
