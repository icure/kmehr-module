//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.ehealth.samws.v2.actual.common;

import org.taktik.icure.be.ehealth.samws.v2.core.TextType;

import javax.xml.bind.annotation.*;
import java.io.Serializable;


/**
 * <p>Java class for DerogationImportType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="DerogationImportType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:actual:common}DerogationImportKeyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:actual:common}DerogationImportFields"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DerogationImportType", propOrder = {
    "note"
})
@XmlSeeAlso({
    AddDerogationImportType.class
})
public class DerogationImportType
    extends DerogationImportKeyType
    implements Serializable
{

    private final static long serialVersionUID = 2L;
    @XmlElement(name = "Note", required = true)
    protected TextType note;

    /**
     * Gets the value of the note property.
     *
     * @return
     *     possible object is
     *     {@link TextType }
     *
     */
    public TextType getNote() {
        return note;
    }

    /**
     * Sets the value of the note property.
     *
     * @param value
     *     allowed object is
     *     {@link TextType }
     *
     */
    public void setNote(TextType value) {
        this.note = value;
    }

}
