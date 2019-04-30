package coloring.demo;

import java.util.Arrays;

import coloring.algorithms.Coloring;
import coloring.algorithms.Pixel;
import coloring.algorithms.SubspaceExploreUtil;
import marcupic.opjj.statespace.coloring.FillAlgorithm;
import marcupic.opjj.statespace.coloring.FillApp;
import marcupic.opjj.statespace.coloring.Picture;

/**
 *	Bojanje1 TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class Bojanje2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FillApp.run(FillApp.OWL, Arrays.asList(bfs, dfs, bfsv)); // ili FillApp.ROSE
	}

	
	private static final FillAlgorithm bfs = new FillAlgorithm() {
		
		@Override
		public String getAlgorithmTitle() {
			return "moj bfs";
		}
		
		@Override
		public void fill(int x, int y, int color, Picture picture) {
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.bfs(col.initialState, col.process, col.succ, col.acceptable);
		}
	};
	
	private static final FillAlgorithm dfs = new FillAlgorithm() {
		
		@Override
		public String getAlgorithmTitle() {
			return "moj dfs";
		}
		
		@Override
		public void fill(int x, int y, int color, Picture picture) {
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.dfs(col.initialState, col.process, col.succ, col.acceptable);
		}
	};
	
private static final FillAlgorithm bfsv = new FillAlgorithm() {
		
		@Override
		public String getAlgorithmTitle() {
			return "moj bfsv";
		}
		
		@Override
		public void fill(int x, int y, int color, Picture picture) {
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.bfsv(col.initialState, col.process, col.succ, col.acceptable);
		}
	};
}
