package at.fhooe.mc.task01;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace="animals")
public enum Type {
	CAT, DOG, MOUSE, BIRD;
}
