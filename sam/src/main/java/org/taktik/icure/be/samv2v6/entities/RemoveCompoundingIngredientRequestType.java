//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v6.entities;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RemoveCompoundingIngredientRequestType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="RemoveCompoundingIngredientRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CompoundingIngredient" type="{urn:be:fgov:ehealth:samws:v2:compounding:common}RemoveCompoundingIngredientType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RemoveCompoundingIngredientRequestType", namespace = "urn:be:fgov:ehealth:samws:v2:compounding:common", propOrder = {
    "compoundingIngredient"
})
public class RemoveCompoundingIngredientRequestType {

    @XmlElement(name = "CompoundingIngredient", namespace = "urn:be:fgov:ehealth:samws:v2:compounding:common", required = true)
    protected List<RemoveCompoundingIngredientType> compoundingIngredient;

    /**
     * Gets the value of the compoundingIngredient property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the compoundingIngredient property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCompoundingIngredient().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RemoveCompoundingIngredientType }
     *
     *
     */
    public List<RemoveCompoundingIngredientType> getCompoundingIngredient() {
        if (compoundingIngredient == null) {
            compoundingIngredient = new ArrayList<RemoveCompoundingIngredientType>();
        }
        return this.compoundingIngredient;
    }

}
