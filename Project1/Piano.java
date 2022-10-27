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
		int bk1 = - BLACK_KEY_WIDTH/2;
		int bk2 = BLACK_KEY_WIDTH/2;
		int wk1 = 0;
		int wk2 = WHITE_KEY_WIDTH;
		int whiteKeyHeight = WHITE_KEY_HEIGHT - 1; //fixes display issue where bottom of key not outlined


		int[] XCoords = new int[] {
				wk1,
				wk2,
				wk2,
				wk1
		};
		int[] YCoords = new int[] {
				0,
				0,
				whiteKeyHeight,
				whiteKeyHeight
		};

		Polygon polygon = new Polygon(XCoords, YCoords, XCoords.length);
		Key key = new Key(polygon, START_PITCH, this, Color.WHITE);
		_keys.add(key);

		for(int i=0; i < NUM_WHITE_KEYS; i++){

			wk1 += WHITE_KEY_WIDTH;
			wk2 += WHITE_KEY_WIDTH;

			XCoords = new int[] {
				wk1,
				wk2,
				wk2,
				wk1
			};
		    YCoords = new int[] {
			    0,
				0,
				whiteKeyHeight,
				whiteKeyHeight
			};

			polygon = new Polygon(XCoords, YCoords, XCoords.length);
			key = new Key(polygon, START_PITCH, this, Color.WHITE);
			_keys.add(key);

			XCoords = new int[] {
				bk1,
				bk2,
				bk2,
				bk1
		    };
		    YCoords = new int[] {
			    0,
				0,
				BLACK_KEY_HEIGHT,
				BLACK_KEY_HEIGHT
			};

			polygon = new Polygon(XCoords, YCoords, XCoords.length);
			key = new Key(polygon, START_PITCH, this, Color.BLACK);

			if(!(i % NUM_WHITE_KEYS_PER_OCTAVE == 3 || i % NUM_WHITE_KEYS_PER_OCTAVE == 0)){
				_keys.add(key);
			}

			bk1 += WHITE_KEY_WIDTH;
			bk2 += WHITE_KEY_WIDTH;
		}
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
