//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v6.entities;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AmpBcpiType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AmpBcpiType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:actual:common}AmpKeyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:actual:common}AmpBcpiFields"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AmpBcpiType", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common", propOrder = {
    "abbreviatedName",
    "proprietarySuffix",
    "prescriptionName",
    "singleAdministrationDose",
    "standardDosageCode"
})
@XmlSeeAlso({
    AddAmpBcpiType.class
})
public class AmpBcpiType
    extends AmpKeyType
{

    @XmlElement(name = "AbbreviatedName", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common", required = true)
    protected Text255Type abbreviatedName;
    @XmlElement(name = "ProprietarySuffix", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common", required = true)
    protected Text255Type proprietarySuffix;
    @XmlElement(name = "PrescriptionName", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common", required = true)
    protected Text255Type prescriptionName;
    @XmlElement(name = "SingleAdministrationDose", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected QuantityType singleAdministrationDose;
    @XmlElement(name = "StandardDosageCode", namespace = "urn:be:fgov:ehealth:samws:v2:actual:common")
    protected List<String> standardDosageCode;

    /**
     * Gets the value of the abbreviatedName property.
     *
     * @return
     *     possible object is
     *     {@link Text255Type }
     *
     */
    public Text255Type getAbbreviatedName() {
        return abbreviatedName;
    }

    /**
     * Sets the value of the abbreviatedName property.
     *
     * @param value
     *     allowed object is
     *     {@link Text255Type }
     *
     */
    public void setAbbreviatedName(Text255Type value) {
        this.abbreviatedName = value;
    }

    /**
     * Gets the value of the proprietarySuffix property.
     *
     * @return
     *     possible object is
     *     {@link Text255Type }
     *
     */
    public Text255Type getProprietarySuffix() {
        return proprietarySuffix;
    }

    /**
     * Sets the value of the proprietarySuffix property.
     *
     * @param value
     *     allowed object is
     *     {@link Text255Type }
     *
     */
    public void setProprietarySuffix(Text255Type value) {
        this.proprietarySuffix = value;
    }

    /**
     * Gets the value of the prescriptionName property.
     *
     * @return
     *     possible object is
     *     {@link Text255Type }
     *
     */
    public Text255Type getPrescriptionName() {
        return prescriptionName;
    }

    /**
     * Sets the value of the prescriptionName property.
     *
     * @param value
     *     allowed object is
     *     {@link Text255Type }
     *
     */
    public void setPrescriptionName(Text255Type value) {
        this.prescriptionName = value;
    }

    /**
     * Gets the value of the singleAdministrationDose property.
     *
     * @return
     *     possible object is
     *     {@link QuantityType }
     *
     */
    public QuantityType getSingleAdministrationDose() {
        return singleAdministrationDose;
    }

    /**
     * Sets the value of the singleAdministrationDose property.
     *
     * @param value
     *     allowed object is
     *     {@link QuantityType }
     *
     */
    public void setSingleAdministrationDose(QuantityType value) {
        this.singleAdministrationDose = value;
    }

    /**
     * Gets the value of the standardDosageCode property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the standardDosageCode property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStandardDosageCode().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     *
     *
     */
    public List<String> getStandardDosageCode() {
        if (standardDosageCode == null) {
            standardDosageCode = new ArrayList<String>();
        }
        return this.standardDosageCode;
    }

}