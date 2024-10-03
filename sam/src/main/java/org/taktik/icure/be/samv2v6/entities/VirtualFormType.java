//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v6.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualFormType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="VirtualFormType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:refdata}VirtualFormKeyType">
 *       &lt;sequence>
 *         &lt;element name="Abbreviation" type="{urn:be:fgov:ehealth:samws:v2:core}Text255Type"/>
 *         &lt;element name="Name" type="{urn:be:fgov:ehealth:samws:v2:core}Text255Type"/>
 *         &lt;element name="Description" type="{urn:be:fgov:ehealth:samws:v2:core}TextType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualFormType", namespace = "urn:be:fgov:ehealth:samws:v2:refdata", propOrder = {
    "abbreviation",
    "name",
    "description"
})
@XmlSeeAlso({
    VirtualFormWithStandardsType.class
})
public class VirtualFormType
    extends VirtualFormKeyType
{

    @XmlElement(name = "Abbreviation", namespace = "urn:be:fgov:ehealth:samws:v2:refdata", required = true)
    protected Text255Type abbreviation;
    @XmlElement(name = "Name", namespace = "urn:be:fgov:ehealth:samws:v2:refdata", required = true)
    protected Text255Type name;
    @XmlElement(name = "Description", namespace = "urn:be:fgov:ehealth:samws:v2:refdata")
    protected TextType description;

    /**
     * Gets the value of the abbreviation property.
     *
     * @return
     *     possible object is
     *     {@link Text255Type }
     *
     */
    public Text255Type getAbbreviation() {
        return abbreviation;
    }

    /**
     * Sets the value of the abbreviation property.
     *
     * @param value
     *     allowed object is
     *     {@link Text255Type }
     *
     */
    public void setAbbreviation(Text255Type value) {
        this.abbreviation = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link Text255Type }
     *
     */
    public Text255Type getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link Text255Type }
     *
     */
    public void setName(Text255Type value) {
        this.name = value;
    }

    /**
     * Gets the value of the description property.
     *
     * @return
     *     possible object is
     *     {@link TextType }
     *
     */
    public TextType getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     *
     * @param value
     *     allowed object is
     *     {@link TextType }
     *
     */
    public void setDescription(TextType value) {
        this.description = value;
    }

}