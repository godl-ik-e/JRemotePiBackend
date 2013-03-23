package de.godlike.jremotepi.business.dtos;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NavBar extends OverElement {
	public Type type = Type.NAVBAR;
	public List<Button> list;

	/**
	 * @param content
	 */
	public NavBar(List<Button> content) {
		super();
		this.list = content;
	}

	/**
	 * 
	 */
	public NavBar() {
		super();
	}
}