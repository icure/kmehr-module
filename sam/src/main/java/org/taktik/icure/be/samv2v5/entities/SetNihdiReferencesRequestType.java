//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.samv2v5.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for SetNihdiReferencesRequestType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="SetNihdiReferencesRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:be:fgov:ehealth:samws:v2:refdata}SetNihdiEntities"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SetNihdiReferencesRequestType", namespace = "urn:be:fgov:ehealth:samws:v2:refdata", propOrder = {
    "appendix",
    "formCategory",
    "parameter",
    "reimbursementCriterion"
})
public class SetNihdiReferencesRequestType {

    @XmlElement(name = "Appendix", namespace = "urn:be:fgov:ehealth:samws:v2:refdata")
    protected List<AppendixType> appendix;
    @XmlElement(name = "FormCategory", namespace = "urn:be:fgov:ehealth:samws:v2:refdata")
    protected List<FormCategoryType> formCategory;
    @XmlElement(name = "Parameter", namespace = "urn:be:fgov:ehealth:samws:v2:refdata")
    protected List<ParameterType> parameter;
    @XmlElement(name = "ReimbursementCriterion", namespace = "urn:be:fgov:ehealth:samws:v2:refdata")
    protected List<ReimbursementCriterionType> reimbursementCriterion;

    /**
     * Gets the value of the appendix property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the appendix property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAppendix().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AppendixType }
     *
     *
     */
    public List<AppendixType> getAppendix() {
        if (appendix == null) {
            appendix = new ArrayList<AppendixType>();
        }
        return this.appendix;
    }

    /**
     * Gets the value of the formCategory property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the formCategory property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFormCategory().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FormCategoryType }
     *
     *
     */
    public List<FormCategoryType> getFormCategory() {
        if (formCategory == null) {
            formCategory = new ArrayList<FormCategoryType>();
        }
        return this.formCategory;
    }

    /**
     * Gets the value of the parameter property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parameter property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameter().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParameterType }
     *
     *
     */
    public List<ParameterType> getParameter() {
        if (parameter == null) {
            parameter = new ArrayList<ParameterType>();
        }
        return this.parameter;
    }

    /**
     * Gets the value of the reimbursementCriterion property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reimbursementCriterion property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReimbursementCriterion().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReimbursementCriterionType }
     *
     *
     */
    public List<ReimbursementCriterionType> getReimbursementCriterion() {
        if (reimbursementCriterion == null) {
            reimbursementCriterion = new ArrayList<ReimbursementCriterionType>();
        }
        return this.reimbursementCriterion;
    }

}
