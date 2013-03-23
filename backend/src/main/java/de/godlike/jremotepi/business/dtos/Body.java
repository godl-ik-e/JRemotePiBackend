package de.godlike.jremotepi.business.dtos;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Body extends OverElement {
	public Type type = Type.BODY;
	@XmlElements({ @XmlElement(type = Text.class),
			@XmlElement(type = Button.class) })
	public List<Element> list;

	/**
	 * 
	 */
	public Body() {
		super();
	}

	/**
	 * @param content
	 */
	public Body(List<Element> content) {
		super();
		this.list = content;
	}
}