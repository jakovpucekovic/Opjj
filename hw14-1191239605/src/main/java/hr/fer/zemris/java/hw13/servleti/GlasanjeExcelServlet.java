package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.p12.model.VotingCandidate;

/**
 *	Servlet which creates an xls document with current voting standings.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
@WebServlet(name="glasanjeExcelServlet", urlPatterns={"/glasanje-xls"})
public class GlasanjeExcelServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 *	{@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
//		List<VotingCandidate> results = VotingCandidate.loadCandidatesAndResults(definicijaFileName, rezultatiFileName);
//		results.sort((a,b)-> b.getVotes()-a.getVotes());
//		
//		HSSFWorkbook hwb = new HSSFWorkbook();
//		HSSFSheet sheet =  hwb.createSheet("Voting results");
//		HSSFRow rowhead =  sheet.createRow(0);
//		rowhead.createCell(0).setCellValue("Name");
//		rowhead.createCell(1).setCellValue("Votes");
//			
//		for(int i = 0, size = results.size() ; i < size; ++i) {
//			HSSFRow row = sheet.createRow(i + 1);
//			VotingCandidate entry = results.get(i);
//			row.createCell(0).setCellValue(entry.getName());
//			row.createCell(1).setCellValue(entry.getVotes());
//		}		
//		
//		resp.setContentType("application/octet-stream");
//		resp.setHeader("Content-Disposition", "attachment; filename=\"glasanje.xls\"");
//		hwb.write(resp.getOutputStream());
//		hwb.close();
	}
	
}
