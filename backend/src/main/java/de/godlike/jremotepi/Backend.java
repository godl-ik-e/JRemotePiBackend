package de.godlike.jremotepi;

import javax.ws.rs.ApplicationPath;

import com.sun.jersey.api.core.PackagesResourceConfig;

@ApplicationPath("/resources")
public class Backend extends PackagesResourceConfig {

	/**
	 * @param props
	 */
	public Backend() {
		super("de.godlike.jremotepi.handlers");
	}

}
