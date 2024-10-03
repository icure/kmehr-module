//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v6.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParagraphTraceKeyType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ParagraphTraceKeyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="ParentChapterName" use="required" type="{urn:be:fgov:ehealth:samws:v2:core}String10Type" />
 *       &lt;attribute name="ParentParagraphName" use="required" type="{urn:be:fgov:ehealth:samws:v2:core}String10Type" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParagraphTraceKeyType", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit")
@XmlSeeAlso({
    ParagraphTraceFullDataType.class,
    ParagraphTraceType.class
})
public class ParagraphTraceKeyType {

    @XmlAttribute(name = "ParentChapterName", required = true)
    protected String parentChapterName;
    @XmlAttribute(name = "ParentParagraphName", required = true)
    protected String parentParagraphName;

    /**
     * Gets the value of the parentChapterName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getParentChapterName() {
        return parentChapterName;
    }

    /**
     * Sets the value of the parentChapterName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setParentChapterName(String value) {
        this.parentChapterName = value;
    }

    /**
     * Gets the value of the parentParagraphName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getParentParagraphName() {
        return parentParagraphName;
    }

    /**
     * Sets the value of the parentParagraphName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setParentParagraphName(String value) {
        this.parentParagraphName = value;
    }

}