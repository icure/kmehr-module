//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.ehealth.samws.v2.refdata;

import org.taktik.icure.be.ehealth.samws.v2.core.Text255Type;
import org.taktik.icure.be.ehealth.samws.v2.core.TextType;
import org.taktik.icure.be.samv2v5.entities.SubstanceWithStandardsType;

import javax.xml.bind.annotation.*;
import java.io.Serializable;


/**
 * <p>Java class for SubstanceType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="SubstanceType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:refdata}SubstanceKeyType">
 *       &lt;sequence>
 *         &lt;element name="ChemicalForm" type="{urn:be:fgov:ehealth:samws:v2:core}String10Type" minOccurs="0"/>
 *         &lt;element name="Name" type="{urn:be:fgov:ehealth:samws:v2:core}Text255Type"/>
 *         &lt;element name="Note" type="{urn:be:fgov:ehealth:samws:v2:core}TextType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubstanceType", propOrder = {
    "chemicalForm",
    "name",
    "note"
})
@XmlSeeAlso({
    SubstanceWithStandardsType.class
})
public class SubstanceType
    extends SubstanceKeyType
    implements Serializable
{

    private final static long serialVersionUID = 2L;
    @XmlElement(name = "ChemicalForm")
    protected String chemicalForm;
    @XmlElement(name = "Name", required = true)
    protected Text255Type name;
    @XmlElement(name = "Note")
    protected TextType note;

    /**
     * Gets the value of the chemicalForm property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getChemicalForm() {
        return chemicalForm;
    }

    /**
     * Sets the value of the chemicalForm property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setChemicalForm(String value) {
        this.chemicalForm = value;
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
