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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExportReimbursementsType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ExportReimbursementsType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:export}VersionedExportType">
 *       &lt;sequence>
 *         &lt;element name="ReimbursementContext" type="{urn:be:fgov:ehealth:samws:v2:export}ReimbursementContextFullDataType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExportReimbursementsType", propOrder = {
    "reimbursementContext"
})
@XmlRootElement(name = "ExportReimbursements")
public class ExportReimbursementsType
    extends VersionedExportType
{

    @XmlElement(name = "ReimbursementContext")
    protected List<ReimbursementContextFullDataType> reimbursementContext;

    /**
     * Gets the value of the reimbursementContext property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reimbursementContext property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReimbursementContext().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReimbursementContextFullDataType }
     *
     *
     */
    public List<ReimbursementContextFullDataType> getReimbursementContext() {
        if (reimbursementContext == null) {
            reimbursementContext = new ArrayList<ReimbursementContextFullDataType>();
        }
        return this.reimbursementContext;
    }

}
