package cz.jcu.prf.uai.javamugs.logic;

/**
 * Game score container.
 * @author Daniel Hryzbil
 */
public class Score {

	private long score;
	private double multiplier;

	/**
	 * Creates game score.
	 */
	public Score() {
		score = 0;
		multiplier = 1;
	}

	/**
	 * @return current score
	 */
	public long getScore() {
		return score;
	}

	/**
	 * @param score added score. It's multiplied by current multiplier.
	 */
	public void addScore(long number) {
		score += number*multiplier;
	}
	
	/**
	 * Sets multiplier to 1.
	 */
	public void resetMultiplier() {
		multiplier = 1;
	}
	
	/**
	 * @param multiplier sets multiplier to new value.
	 */
	public void addMultiplier(double number) {
		multiplier += number;
	}
	
	/**
	 * @return current multiplier
	 */
	public double getMultiplier() {
		return multiplier;
	}
}
