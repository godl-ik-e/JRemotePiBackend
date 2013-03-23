package de.godlike.jremotepi.business.dtos;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DeviceOverview {

	@XmlElements({ @XmlElement(type = NavBar.class),
			@XmlElement(type = Body.class), @XmlElement(type = Footer.class) })
	public List<OverElement> page;

	/**
	 * @param navbar
	 * @param list
	 * @param footer
	 */
	public DeviceOverview(List<OverElement> page) {
		super();
		this.page = page;
	}

	/**
	 * 
	 */
	public DeviceOverview() {
		super();
	}

}
