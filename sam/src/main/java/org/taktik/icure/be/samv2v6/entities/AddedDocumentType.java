//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v6.entities;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AddedDocumentType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AddedDocumentType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:chapteriv:submit}AddedDocumentKeyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:chapteriv:submit}AddedDocumentFields"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddedDocumentType", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit", propOrder = {
    "nameId",
    "formTypeId",
    "appendixTypeId",
    "mimeType",
    "documentContent",
    "addressUrl"
})
@XmlSeeAlso({
    AddAddedDocumentType.class
})
public class AddedDocumentType
    extends AddedDocumentKeyType
{

    @XmlElement(name = "NameId", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit", required = true)
    protected BigDecimal nameId;
    @XmlElement(name = "FormTypeId", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit")
    protected int formTypeId;
    @XmlElement(name = "AppendixTypeId", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit")
    protected int appendixTypeId;
    @XmlElement(name = "MimeType", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit")
    protected String mimeType;
    @XmlElement(name = "DocumentContent", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit")
    protected byte[] documentContent;
    @XmlElement(name = "AddressUrl", namespace = "urn:be:fgov:ehealth:samws:v2:chapteriv:submit")
    protected String addressUrl;

    /**
     * Gets the value of the nameId property.
     *
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *
     */
    public BigDecimal getNameId() {
        return nameId;
    }

    /**
     * Sets the value of the nameId property.
     *
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *
     */
    public void setNameId(BigDecimal value) {
        this.nameId = value;
    }

    /**
     * Gets the value of the formTypeId property.
     *
     */
    public int getFormTypeId() {
        return formTypeId;
    }

    /**
     * Sets the value of the formTypeId property.
     *
     */
    public void setFormTypeId(int value) {
        this.formTypeId = value;
    }

    /**
     * Gets the value of the appendixTypeId property.
     *
     */
    public int getAppendixTypeId() {
        return appendixTypeId;
    }

    /**
     * Sets the value of the appendixTypeId property.
     *
     */
    public void setAppendixTypeId(int value) {
        this.appendixTypeId = value;
    }

    /**
     * Gets the value of the mimeType property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Sets the value of the mimeType property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMimeType(String value) {
        this.mimeType = value;
    }

    /**
     * Gets the value of the documentContent property.
     *
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDocumentContent() {
        return documentContent;
    }

    /**
     * Sets the value of the documentContent property.
     *
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDocumentContent(byte[] value) {
        this.documentContent = value;
    }

    /**
     * Gets the value of the addressUrl property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAddressUrl() {
        return addressUrl;
    }

    /**
     * Sets the value of the addressUrl property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAddressUrl(String value) {
        this.addressUrl = value;
    }

}
