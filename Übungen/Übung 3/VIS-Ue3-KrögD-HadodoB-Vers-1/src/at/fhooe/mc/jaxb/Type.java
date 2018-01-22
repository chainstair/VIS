package at.fhooe.mc.jaxb;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace="animals")
public enum Type {
	CAT, DOG, MOUSE, BIRD;
}
