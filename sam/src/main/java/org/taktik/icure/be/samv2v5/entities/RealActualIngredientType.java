//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v5.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RealActualIngredientType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="RealActualIngredientType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:actual:common}RealActualIngredientKeyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:actual:common}RealActualIngredientFields"/>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:actual:common}RealActualIngredientReferences"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RealActualIngredientType", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common", propOrder = {
    "type",
    "knownEffect",
    "strength",
    "strengthDescription",
    "additionalInformation",
    "substanceCode"
})
@XmlSeeAlso({
    AddRealActualIngredientType.class
})
public class RealActualIngredientType
    extends RealActualIngredientKeyType
{

    @XmlElement(name = "Type", required = true)
    @XmlSchemaType(name = "string")
    protected IngredientTypeType type;
    @XmlElement(name = "KnownEffect")
    protected Boolean knownEffect;
    @XmlElement(name = "Strength")
    protected QuantityType strength;
    @XmlElement(name = "StrengthDescription")
    protected String strengthDescription;
    @XmlElement(name = "AdditionalInformation")
    protected String additionalInformation;
    @XmlElement(name = "SubstanceCode", required = true)
    protected String substanceCode;

    /**
     * Gets the value of the type property.
     *
     * @return
     *     possible object is
     *     {@link IngredientTypeType }
     *
     */
    public IngredientTypeType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value
     *     allowed object is
     *     {@link IngredientTypeType }
     *
     */
    public void setType(IngredientTypeType value) {
        this.type = value;
    }

    /**
     * Gets the value of the knownEffect property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isKnownEffect() {
        return knownEffect;
    }

    /**
     * Sets the value of the knownEffect property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setKnownEffect(Boolean value) {
        this.knownEffect = value;
    }

    /**
     * Gets the value of the strength property.
     *
     * @return
     *     possible object is
     *     {@link QuantityType }
     *
     */
    public QuantityType getStrength() {
        return strength;
    }

    /**
     * Sets the value of the strength property.
     *
     * @param value
     *     allowed object is
     *     {@link QuantityType }
     *
     */
    public void setStrength(QuantityType value) {
        this.strength = value;
    }

    /**
     * Gets the value of the strengthDescription property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getStrengthDescription() {
        return strengthDescription;
    }

    /**
     * Sets the value of the strengthDescription property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setStrengthDescription(String value) {
        this.strengthDescription = value;
    }

    /**
     * Gets the value of the additionalInformation property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAdditionalInformation() {
        return additionalInformation;
    }

    /**
     * Sets the value of the additionalInformation property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAdditionalInformation(String value) {
        this.additionalInformation = value;
    }

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

}
