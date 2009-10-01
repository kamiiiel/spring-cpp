package com.googlecode.wmlynar.springcpp.domain;

public class ConstructorArg {

	private String value;
	private boolean text;
	private String ref;
	
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getRef() {
		return ref;
	}
	public void setText(boolean text) {
		this.text = text;
	}
	public boolean isText() {
		return text;
	}
}
