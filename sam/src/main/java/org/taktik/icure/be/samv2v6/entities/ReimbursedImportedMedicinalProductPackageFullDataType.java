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
 * <p>Java class for ReimbursedImportedMedicinalProductPackageFullDataType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ReimbursedImportedMedicinalProductPackageFullDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:actual:imported}ReimbursedImportedMedicinalProductPackageKeyType">
 *       &lt;sequence>
 *         &lt;element name="Data" type="{urn:be:fgov:ehealth:samws:v2:export}ReimbursedImportedMedicinalProductPackageDataType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReimbursedImportedMedicinalProductPackageFullDataType", propOrder = {
    "data"
})
public class ReimbursedImportedMedicinalProductPackageFullDataType
    extends ReimbursedImportedMedicinalProductPackageKeyType
{

    @XmlElement(name = "Data", required = true)
    protected List<ReimbursedImportedMedicinalProductPackageDataType> data;

    /**
     * Gets the value of the data property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the data property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getData().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReimbursedImportedMedicinalProductPackageDataType }
     *
     *
     */
    public List<ReimbursedImportedMedicinalProductPackageDataType> getData() {
        if (data == null) {
            data = new ArrayList<ReimbursedImportedMedicinalProductPackageDataType>();
        }
        return this.data;
    }

}
