/*
 * File name:  AudioHandler.java
 *
 * Programmer : Jake Botka
 *
 * Date: Aug 11, 2020
 *
 */
package com.botka.data.set.visualization.app;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import main.com.botka.data.set.visualization.api.sound.engine.IAudioListener;
import main.com.botka.data.set.visualization.api.sound.engine.IPlayAudio;

/**
 * 
 * Handler class to handle feeding audio information to the system.
 *
 * @author Jake Botka
 *
 */
public class AudioHandler implements IPlayAudio, IAudioListener {
	/**
	 * 
	 */
	@Override
	public boolean playAudio(File file) {
		System.out.println("Sound egine has reuest implementation to play audio");
		try {
			Media sound = new Media(file.toURI().toString());
			MediaPlayer player = new MediaPlayer(sound);
			player.play();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 
	 */
	@Override
	public void onAudioPlayed(long id) {
		System.out.println("Audio played, ID: " + String.valueOf(id));
	}

	/**
	 * 
	 */
	@Override
	public void onAudioCompleted(long id) {
		// TODO Auto-generated method stub

	}

}
