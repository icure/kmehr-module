//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.ehealth.samws.v2.consultation;

import org.taktik.icure.be.ehealth.samws.v2.refdata.SubstanceKeyType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ConsultStandardSubstanceAndSubstanceType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ConsultStandardSubstanceAndSubstanceType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:consultation}ConsultStandardSubstanceType">
 *       &lt;sequence>
 *         &lt;element name="Substance" type="{urn:be:fgov:ehealth:samws:v2:refdata}SubstanceKeyType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsultStandardSubstanceAndSubstanceType", propOrder = {
    "substances"
})
public class ConsultStandardSubstanceAndSubstanceType
    extends ConsultStandardSubstanceType
    implements Serializable
{

    private final static long serialVersionUID = 2L;
    @XmlElement(name = "Substance")
    protected List<SubstanceKeyType> substances;

    /**
     * Gets the value of the substances property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the substances property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubstances().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubstanceKeyType }
     *
     *
     */
    public List<SubstanceKeyType> getSubstances() {
        if (substances == null) {
            substances = new ArrayList<SubstanceKeyType>();
        }
        return this.substances;
    }

}
