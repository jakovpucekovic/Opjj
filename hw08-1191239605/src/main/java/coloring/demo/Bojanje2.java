package coloring.demo;

import java.util.Arrays;

import coloring.algorithms.Coloring;
import coloring.algorithms.Pixel;
import coloring.algorithms.SubspaceExploreUtil;
import marcupic.opjj.statespace.coloring.FillAlgorithm;
import marcupic.opjj.statespace.coloring.FillApp;
import marcupic.opjj.statespace.coloring.Picture;

/**
 *	Demo class which enables using the 3 custom algorithms
 *	which are bfs, dfs and bfsv to color the picture.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class Bojanje2 {

	/**
	 *	Runs the coloring application.
	 * 	@param args Not used here.
	 */
	public static void main(String[] args) {
		FillApp.run(FillApp.OWL, Arrays.asList(bfs, dfs, bfsv)); // ili FillApp.ROSE
	}

	
	/**
	 * 	A fill algorithm which uses the breadth-first-search.
	 */
	private static final FillAlgorithm bfs = new FillAlgorithm() {
		
		/**
		 * 	Returns the title of the algorithm.
		 * 	@return The title of the algorithm.
		 */
		@Override
		public String getAlgorithmTitle() {
			return "moj bfs";
		}
		
		/**
		 * 	Fills the given picture with the given color starting
		 * 	from the given position.
		 * 	@param x X-coordinate of the starting pixel.
		 * 	@param y Y-coordinate of the starting pixel.
		 * 	@param color The color to fill.
		 * 	@param picture Picture to color.
		 */
		@Override
		public void fill(int x, int y, int color, Picture picture) {
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.bfs(col.initialState, col.process, col.succ, col.acceptable);
		}
	};
	
	/**
	 * 	A fill algorithm which uses the depth-first-search.
	 */
	private static final FillAlgorithm dfs = new FillAlgorithm() {
		
		/**
		 * 	Returns the title of the algorithm.
		 * 	@return The title of the algorithm.
		 */
		@Override
		public String getAlgorithmTitle() {
			return "moj dfs";
		}
		
		/**
		 * 	Fills the given picture with the given color starting
		 * 	from the given position.
		 * 	@param x X-coordinate of the starting pixel.
		 * 	@param y Y-coordinate of the starting pixel.
		 * 	@param color The color to fill.
		 * 	@param picture Picture to color.
		 */
		@Override
		public void fill(int x, int y, int color, Picture picture) {
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.dfs(col.initialState, col.process, col.succ, col.acceptable);
		}
	};
	
	/**
	 * 	A fill algorithm which uses the breadth-first-search without repetition.
	 */
	private static final FillAlgorithm bfsv = new FillAlgorithm() {
		
		/**
		 * 	Returns the title of the algorithm.
		 * 	@return The title of the algorithm.
		 */
		@Override
		public String getAlgorithmTitle() {
			return "moj bfsv";
		}
		
		/**
		 * 	Fills the given picture with the given color starting
		 * 	from the given position.
		 * 	@param x X-coordinate of the starting pixel.
		 * 	@param y Y-coordinate of the starting pixel.
		 * 	@param color The color to fill.
		 * 	@param picture Picture to color.
		 */
		@Override
		public void fill(int x, int y, int color, Picture picture) {
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.bfsv(col.initialState, col.process, col.succ, col.acceptable);
		}
	};
}
