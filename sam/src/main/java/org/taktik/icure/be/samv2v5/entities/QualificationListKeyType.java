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
 * <p>Java class for QualificationListKeyType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="QualificationListKeyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="QualificationList" use="required" type="{urn:be:fgov:ehealth:samws:v2:core}String10Type" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QualificationListKeyType", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit")
@XmlSeeAlso({
    QualificationListFullDataType.class,
    QualificationListType.class
})
public class QualificationListKeyType {

    @XmlAttribute(name = "QualificationList", required = true)
    protected String qualificationList;

    /**
     * Gets the value of the qualificationList property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getQualificationList() {
        return qualificationList;
    }

    /**
     * Sets the value of the qualificationList property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setQualificationList(String value) {
        this.qualificationList = value;
    }

}
