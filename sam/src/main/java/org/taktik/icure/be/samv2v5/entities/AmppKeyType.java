//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v5.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AmppKeyType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AmppKeyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="ctiExtended" use="required" type="{urn:be:fgov:ehealth:samws:v2:core}CtiExtendedType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AmppKeyType", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
@XmlSeeAlso({
    AmppFullDataType.class,
    ChangeAmppFamhpType.class,
    AmppNihdiType.class,
    AddAmppMinEcoType.class,
    ChangeAmppBcpiType.class,
    RemoveAmppType.class,
    ChangeAmppNihdiBisType.class,
    ChangeAmppNihdiType.class,
    AmppNihdiBisType.class,
    AmppBcpiType.class,
    AmppFamhpType.class
})
public class AmppKeyType {

    @XmlAttribute(name = "ctiExtended", required = true)
    protected String ctiExtended;

    /**
     * Gets the value of the ctiExtended property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCtiExtended() {
        return ctiExtended;
    }

    /**
     * Sets the value of the ctiExtended property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCtiExtended(String value) {
        this.ctiExtended = value;
    }

}
