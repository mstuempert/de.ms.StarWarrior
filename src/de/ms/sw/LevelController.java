package de.ms.sw;

import java.util.Timer;
import java.util.TimerTask;

public class LevelController {
	
	private static final long LEVEL_RAISE_TIME_MILLIS = 5000;
	
	private Timer timer;
	
	private TimerTask timerTask;
	
	private int currentLevel;
	
	private long millisToWait;

	public LevelController() {
		this.timer = new Timer();
		reset(1);
	}
	
	public int getCurrentLevel() {
		return this.currentLevel;
	}
	
	public float getEnemiesPerSecond() {
		return 0.1f * this.currentLevel;
	}
	
	public void destroy() {
		stop();
	}
	
	public void reset(int startingLevel) {
		stop();
		this.currentLevel = startingLevel;
		this.millisToWait = LEVEL_RAISE_TIME_MILLIS;
	}
	
	public void start() {
		if (this.timerTask == null) {
			this.timerTask = new TimerTask() {
				@Override
				public void run() {
					raiseLevel();
				}
			};
			this.timer.scheduleAtFixedRate(this.timerTask, this.millisToWait, LEVEL_RAISE_TIME_MILLIS);
		}
	}

	public void stop() {
		if (this.timerTask != null) {
			this.millisToWait = this.timerTask.scheduledExecutionTime()-System.currentTimeMillis();
			this.timerTask.cancel();
			this.timer.purge();
		}
	}
	
	protected void raiseLevel() {
		this.currentLevel++;
	}

}
