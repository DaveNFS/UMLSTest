
package UtsSemNet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getSemanticTypeRelation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getSemanticTypeRelation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ticket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="styRelationLabel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="relatedSty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSemanticTypeRelation", propOrder = {
    "ticket",
    "version",
    "sty",
    "styRelationLabel",
    "relatedSty"
})
public class GetSemanticTypeRelation {

    protected String ticket;
    protected String version;
    protected String sty;
    protected String styRelationLabel;
    protected String relatedSty;

    /**
     * Gets the value of the ticket property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * Sets the value of the ticket property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTicket(String value) {
        this.ticket = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the sty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSty() {
        return sty;
    }

    /**
     * Sets the value of the sty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSty(String value) {
        this.sty = value;
    }

    /**
     * Gets the value of the styRelationLabel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStyRelationLabel() {
        return styRelationLabel;
    }

    /**
     * Sets the value of the styRelationLabel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStyRelationLabel(String value) {
        this.styRelationLabel = value;
    }

    /**
     * Gets the value of the relatedSty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelatedSty() {
        return relatedSty;
    }

    /**
     * Sets the value of the relatedSty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelatedSty(String value) {
        this.relatedSty = value;
    }

}
