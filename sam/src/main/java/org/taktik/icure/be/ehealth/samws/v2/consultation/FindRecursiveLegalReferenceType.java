//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.ehealth.samws.v2.consultation;

import org.taktik.icure.be.ehealth.samws.v2.reimbursementlaw.submit.LegalReferenceTypeType;

import javax.xml.bind.annotation.*;
import java.io.Serializable;


/**
 * <p>Java class for FindRecursiveLegalReferenceType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="FindRecursiveLegalReferenceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="Key" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;/choice>
 *         &lt;element name="Type" type="{urn:be:fgov:ehealth:samws:v2:reimbursementlaw:submit}LegalReferenceTypeType" minOccurs="0"/>
 *         &lt;element name="LegalReference" type="{urn:be:fgov:ehealth:samws:v2:consultation}FindRecursiveLegalReferenceType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FindRecursiveLegalReferenceType", propOrder = {
    "title",
    "key",
    "type",
    "legalReference"
})
public class FindRecursiveLegalReferenceType
    implements Serializable
{

    private final static long serialVersionUID = 2L;
    @XmlElement(name = "Title")
    protected String title;
    @XmlElement(name = "Key")
    protected String key;
    @XmlElement(name = "Type")
    @XmlSchemaType(name = "string")
    protected LegalReferenceTypeType type;
    @XmlElement(name = "LegalReference")
    protected FindRecursiveLegalReferenceType legalReference;

    /**
     * Gets the value of the title property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the key property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setKey(String value) {
        this.key = value;
    }

    /**
     * Gets the value of the type property.
     *
     * @return
     *     possible object is
     *     {@link LegalReferenceTypeType }
     *
     */
    public LegalReferenceTypeType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value
     *     allowed object is
     *     {@link LegalReferenceTypeType }
     *
     */
    public void setType(LegalReferenceTypeType value) {
        this.type = value;
    }

    /**
     * Gets the value of the legalReference property.
     *
     * @return
     *     possible object is
     *     {@link FindRecursiveLegalReferenceType }
     *
     */
    public FindRecursiveLegalReferenceType getLegalReference() {
        return legalReference;
    }

    /**
     * Sets the value of the legalReference property.
     *
     * @param value
     *     allowed object is
     *     {@link FindRecursiveLegalReferenceType }
     *
     */
    public void setLegalReference(FindRecursiveLegalReferenceType value) {
        this.legalReference = value;
    }

}
