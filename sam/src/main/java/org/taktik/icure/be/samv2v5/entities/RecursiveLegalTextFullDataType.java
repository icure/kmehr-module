//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v5.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for RecursiveLegalTextFullDataType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="RecursiveLegalTextFullDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:export}LegalTextFullDataType">
 *       &lt;sequence>
 *         &lt;element name="LegalText" type="{urn:be:fgov:ehealth:samws:v2:export}RecursiveLegalTextFullDataType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RecursiveLegalTextFullDataType", propOrder = {
    "legalText"
})
public class RecursiveLegalTextFullDataType
    extends LegalTextFullDataType
{

    @XmlElement(name = "LegalText")
    protected List<RecursiveLegalTextFullDataType> legalText;

    /**
     * Gets the value of the legalText property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the legalText property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLegalText().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RecursiveLegalTextFullDataType }
     *
     *
     */
    public List<RecursiveLegalTextFullDataType> getLegalText() {
        if (legalText == null) {
            legalText = new ArrayList<RecursiveLegalTextFullDataType>();
        }
        return this.legalText;
    }

}
