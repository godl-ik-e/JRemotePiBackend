package de.godlike.jremotepi.business.dtos;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Footer extends OverElement {
	public Type type = Type.FOOTER;
	@XmlElements({ @XmlElement(type = Text.class),
			@XmlElement(type = Button.class) })
	public List<Element> list;

	/**
	 * 
	 */
	public Footer() {
		super();
	}

	/**
	 * @param content
	 */
	public Footer(List<Element> content) {
		super();
		this.list = content;
	}
}