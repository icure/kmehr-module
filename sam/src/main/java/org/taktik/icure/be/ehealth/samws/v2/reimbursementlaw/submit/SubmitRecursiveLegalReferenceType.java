//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.ehealth.samws.v2.reimbursementlaw.submit;

import org.taktik.icure.be.ehealth.samws.v2.core.AllActionsType;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for SubmitRecursiveLegalReferenceType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="SubmitRecursiveLegalReferenceType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:reimbursementlaw:submit}LegalReferenceType">
 *       &lt;choice>
 *         &lt;element name="RecursiveLegalReference" type="{urn:be:fgov:ehealth:samws:v2:reimbursementlaw:submit}SubmitRecursiveLegalReferenceType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;sequence>
 *           &lt;element name="FormalInterpretation" type="{urn:be:fgov:ehealth:samws:v2:reimbursementlaw:submit}SubmitFormalInterpretationType" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="RecursiveLegalText" type="{urn:be:fgov:ehealth:samws:v2:reimbursementlaw:submit}SubmitRecursiveLegalTextType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/sequence>
 *       &lt;/choice>
 *       &lt;attGroup ref="{urn:be:fgov:ehealth:samws:v2:core}allActionsMetadata"/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubmitRecursiveLegalReferenceType", propOrder = {
    "formalInterpretations",
    "recursiveLegalTexts",
    "recursiveLegalReferences"
})
public class SubmitRecursiveLegalReferenceType
    extends LegalReferenceType
    implements Serializable
{

    private final static long serialVersionUID = 2L;
    @XmlElement(name = "FormalInterpretation")
    protected List<SubmitFormalInterpretationType> formalInterpretations;
    @XmlElement(name = "RecursiveLegalText")
    protected List<SubmitRecursiveLegalTextType> recursiveLegalTexts;
    @XmlElement(name = "RecursiveLegalReference")
    protected List<SubmitRecursiveLegalReferenceType> recursiveLegalReferences;
    @XmlAttribute(name = "action", required = true)
    protected AllActionsType action;
    @XmlAttribute(name = "from")
    protected XMLGregorianCalendar from;
    @XmlAttribute(name = "to")
    protected XMLGregorianCalendar to;

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
     * {@link SubmitFormalInterpretationType }
     *
     *
     */
    public List<SubmitFormalInterpretationType> getFormalInterpretations() {
        if (formalInterpretations == null) {
            formalInterpretations = new ArrayList<SubmitFormalInterpretationType>();
        }
        return this.formalInterpretations;
    }

    /**
     * Gets the value of the recursiveLegalTexts property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the recursiveLegalTexts property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRecursiveLegalTexts().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubmitRecursiveLegalTextType }
     *
     *
     */
    public List<SubmitRecursiveLegalTextType> getRecursiveLegalTexts() {
        if (recursiveLegalTexts == null) {
            recursiveLegalTexts = new ArrayList<SubmitRecursiveLegalTextType>();
        }
        return this.recursiveLegalTexts;
    }

    /**
     * Gets the value of the recursiveLegalReferences property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the recursiveLegalReferences property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRecursiveLegalReferences().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubmitRecursiveLegalReferenceType }
     *
     *
     */
    public List<SubmitRecursiveLegalReferenceType> getRecursiveLegalReferences() {
        if (recursiveLegalReferences == null) {
            recursiveLegalReferences = new ArrayList<SubmitRecursiveLegalReferenceType>();
        }
        return this.recursiveLegalReferences;
    }

    /**
     * Gets the value of the action property.
     *
     * @return
     *     possible object is
     *     {@link AllActionsType }
     *
     */
    public AllActionsType getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     *
     * @param value
     *     allowed object is
     *     {@link AllActionsType }
     *
     */
    public void setAction(AllActionsType value) {
        this.action = value;
    }

    /**
     * Gets the value of the from property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getFrom() {
        return from;
    }

    /**
     * Sets the value of the from property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setFrom(XMLGregorianCalendar value) {
        this.from = value;
    }

    /**
     * Gets the value of the to property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getTo() {
        return to;
    }

    /**
     * Sets the value of the to property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setTo(XMLGregorianCalendar value) {
        this.to = value;
    }

}
