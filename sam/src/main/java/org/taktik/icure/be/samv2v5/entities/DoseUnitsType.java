//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v5.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DoseUnitsType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="DoseUnitsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SubstanceCode" type="{urn:be:fgov:ehealth:samws:v2:core}String10Type"/>
 *         &lt;element name="Strength" type="{urn:be:fgov:ehealth:samws:v2:core}StrengthRangeType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="rank" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DoseUnitsType", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common", propOrder = {
    "substanceCode",
    "strength"
})
public class DoseUnitsType {

    @XmlElement(name = "SubstanceCode", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common", required = true)
    protected String substanceCode;
    @XmlElement(name = "Strength", namespace = "urn:be:fgov:ehealth:samws:v2:virtual:common", required = true)
    protected StrengthRangeType strength;
    @XmlAttribute(name = "rank")
    @XmlSchemaType(name = "anySimpleType")
    protected String rank;

    /**
     * Gets the value of the substanceCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSubstanceCode() {
        return substanceCode;
    }

    /**
     * Sets the value of the substanceCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSubstanceCode(String value) {
        this.substanceCode = value;
    }

    /**
     * Gets the value of the strength property.
     *
     * @return
     *     possible object is
     *     {@link StrengthRangeType }
     *
     */
    public StrengthRangeType getStrength() {
        return strength;
    }

    /**
     * Sets the value of the strength property.
     *
     * @param value
     *     allowed object is
     *     {@link StrengthRangeType }
     *
     */
    public void setStrength(StrengthRangeType value) {
        this.strength = value;
    }

    /**
     * Gets the value of the rank property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getRank() {
        return rank;
    }

    /**
     * Sets the value of the rank property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRank(String value) {
        this.rank = value;
    }

}
