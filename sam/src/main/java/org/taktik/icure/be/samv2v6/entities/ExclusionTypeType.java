//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v6.entities;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExclusionTypeType.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ExclusionTypeType">
 *   &lt;restriction base="{urn:be:fgov:ehealth:samws:v2:core}CharacterType">
 *     &lt;enumeration value="T"/>
 *     &lt;enumeration value="C"/>
 *     &lt;enumeration value="A"/>
 *     &lt;enumeration value="D"/>
 *     &lt;enumeration value="J"/>
 *     &lt;enumeration value="I"/>
 *     &lt;enumeration value="K"/>
 *     &lt;enumeration value="E"/>
 *     &lt;enumeration value="V"/>
 *     &lt;enumeration value="S"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "ExclusionTypeType", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit")
@XmlEnum
public enum ExclusionTypeType {

    T,
    C,
    A,
    D,
    J,
    I,
    K,
    E,
    V,

    /**
     * Temporary included for backwards compatibility with SAM v1
     *
     */
    S;

    public String value() {
        return name();
    }

    public static ExclusionTypeType fromValue(String v) {
        return valueOf(v);
    }

}
