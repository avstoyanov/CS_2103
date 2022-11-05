import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import javax.sound.midi.*;
import java.awt.event.*;
import org.junit.jupiter.api.*;

/**
 * Contains a set of unit tests for the Piano class.
 */
class PianoTester {
	private TestReceiver _receiver;
	private Piano _piano;
	private PianoMouseListener _mouseListener;

	private MouseEvent makeMouseEvent (int x, int y) {
		return new MouseEvent(_piano, 0, 0, 0, x, y, 0, false);
	}

	@BeforeEach
	void setup () {
		// A new TestReceiver will be created before running *each*
		// test. Hence, the "turn on" and "turn off" counts will be
		// reset to 0 before *each* test.
		_receiver = new TestReceiver();
		_piano = new Piano(_receiver);
		_mouseListener = _piano.getMouseListener();
	}

	@Test
	void testClickUpperLeftMostPixel () {
		// Pressing the mouse should cause the key to turn on.
		_mouseListener.mousePressed(makeMouseEvent(0, 0));
		assertTrue(_receiver.isKeyOn(Piano.START_PITCH));
	}

	@Test
	void testKeyVertex () {
		_mouseListener.mousePressed(makeMouseEvent(Piano.WHITE_KEY_WIDTH, Piano.BLACK_KEY_HEIGHT));
		assertTrue(_receiver.isKeyOn(50));
	}

	@Test
	void testWhiteMiddleKeyLeftEdge () {
		_mouseListener.mousePressed(makeMouseEvent(Piano.WHITE_KEY_WIDTH + Piano.BLACK_KEY_WIDTH/2, 0));
		assertTrue(_receiver.isKeyOn(50));
	}

	@Test
	void testBlackKeyLeftEdge () {
		_mouseListener.mousePressed(makeMouseEvent(Piano.WHITE_KEY_WIDTH - Piano.BLACK_KEY_WIDTH/2, 0));
		assertTrue(_receiver.isKeyOn(49));
	}

	@Test
	void testWhiteRightKeyLeftEdge () {
		_mouseListener.mousePressed(makeMouseEvent(Piano.WHITE_KEY_WIDTH*2 + Piano.BLACK_KEY_WIDTH/2, 0));
		assertTrue(_receiver.isKeyOn(52));
	}

	@Test
	void testWhiteLeftKeyLowerRightEdge () {
		_mouseListener.mousePressed(makeMouseEvent(Piano.WHITE_KEY_WIDTH, Piano.BLACK_KEY_HEIGHT + 1));
		assertTrue(_receiver.isKeyOn(50));
	}

	@Test
	void testDragWithinKey () {
		// Test that pressing and dragging the mouse *within* the same key
		// should cause the key to be turned on only once, not multiple times.
		// Use makeMouseEvent and TestReceiver.getKeyOnCount.

		_mouseListener.mousePressed(makeMouseEvent(0, 0));
		_mouseListener.mouseDragged(makeMouseEvent(10,140));
		assertEquals(_receiver.getKeyOnCount(48),1);
	}

	@Test
	void testDragOutsideKey () {
		// Test pressing and dragging the mouse to another key
		_mouseListener.mousePressed(makeMouseEvent(0,0));
		_mouseListener.mouseDragged(makeMouseEvent(40,140));
		assertTrue(_receiver.isKeyOn(50));
	}

	@Test
	void testRightEdge () {
		_mouseListener.mousePressed(makeMouseEvent(Piano.WIDTH, 0));
		assertFalse(_receiver.isKeyOn(83));
	}

	@Test
	void testBottomEdge () {
		_mouseListener.mousePressed(makeMouseEvent(0, Piano.HEIGHT));
		assertFalse(_receiver.isKeyOn(48));
	}

	@Test
	void testRepeatedOn () {
		_mouseListener.mousePressed(makeMouseEvent(0, Piano.BLACK_KEY_HEIGHT));
		_mouseListener.mousePressed(makeMouseEvent(0, Piano.BLACK_KEY_HEIGHT));
		assertEquals(1, _receiver.getKeyOnCount(48));
	}

	@Test
	void testNoPressOff () {
		_mouseListener.mouseReleased(makeMouseEvent(0, Piano.BLACK_KEY_HEIGHT));
		_mouseListener.mouseReleased(makeMouseEvent(0, Piano.BLACK_KEY_HEIGHT));
		assertEquals(0, _receiver.getKeyOnCount(48));
	}

	@Test
	void testMultipleOff () {
		_mouseListener.mousePressed(makeMouseEvent(0, Piano.BLACK_KEY_HEIGHT));
		_mouseListener.mouseReleased(makeMouseEvent(0, Piano.BLACK_KEY_HEIGHT));
		_mouseListener.mouseReleased(makeMouseEvent(0, Piano.BLACK_KEY_HEIGHT));
		assertEquals(1, _receiver.getKeyOnCount(48));
	}

	@Test
	void testKeys () {
		_mouseListener.mousePressed(makeMouseEvent(Piano.WHITE_KEY_WIDTH, 0));
		assertTrue(_receiver.isKeyOn(49));
		_mouseListener.mouseReleased(makeMouseEvent(Piano.WHITE_KEY_WIDTH, 0));
		assertFalse(_receiver.isKeyOn(49));

		_mouseListener.mousePressed(makeMouseEvent(Piano.WHITE_KEY_WIDTH*2, 0));
		assertTrue(_receiver.isKeyOn(51));
		_mouseListener.mouseReleased(makeMouseEvent(Piano.WHITE_KEY_WIDTH*2, 0));
		assertFalse(_receiver.isKeyOn(51));

		_mouseListener.mousePressed(makeMouseEvent(0, Piano.BLACK_KEY_HEIGHT));
		assertTrue(_receiver.isKeyOn(48));
		_mouseListener.mouseReleased(makeMouseEvent(0, Piano.BLACK_KEY_HEIGHT));
		assertFalse(_receiver.isKeyOn(48));

		_mouseListener.mousePressed(makeMouseEvent(Piano.WHITE_KEY_WIDTH*5+3, Piano.BLACK_KEY_HEIGHT));
		assertTrue(_receiver.isKeyOn(57));
		_mouseListener.mouseReleased(makeMouseEvent(Piano.WHITE_KEY_WIDTH*5+3, Piano.BLACK_KEY_HEIGHT));
		assertFalse(_receiver.isKeyOn(57));

		_mouseListener.mousePressed(makeMouseEvent(Piano.WHITE_KEY_WIDTH*20+1, Piano.BLACK_KEY_HEIGHT));
		assertTrue(_receiver.isKeyOn(83));
		_mouseListener.mouseReleased(makeMouseEvent(Piano.WHITE_KEY_WIDTH*20+1, Piano.BLACK_KEY_HEIGHT));
		assertFalse(_receiver.isKeyOn(83));
	}

	@Test
	void testMultipleOn () {
		_mouseListener.mousePressed(makeMouseEvent(0, Piano.BLACK_KEY_HEIGHT));
		_mouseListener.mousePressed(makeMouseEvent(Piano.WHITE_KEY_WIDTH*7, Piano.BLACK_KEY_HEIGHT));
		assertFalse(_receiver.isKeyOn(48));
		assertTrue(_receiver.isKeyOn(60));
	}

}
