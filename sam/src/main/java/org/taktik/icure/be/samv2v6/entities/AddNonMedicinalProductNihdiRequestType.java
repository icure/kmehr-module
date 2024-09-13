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
 * <p>Java class for AddNonMedicinalProductNihdiRequestType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AddNonMedicinalProductNihdiRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NonMedicinalProduct" type="{urn:be:fgov:ehealth:samws:v2:nonmedicinal:common}AddNonMedicinalProductNihdiType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddNonMedicinalProductNihdiRequestType", namespace = "urn:be:fgov:ehealth:samws:v2:nonmedicinal:common", propOrder = {
    "nonMedicinalProduct"
})
public class AddNonMedicinalProductNihdiRequestType {

    @XmlElement(name = "NonMedicinalProduct", namespace = "urn:be:fgov:ehealth:samws:v2:nonmedicinal:common", required = true)
    protected List<AddNonMedicinalProductNihdiType> nonMedicinalProduct;

    /**
     * Gets the value of the nonMedicinalProduct property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nonMedicinalProduct property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNonMedicinalProduct().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AddNonMedicinalProductNihdiType }
     *
     *
     */
    public List<AddNonMedicinalProductNihdiType> getNonMedicinalProduct() {
        if (nonMedicinalProduct == null) {
            nonMedicinalProduct = new ArrayList<AddNonMedicinalProductNihdiType>();
        }
        return this.nonMedicinalProduct;
    }

}
