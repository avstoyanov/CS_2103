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
	private Key curKey = new Key(); //initializing in order to not have to test if it's null


	/**
	 * @param keys the list of keys in the piano.
	 */
	public PianoMouseListener (List<Key> keys) {
		_keys = keys;
	}

	/**
	 * determines which key the mouse is on based on the x and y coordinates,
	 * assuming that white keys are on even indexes and black keys are in odd indexes.
	 * The array stores the keys as: White key, blank key or black key BEFORE it, then the next white key and so on.
	 * Two adjacent white keys have a blank key between them, and black keys are drawn after white keys
	 * so that they can overlay the white keys to simplify key generation logic.
	 * @param x coordinate of mouse
	 * @param y coordinate of mouse
	 * @return key
	 */
	private Key currentKey(int x, int y){
		int k = x/Piano.WHITE_KEY_WIDTH * 2;

		if(y < Piano.BLACK_KEY_HEIGHT){
			if(k > 0 && _keys.get(k+1).getPolygon().contains(x, y)) {
				return _keys.get(k+1); // left black key
			}
			if(k < _keys.size() - 2 && _keys.get(k+3).getPolygon().contains(x, y)){
				return _keys.get(k+3); // right black key
			}
		}
		return _keys.get(k);
	}
	@Override
	/**
	 * This method is called by Swing whenever the user drags the mouse.
	 * @param e the MouseEvent containing the (x,y) location, relative to the upper-left-hand corner
	 * of the entire piano, of where the mouse is currently located.
	 */
	public void mouseDragged (MouseEvent e) {
		if( e.getY() >= 0 && e.getY() < Piano.HEIGHT && e.getX() >= 0 && e.getX() < Piano.WIDTH &&
				!(currentKey(e.getX(), e.getY())).getState()){
			turnOffKey(curKey);
			mousePressed(e);
		}
	}


	@Override
	/**
	 * This method is called by Swing whenever the user presses the mouse.
	 * @param e the MouseEvent containing the (x,y) location, relative to the upper-left-hand corner
	 * of the entire piano, of where the mouse is currently located.
	 *          if a new key is pressed while a key is already pressed, the old key will be released
	 *          and the new key will be pressed. If the same key is pressed twice without releasing,
	 *          the second press will be ignored.
	 */
	public void mousePressed (MouseEvent e) {
		// To test whether a certain key received the mouse event, you could write something like:
		//	if (key.getPolygon().contains(e.getX(), e.getY())) {
		// To turn a key "on", you could then write:
		//      key.play(true);  // Note that the key should eventually be turned off

		if(e.getY() >= 0 && e.getY() < Piano.HEIGHT && e.getX() >= 0 && e.getX() < Piano.WIDTH) {
			Key tempKey = currentKey(e.getX(), e.getY());
			if(!tempKey.getState()) {
				turnOffKey(curKey);
				curKey = tempKey;
				curKey.play(true);
			}
		}
	}


	@Override
	/**
	 * This method is called by Swing whenever the user releases the mouse.
	 * @param e the MouseEvent containing the (x,y) location, relative to the upper-left-hand corner
	 * of the entire piano, of where the mouse is currently located.
	 */
	public void mouseReleased (MouseEvent e) {
		turnOffKey(curKey);
	}

	//sets the state of the key to off(false)
	private void turnOffKey(Key k){
		if(k.getState()) k.play(false);
	}
}
