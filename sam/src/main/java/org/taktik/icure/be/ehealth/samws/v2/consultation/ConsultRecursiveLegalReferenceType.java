//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.ehealth.samws.v2.consultation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ConsultRecursiveLegalReferenceType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ConsultRecursiveLegalReferenceType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:consultation}ConsultLegalReferenceType">
 *       &lt;choice>
 *         &lt;element name="LegalReference" type="{urn:be:fgov:ehealth:samws:v2:consultation}ConsultRecursiveLegalReferenceType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;sequence>
 *           &lt;element name="FormalInterpretation" type="{urn:be:fgov:ehealth:samws:v2:consultation}ConsultFormalInterpretationType" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="LegalText" type="{urn:be:fgov:ehealth:samws:v2:consultation}ConsultRecursiveLegalTextType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/sequence>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsultRecursiveLegalReferenceType", propOrder = {
    "formalInterpretations",
    "legalTexts",
    "legalReferences"
})
public class ConsultRecursiveLegalReferenceType
    extends ConsultLegalReferenceType
    implements Serializable
{

    private final static long serialVersionUID = 2L;
    @XmlElement(name = "FormalInterpretation")
    protected List<ConsultFormalInterpretationType> formalInterpretations;
    @XmlElement(name = "LegalText")
    protected List<ConsultRecursiveLegalTextType> legalTexts;
    @XmlElement(name = "LegalReference")
    protected List<ConsultRecursiveLegalReferenceType> legalReferences;

    /**
     * Gets the value of the formalInterpretations property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the formalInterpretations property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFormalInterpretations().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConsultFormalInterpretationType }
     *
     *
     */
    public List<ConsultFormalInterpretationType> getFormalInterpretations() {
        if (formalInterpretations == null) {
            formalInterpretations = new ArrayList<ConsultFormalInterpretationType>();
        }
        return this.formalInterpretations;
    }

    /**
     * Gets the value of the legalTexts property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the legalTexts property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLegalTexts().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConsultRecursiveLegalTextType }
     *
     *
     */
    public List<ConsultRecursiveLegalTextType> getLegalTexts() {
        if (legalTexts == null) {
            legalTexts = new ArrayList<ConsultRecursiveLegalTextType>();
        }
        return this.legalTexts;
    }

    /**
     * Gets the value of the legalReferences property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the legalReferences property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLegalReferences().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConsultRecursiveLegalReferenceType }
     *
     *
     */
    public List<ConsultRecursiveLegalReferenceType> getLegalReferences() {
        if (legalReferences == null) {
            legalReferences = new ArrayList<ConsultRecursiveLegalReferenceType>();
        }
        return this.legalReferences;
    }

}
