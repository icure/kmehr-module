//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.ehealth.samws.v2.compounding.common;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for AddCompoundingFormulaRequestType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AddCompoundingFormulaRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CompoundingFormula" type="{urn:be:fgov:ehealth:samws:v2:compounding:common}AddCompoundingFormulaType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddCompoundingFormulaRequestType", propOrder = {
    "compoundingFormulas"
})
@XmlRootElement(name = "AddCompoundingFormulaRequest")
public class AddCompoundingFormulaRequest
    implements Serializable
{

    private final static long serialVersionUID = 2L;
    @XmlElement(name = "CompoundingFormula", required = true)
    protected List<AddCompoundingFormulaType> compoundingFormulas;

    /**
     * Gets the value of the compoundingFormulas property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the compoundingFormulas property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCompoundingFormulas().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AddCompoundingFormulaType }
     *
     *
     */
    public List<AddCompoundingFormulaType> getCompoundingFormulas() {
        if (compoundingFormulas == null) {
            compoundingFormulas = new ArrayList<AddCompoundingFormulaType>();
        }
        return this.compoundingFormulas;
    }

}
