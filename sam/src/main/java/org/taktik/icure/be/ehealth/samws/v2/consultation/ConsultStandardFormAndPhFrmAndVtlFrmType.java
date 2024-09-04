//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.ehealth.samws.v2.consultation;

import org.taktik.icure.be.ehealth.samws.v2.refdata.PharmaceuticalFormKeyType;
import org.taktik.icure.be.ehealth.samws.v2.refdata.VirtualFormKeyType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ConsultStandardFormAndPhFrmAndVtlFrmType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ConsultStandardFormAndPhFrmAndVtlFrmType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:consultation}ConsultStandardFormType">
 *       &lt;sequence>
 *         &lt;element name="PharmaceuticalForm" type="{urn:be:fgov:ehealth:samws:v2:refdata}PharmaceuticalFormKeyType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="VirtualForm" type="{urn:be:fgov:ehealth:samws:v2:refdata}VirtualFormKeyType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsultStandardFormAndPhFrmAndVtlFrmType", propOrder = {
    "pharmaceuticalForms",
    "virtualForms"
})
public class ConsultStandardFormAndPhFrmAndVtlFrmType
    extends ConsultStandardFormType
    implements Serializable
{

    private final static long serialVersionUID = 2L;
    @XmlElement(name = "PharmaceuticalForm")
    protected List<PharmaceuticalFormKeyType> pharmaceuticalForms;
    @XmlElement(name = "VirtualForm")
    protected List<VirtualFormKeyType> virtualForms;

    /**
     * Gets the value of the pharmaceuticalForms property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pharmaceuticalForms property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPharmaceuticalForms().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PharmaceuticalFormKeyType }
     *
     *
     */
    public List<PharmaceuticalFormKeyType> getPharmaceuticalForms() {
        if (pharmaceuticalForms == null) {
            pharmaceuticalForms = new ArrayList<PharmaceuticalFormKeyType>();
        }
        return this.pharmaceuticalForms;
    }

    /**
     * Gets the value of the virtualForms property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the virtualForms property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVirtualForms().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VirtualFormKeyType }
     *
     *
     */
    public List<VirtualFormKeyType> getVirtualForms() {
        if (virtualForms == null) {
            virtualForms = new ArrayList<VirtualFormKeyType>();
        }
        return this.virtualForms;
    }

}
