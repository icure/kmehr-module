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
 * <p>Java class for NameTranslationKeyType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="NameTranslationKeyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="NameTypeCV" use="required" type="{urn:be:fgov:ehealth:samws:v2:core}String6Type" />
 *       &lt;attribute name="LanguageCV" use="required" type="{urn:be:fgov:ehealth:samws:v2:core}String2Type" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NameTranslationKeyType", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit")
@XmlSeeAlso({
    NameTranslationFullDataType.class,
    NameTranslationType.class
})
public class NameTranslationKeyType {

    @XmlAttribute(name = "NameTypeCV", required = true)
    protected String nameTypeCV;
    @XmlAttribute(name = "LanguageCV", required = true)
    protected String languageCV;

    /**
     * Gets the value of the nameTypeCV property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNameTypeCV() {
        return nameTypeCV;
    }

    /**
     * Sets the value of the nameTypeCV property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNameTypeCV(String value) {
        this.nameTypeCV = value;
    }

    /**
     * Gets the value of the languageCV property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLanguageCV() {
        return languageCV;
    }

    /**
     * Sets the value of the languageCV property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLanguageCV(String value) {
        this.languageCV = value;
    }

}
