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
 * <p>Java class for ProfessionalAuthorisationType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ProfessionalAuthorisationType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:chapteriv:submit}ProfessionalAuthorisationKeyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:chapteriv:submit}ProfessionalAuthorisationFields"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProfessionalAuthorisationType", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit", propOrder = {
    "professionalCV",
    "purchasingAdvisorName"
})
@XmlSeeAlso({
    AddProfessionalAuthorisationType.class
})
public class ProfessionalAuthorisationType
    extends ProfessionalAuthorisationKeyType
{

    @XmlElement(name = "ProfessionalCV", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit")
    protected String professionalCV;
    @XmlElement(name = "PurchasingAdvisorName", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit")
    protected String purchasingAdvisorName;

    /**
     * Gets the value of the professionalCV property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getProfessionalCV() {
        return professionalCV;
    }

    /**
     * Sets the value of the professionalCV property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setProfessionalCV(String value) {
        this.professionalCV = value;
    }

    /**
     * Gets the value of the purchasingAdvisorName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPurchasingAdvisorName() {
        return purchasingAdvisorName;
    }

    /**
     * Sets the value of the purchasingAdvisorName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPurchasingAdvisorName(String value) {
        this.purchasingAdvisorName = value;
    }

}