package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 *	BarChartServlet TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
@WebServlet(name="barchart", urlPatterns={"/reportImage"})
//TODO jel bi on trebao ici u WEB-INF tako da bude sakriven od korisnika
//TODO jel se on treba svaki puta generirati ovako, ili se treba spremiti pa loadati?
public class BarChartServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 *	{@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PieDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset, "Which OS are you using?");
	
		byte[] chartByte = ChartUtils.encodeAsPNG(chart.createBufferedImage(500, 270));	
		resp.setContentType("image/png");
		resp.getOutputStream().write(chartByte);
		resp.getOutputStream().flush();
		resp.getOutputStream().close();
	}
	
	/**
	 * Creates a sample dataset
	 */
	private  PieDataset createDataset() {
	    DefaultPieDataset result = new DefaultPieDataset();
	    result.setValue("Linux", 64);
	    result.setValue("Mac", 27);
	    result.setValue("Windows", 9);
	    return result;
		
	}
		
	/**
	 * Creates a chart
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {
		
	    JFreeChart chart = ChartFactory.createPieChart3D(
	        title,                  // chart title
	        dataset,                // data
	        true,                   // include legend
	        true,
	        false
	    );
		
	    PiePlot3D plot = (PiePlot3D) chart.getPlot();
	    plot.setStartAngle(290);
	    plot.setDirection(Rotation.CLOCKWISE);
	    plot.setForegroundAlpha(0.5f);
	    return chart;
	}
		
}
