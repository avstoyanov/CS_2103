import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.sound.midi.*;

/**
 * Implements a simulated piano with 36 keys.
 */
public class Piano extends JPanel {
	// DO NOT MODIFY THESE CONSTANTS
	public static int START_PITCH = 48;
	public static int WHITE_KEY_WIDTH = 40;
	public static int BLACK_KEY_WIDTH = WHITE_KEY_WIDTH/2;
	public static int WHITE_KEY_HEIGHT = 200;
	public static int BLACK_KEY_HEIGHT = WHITE_KEY_HEIGHT/2;
	public static int NUM_WHITE_KEYS_PER_OCTAVE = 7;
	public static int NUM_OCTAVES = 3;
	public static int NUM_WHITE_KEYS = NUM_WHITE_KEYS_PER_OCTAVE * NUM_OCTAVES;
	public static int WIDTH = NUM_WHITE_KEYS * WHITE_KEY_WIDTH;
	public static int HEIGHT = WHITE_KEY_HEIGHT;
		
	private java.util.List<Key> _keys = new ArrayList<>();
	private Receiver _receiver;
	private PianoMouseListener _mouseListener;

	//Ghost key: Halloween special!! It makes all the math conveniently work and makes the program more efficient.
	public Key NULL_KEY = new Key(new Polygon(new int[]{}, new int[]{}, 0), 0, this, Color.WHITE);

	/**
	 * Returns the list of keys in the piano.
	 * @return the list of keys.
	 */
	public java.util.List<Key> getKeys () {
		return _keys;
	}

	/**
	 * Sets the MIDI receiver of the piano to the specified value.
	 * @param receiver the MIDI receiver 
	 */
	public void setReceiver (Receiver receiver) {
		_receiver = receiver;
	}

	/**
	 * Returns the current MIDI receiver of the piano.
	 * @return the current MIDI receiver 
	 */
	public Receiver getReceiver () {
		return _receiver;
	}

	// DO NOT MODIFY THIS METHOD.
	/**
	 * @param receiver the MIDI receiver to use in the piano.
	 */
	public Piano (Receiver receiver) {
		// Some Swing setup stuff; don't worry too much about it.
		setFocusable(true);
		setLayout(null);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		setReceiver(receiver);
		_mouseListener = new PianoMouseListener(_keys);
		addMouseListener(_mouseListener);
		addMouseMotionListener(_mouseListener);
		makeKeys();
	}

	/**
	 * Returns the PianoMouseListener associated with the piano.
	 * @return the PianoMouseListener associated with the piano.
	 */
	public PianoMouseListener getMouseListener () {
		return _mouseListener;
	}

	// TODO: implement this method. You should create and use several helper methods to do so.
	/**
	 * Instantiate all the Key objects with their correct polygons and pitches, and
	 * add them to the _keys array.
	 */
	private void makeKeys () {
		int bKeyOffset = BLACK_KEY_WIDTH/2;
		int currentPitch = START_PITCH;
		int xWhite = 0;

		addWhiteKey(xWhite, currentPitch);
		for(int i=1; i < NUM_WHITE_KEYS; i++){
			xWhite = WHITE_KEY_WIDTH*i;

			currentPitch++;

			if(!(i % NUM_WHITE_KEYS_PER_OCTAVE == 3 || i % NUM_WHITE_KEYS_PER_OCTAVE == 0)){
				addKey(xWhite, xWhite + WHITE_KEY_WIDTH, WHITE_KEY_HEIGHT, currentPitch+1);
				addKey(xWhite - bKeyOffset,
						xWhite + bKeyOffset,
						BLACK_KEY_HEIGHT, currentPitch);
				currentPitch++;
			} else{
				addWhiteKey(xWhite, currentPitch);
			}
		}
	}


	private void addWhiteKey(int x, int pitch){
		addKey(x, x + WHITE_KEY_WIDTH, WHITE_KEY_HEIGHT, pitch);
		_keys.add(NULL_KEY);
	}

	/**
	 * adds the key with the specified x coords, height, and pitch to the _keys array
	 * @param x1 top left x coordinate
	 * @param x2 top right x coordinate
	 * @param height height of key, also determines color of the key
	 * @param pitch
	 */
	private void addKey(int x1, int x2, int height, int pitch){
		Polygon polygon = new Polygon(new int[]{x1, x2, x2, x1},
									  new int[]{0, 0, height, height},
							  4);
		_keys.add(new Key(polygon, pitch, this, (height == BLACK_KEY_HEIGHT ? Color.BLACK : Color.WHITE)));
	}

	// DO NOT MODIFY THIS METHOD.
	@Override
	/**
	 * Paints the piano and all its constituent keys.
	 * @param g the Graphics object to use for painting.
	 */
	public void paint (Graphics g) {
		// Delegates to all the individual keys to draw themselves.
		for (Key key: _keys) {
			key.paint(g);
		}
	}
}
