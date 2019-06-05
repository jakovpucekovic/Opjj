package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;
import java.util.List;

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

import hr.fer.zemris.java.hw13.classes.VotingCandidate;

/**
 *	BarChartServlet TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
@WebServlet(name="glasanjePiechart", urlPatterns={"/glasanje-grafika"})
public class GlasanjePieChartServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 *	{@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PieDataset dataset = createDataset(req);
		JFreeChart chart = createChart(dataset, "Rezultati glasovanja");
	
		byte[] chartByte = ChartUtils.encodeAsPNG(chart.createBufferedImage(500, 270));	
		resp.setContentType("image/png");
		resp.getOutputStream().write(chartByte);
		resp.getOutputStream().flush();
		resp.getOutputStream().close();
	}
	
	/**
	 * Creates a sample dataset
	 */
	private  PieDataset createDataset(HttpServletRequest req) throws IOException {
	    String rezultatiFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		String definicijaFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		
		List <VotingCandidate> results = VotingCandidate.loadCandidatesAndResults(definicijaFileName, rezultatiFileName);
		
		DefaultPieDataset dataset = new DefaultPieDataset();
		for(var entry : results) {
			dataset.setValue(entry.getName(), entry.getVotes());
		}
		
		return dataset;
		
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

