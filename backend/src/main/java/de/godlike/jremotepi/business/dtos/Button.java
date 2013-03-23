package de.godlike.jremotepi.business.dtos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Button extends Element {
	public Type type = Type.BUTTON;
	public String name;
	public String action;
	public String actionType;

	/**
	 * 
	 */
	public Button() {
		super();
	}

	/**
	 * @param name
	 * @param action
	 * @param actionType
	 */
	public Button(String name, String action, String actionType) {
		super();
		this.name = name;
		this.action = action;
		this.actionType = actionType;
	}
}