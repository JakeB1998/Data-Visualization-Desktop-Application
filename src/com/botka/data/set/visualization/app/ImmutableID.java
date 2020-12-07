/**
 * File Name: ImmutableID.java
 * Programmer: Jake Botka
 * Date Created: Dec 6, 2020
 *
 */
package com.botka.data.set.visualization.app;

import main.org.botka.openjdk.api.addon.security.IDGenerator;

/**
 * @author Jake Botka
 *
 */
public interface ImmutableID {

	public final String ID = IDGenerator.generateSecureID();
}
