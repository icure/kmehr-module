//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.ehealth.samws.v2.refdata;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StdRteAllStandardsType.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StdRteAllStandardsType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DM+D"/>
 *     &lt;enumeration value="EDQM"/>
 *     &lt;enumeration value="SNOMED_CT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "StdRteAllStandardsType")
@XmlEnum
public enum StdRteAllStandardsType {

    @XmlEnumValue("DM+D")
    DM_D("DM+D"),
    EDQM("EDQM"),
    SNOMED_CT("SNOMED_CT");
    private final String value;

    StdRteAllStandardsType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StdRteAllStandardsType fromValue(String v) {
        for (StdRteAllStandardsType c: StdRteAllStandardsType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
