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
 * <p>Java class for AddQualificationListRequestType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AddQualificationListRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="QualificationList" type="{urn:be:fgov:ehealth:samws:v2:chapteriv:submit}AddQualificationListType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddQualificationListRequestType", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit", propOrder = {
    "qualificationList"
})
public class AddQualificationListRequestType {

    @XmlElement(name = "QualificationList", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit", required = true)
    protected AddQualificationListType qualificationList;

    /**
     * Gets the value of the qualificationList property.
     *
     * @return
     *     possible object is
     *     {@link AddQualificationListType }
     *
     */
    public AddQualificationListType getQualificationList() {
        return qualificationList;
    }

    /**
     * Sets the value of the qualificationList property.
     *
     * @param value
     *     allowed object is
     *     {@link AddQualificationListType }
     *
     */
    public void setQualificationList(AddQualificationListType value) {
        this.qualificationList = value;
    }

}
