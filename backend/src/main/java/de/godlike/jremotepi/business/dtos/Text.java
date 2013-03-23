package de.godlike.jremotepi.business.dtos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Text extends Element {
	public Type type = Type.TEXT;
	public String textContent;

	/**
	 * 
	 */
	public Text() {
		super();
	}

	/**
	 * @param content
	 */
	public Text(String content) {
		super();
		this.textContent = content;
	}
}