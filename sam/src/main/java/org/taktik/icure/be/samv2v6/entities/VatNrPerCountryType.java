//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v6.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for VatNrPerCountryType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="VatNrPerCountryType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;urn:be:fgov:ehealth:samws:v2:company:submit>VatNrType">
 *       &lt;attribute name="countryCode" use="required" type="{urn:be:fgov:ehealth:samws:v2:company:submit}CountryCodeType" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VatNrPerCountryType", namespace = "urn:be:fgov:ehealth:samws:v2:company:submit", propOrder = {
    "value"
})
public class VatNrPerCountryType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "countryCode", required = true)
    protected String countryCode;

    /**
     * Gets the value of the value property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the countryCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the value of the countryCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCountryCode(String value) {
        this.countryCode = value;
    }

}
