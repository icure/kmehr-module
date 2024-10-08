//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v5.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for CompoundingFormulaType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CompoundingFormulaType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:compounding:common}CompoundingFormulaKeyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:compounding:common}CompoundingFormulaFields"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CompoundingFormulaType", namespace = "urn:be:fgov:ehealth:samws:v2:compounding:common", propOrder = {
    "synonym",
    "formulary"
})
@XmlSeeAlso({
    AddCompoundingFormulaType.class
})
public class CompoundingFormulaType
    extends CompoundingFormulaKeyType
{

    @XmlElement(name = "Synonym", namespace = "urn:be:fgov:ehealth:samws:v2:compounding:common", required = true)
    protected List<SynonymType> synonym;
    @XmlElement(name = "Formulary", namespace = "urn:be:fgov:ehealth:samws:v2:compounding:common")
    protected List<String> formulary;

    /**
     * Gets the value of the synonym property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the synonym property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSynonym().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SynonymType }
     *
     *
     */
    public List<SynonymType> getSynonym() {
        if (synonym == null) {
            synonym = new ArrayList<SynonymType>();
        }
        return this.synonym;
    }

    /**
     * Gets the value of the formulary property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the formulary property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFormulary().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     *
     *
     */
    public List<String> getFormulary() {
        if (formulary == null) {
            formulary = new ArrayList<String>();
        }
        return this.formulary;
    }

}
