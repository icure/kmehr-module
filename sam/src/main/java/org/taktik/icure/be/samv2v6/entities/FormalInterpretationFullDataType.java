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
 * <p>Java class for FormalInterpretationFullDataType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="FormalInterpretationFullDataType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:reimbursementlaw:submit}FormalInterpretationKeyType">
 *       &lt;sequence>
 *         &lt;element name="Data" type="{urn:be:fgov:ehealth:samws:v2:export}FormalInterpretationDataType" maxOccurs="unbounded"/>
 *         &lt;element name="ReimbursementCondition" type="{urn:be:fgov:ehealth:samws:v2:export}ReimbursementConditionFullDataType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ReimbursementTerm" type="{urn:be:fgov:ehealth:samws:v2:export}ReimbursementTermFullDataType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FormalInterpretationFullDataType", propOrder = {
    "data",
    "reimbursementCondition",
    "reimbursementTerm"
})
public class FormalInterpretationFullDataType
    extends FormalInterpretationKeyType
{

    @XmlElement(name = "Data", required = true)
    protected List<FormalInterpretationDataType> data;
    @XmlElement(name = "ReimbursementCondition")
    protected List<ReimbursementConditionFullDataType> reimbursementCondition;
    @XmlElement(name = "ReimbursementTerm")
    protected List<ReimbursementTermFullDataType> reimbursementTerm;

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
     * {@link FormalInterpretationDataType }
     *
     *
     */
    public List<FormalInterpretationDataType> getData() {
        if (data == null) {
            data = new ArrayList<FormalInterpretationDataType>();
        }
        return this.data;
    }

    /**
     * Gets the value of the reimbursementCondition property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reimbursementCondition property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReimbursementCondition().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReimbursementConditionFullDataType }
     *
     *
     */
    public List<ReimbursementConditionFullDataType> getReimbursementCondition() {
        if (reimbursementCondition == null) {
            reimbursementCondition = new ArrayList<ReimbursementConditionFullDataType>();
        }
        return this.reimbursementCondition;
    }

    /**
     * Gets the value of the reimbursementTerm property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reimbursementTerm property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReimbursementTerm().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReimbursementTermFullDataType }
     *
     *
     */
    public List<ReimbursementTermFullDataType> getReimbursementTerm() {
        if (reimbursementTerm == null) {
            reimbursementTerm = new ArrayList<ReimbursementTermFullDataType>();
        }
        return this.reimbursementTerm;
    }

}
