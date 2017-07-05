package cz.jcu.prf.uai.javamugs.logic;

import junit.framework.TestCase;

/**
 * GameReport class test.
 * @author Daniel Hryzbil
 */
public class GameReportTest extends TestCase {
	public void testGameReportContainer() {
		Score score = new Score();
		score.addMultiplier(1.0);
		score.addScore(200);
		Chord chordToDraw = new Chord(true, false, true, false, true);
		Chord chordExpected = new Chord(false, true, false, true, false);
		GameReport report = new GameReport(score, chordToDraw, chordExpected);
		assertNotNull(report);
		assertEquals(chordToDraw.getChords(), report.getChordToDraw().getChords());
		assertEquals(chordExpected.getChords(), report.getExpectedChord().getChords());
		assertEquals(score.getScore(), report.getScore());
		assertEquals(score.getMultiplier(), report.getMultiplier());
	}
}