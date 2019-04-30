package searching.slagalica;

import java.util.Arrays;

/**
 *	KonfiguracijaSlagalice TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */

public class KonfiguracijaSlagalice {

	private int[] conf;
	
	public KonfiguracijaSlagalice(int[] conf) {
		this.conf = conf;
	}
	
	public int[] getConfiguration() {
		return Arrays.copyOf(conf, conf.length);
	}
	
	public int indexOfSpaces() {
		for(int i = 0, s = conf.length; i < s ; ++i) {
			if(conf[i] == 0) {
				return i;
			}
		}
		return -1;
	}
	
	
	
	//TODO jel smijemo imati ovo buduci da nije navedeno u uputi
	public int indexOf(int n) {
		for(int i = 0, s = conf.length; i < s ; ++i) {
			if(conf[i] == n) {
				return i;
			}
		}
		return -1;
	}

	/**
	 *	{@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < 3 ; ++i) {
			sb.append(conf[i*3]);
			sb.append(" ");
			sb.append(conf[i*3 + 1]);
			sb.append(" ");
			sb.append(conf[i*3 + 2]);
			if(i != 3) {
				sb.append("\n");
			}
		}
		return sb.toString();
		
	}
	
	
	
}
