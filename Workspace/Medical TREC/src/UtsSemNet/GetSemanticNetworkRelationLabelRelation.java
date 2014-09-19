
package UtsSemNet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getSemanticNetworkRelationLabelRelation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getSemanticNetworkRelationLabelRelation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ticket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="styRelationLabel1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="styRelationLabel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="styRelationLabel2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSemanticNetworkRelationLabelRelation", propOrder = {
    "ticket",
    "version",
    "styRelationLabel1",
    "styRelationLabel",
    "styRelationLabel2"
})
public class GetSemanticNetworkRelationLabelRelation {

    protected String ticket;
    protected String version;
    protected String styRelationLabel1;
    protected String styRelationLabel;
    protected String styRelationLabel2;

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
     * Gets the value of the styRelationLabel1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStyRelationLabel1() {
        return styRelationLabel1;
    }

    /**
     * Sets the value of the styRelationLabel1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStyRelationLabel1(String value) {
        this.styRelationLabel1 = value;
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
     * Gets the value of the styRelationLabel2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStyRelationLabel2() {
        return styRelationLabel2;
    }

    /**
     * Sets the value of the styRelationLabel2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStyRelationLabel2(String value) {
        this.styRelationLabel2 = value;
    }

}
