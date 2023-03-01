/* This will handle the "Hot Key" system. */

package Main;

import logic.Control;
import timer.stopWatchX;

public class KeyProcessor{
	// Static Fields
	private static char last = ' ';			// For debouncing purposes
	private static stopWatchX sw = new stopWatchX(17);
	
	// Static Method(s)
	public static void processKey(char key){
		if(key == ' ') {
			Main.key = ' ';
			return;
		}

		if(key == last)
			if(sw.isTimeUp() == false) {
				Main.key = ' ';
				return;
			}

		last = key;
		sw.resetWatch();

		switch(key){
			case '%':								// ESC key
				System.exit(0);
				break;
			case 'd':
				Main.key = 'd';
				break;
			case 'a':
				Main.key = 'a';
				break;
			case '$':
				Main.key = '$';
				break;
			case 'n':
				Main.key = 'n';
				break;
			case 'm':
				// For mouse coordinates
				Control.isMouseCoordsDisplayed = !Control.isMouseCoordsDisplayed;
				break;
		}
	}
}