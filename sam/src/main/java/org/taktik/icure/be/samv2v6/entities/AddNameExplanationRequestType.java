//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v6.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AddNameExplanationRequestType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AddNameExplanationRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NameExplanation" type="{urn:be:fgov:ehealth:samws:v2:chapteriv:submit}AddNameExplanationType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddNameExplanationRequestType", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit", propOrder = {
    "nameExplanation"
})
public class AddNameExplanationRequestType {

    @XmlElement(name = "NameExplanation", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit", required = true)
    protected AddNameExplanationType nameExplanation;

    /**
     * Gets the value of the nameExplanation property.
     *
     * @return
     *     possible object is
     *     {@link AddNameExplanationType }
     *
     */
    public AddNameExplanationType getNameExplanation() {
        return nameExplanation;
    }

    /**
     * Sets the value of the nameExplanation property.
     *
     * @param value
     *     allowed object is
     *     {@link AddNameExplanationType }
     *
     */
    public void setNameExplanation(AddNameExplanationType value) {
        this.nameExplanation = value;
    }

}
