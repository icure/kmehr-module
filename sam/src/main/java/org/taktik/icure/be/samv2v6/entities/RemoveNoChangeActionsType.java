//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v6.entities;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for removeNoChangeActionsType.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="removeNoChangeActionsType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="REMOVE"/>
 *     &lt;enumeration value="NO_CHANGE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "removeNoChangeActionsType", namespace = "urn:be:fgov:ehealth:samws:v2:core")
@XmlEnum
public enum RemoveNoChangeActionsType {

    REMOVE,
    NO_CHANGE;

    public String value() {
        return name();
    }

    public static RemoveNoChangeActionsType fromValue(String v) {
        return valueOf(v);
    }

}
