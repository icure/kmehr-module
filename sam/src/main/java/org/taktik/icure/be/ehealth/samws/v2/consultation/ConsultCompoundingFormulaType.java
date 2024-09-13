//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
//


package org.taktik.icure.be.ehealth.samws.v2.consultation;

import org.taktik.icure.be.ehealth.samws.v2.compounding.common.CompoundingFormulaKeyType;
import org.taktik.icure.be.ehealth.samws.v2.compounding.common.SynonymType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ConsultCompoundingFormulaType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ConsultCompoundingFormulaType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:be:fgov:ehealth:samws:v2:compounding:common}CompoundingFormulaKeyType">
 *       &lt;sequence>
 *         &lt;element name="Synonym" type="{urn:be:fgov:ehealth:samws:v2:compounding:common}SynonymType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsultCompoundingFormulaType", propOrder = {
    "synonyms"
})
public class ConsultCompoundingFormulaType
    extends CompoundingFormulaKeyType
    implements Serializable
{

    private final static long serialVersionUID = 2L;
    @XmlElement(name = "Synonym", required = true)
    protected List<SynonymType> synonyms;

    /**
     * Gets the value of the synonyms property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the synonyms property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSynonyms().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SynonymType }
     *
     *
     */
    public List<SynonymType> getSynonyms() {
        if (synonyms == null) {
            synonyms = new ArrayList<SynonymType>();
        }
        return this.synonyms;
    }

}
