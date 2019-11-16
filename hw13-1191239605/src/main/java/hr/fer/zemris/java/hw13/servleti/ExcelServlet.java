package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *	Servlet which creates a sample xls document which contains numbers in the given range
 *	and their powers.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
@WebServlet(name="excelServlet", urlPatterns={"/powers"})
public class ExcelServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 *	{@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		//get parameters
		String a = req.getParameter("a");
		String b = req.getParameter("b");
		String n = req.getParameter("n");
		
		int varA, varB, varN;
		try {
			varA = Integer.parseInt(a);
			varB = Integer.parseInt(b);
			varN = Integer.parseInt(n);
		} catch(NumberFormatException ex) {
			resp.sendRedirect(req.getContextPath() + "/error.jsp");
			return;
		}
		if(varA < -100 || varA > 100 || varB < -100 || varB > 100 || varN < 1 || varN > 5) {
			resp.sendRedirect(req.getContextPath() + "/error.jsp");
			return;
		}
		
		//create xls spreadsheet
		HSSFWorkbook hwb = new HSSFWorkbook();
		for(int i = 1; i <= varN; ++i) {
			HSSFSheet sheet =  hwb.createSheet(Integer.toString(i));
			HSSFRow rowhead =  sheet.createRow(0);
			rowhead.createCell(0).setCellValue("value");
			rowhead.createCell(1).setCellValue("power");
			
			for(int j = varA ; j <= varB; ++j) {
				HSSFRow row = sheet.createRow(j - varA + 1);
				row.createCell(0).setCellValue(j);
				row.createCell(1).setCellValue(Math.pow(j, i));
			}		
		}
		
		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment; filename=\"powers.xls\"");
		hwb.write(resp.getOutputStream());
		hwb.close();
	}
	
}
