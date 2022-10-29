import javax.swing.*;
import java.awt.event.*;
import javax.sound.midi.*;
import java.util.*;

/**
 * Handles mouse press, release, and drag events on the Piano.
 */
public class PianoMouseListener extends MouseAdapter {
	// You are free to add more instance variables if you wish.
	private List<Key> _keys;
	private Key curKey;

	/**
	 * @param keys the list of keys in the piano.
	 */
	public PianoMouseListener (List<Key> keys) {
		_keys = keys;
	}

	/**
	 * determines the key the mouse is on based on the x and y coordinates, assuming they lie within the keyboard
	 * @param x coordinate of mouse
	 * @param y coordinate of mouse
	 * @return key
	 */
	private Key currentKey(int x, int y){
		int k = x/Piano.WHITE_KEY_WIDTH * 2;
		System.out.println(k + " " + _keys.get(k).get_pitch());
		if(y < Piano.BLACK_KEY_HEIGHT){
			if(k > 0 && _keys.get(k+1).getPolygon().contains(x, y)){
				return _keys.get(k+1);
			}
			if(k < _keys.size() - 2 && _keys.get(k+3).getPolygon().contains(x, y)){
				return _keys.get(k+3);
			}
		}
		return _keys.get(k);
	}
	// TODO implement this method.
	@Override
	/**
	 * This method is called by Swing whenever the user drags the mouse.
	 * @param e the MouseEvent containing the (x,y) location, relative to the upper-left-hand corner
	 * of the entire piano, of where the mouse is currently located.
	 */
	public void mouseDragged (MouseEvent e) {
		if( e.getY() >= 0 && e.getY() < Piano.HEIGHT && e.getX() >= 0 && e.getX() < Piano.WIDTH &&
				!curKey.getPolygon().contains(e.getX(), e.getY())){
			curKey.play(false);
			mousePressed(e);
		}
	}

	// TODO implement this method.
	@Override
	/**
	 * This method is called by Swing whenever the user presses the mouse.
	 * @param e the MouseEvent containing the (x,y) location, relative to the upper-left-hand corner
	 * of the entire piano, of where the mouse is currently located.
	 */
	public void mousePressed (MouseEvent e) {
		// To test whether a certain key received the mouse event, you could write something like:
		//	if (key.getPolygon().contains(e.getX(), e.getY())) {
		// To turn a key "on", you could then write:
		//      key.play(true);  // Note that the key should eventually be turned off
		curKey = currentKey(e.getX(), e.getY());
		curKey.play(true);
	}

	// TODO implement this method.
	@Override
	/**
	 * This method is called by Swing whenever the user releases the mouse.
	 * @param e the MouseEvent containing the (x,y) location, relative to the upper-left-hand corner
	 * of the entire piano, of where the mouse is currently located.
	 */
	public void mouseReleased (MouseEvent e) {
		curKey.play(false);
	}
}
