/**
  *  This file is part of Carma (Retroduction.org).
  *
  *  Carma is free software: you can redistribute it and/or modify
  *  it under the terms of the GNU General Public License as published by
  *  the Free Software Foundation, either version 3 of the License, or
  *  (at your option) any later version.
  *
  *  Carma is distributed in the hope that it will be useful,
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *  GNU General Public License for more details.
  *
  *  You should have received a copy of the GNU General Public License
  *  along with Carma.  If not, see <http://www.gnu.org/licenses/>.
  *
  * Copyright Retroduction.org (c) 2007-2010
  */
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.5-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.06.12 at 08:28:31 PM CEST 
//


package com.retroduction.carma.xmlreport.om;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MutationRatio complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MutationRatio">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mutationCount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="survivorCount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MutationRatio", propOrder = {
    "mutationCount",
    "survivorCount"
})
public class MutationRatio {

    protected long mutationCount;
    protected long survivorCount;

    /**
     * Gets the value of the mutationCount property.
     * 
     */
    public long getMutationCount() {
        return mutationCount;
    }

    /**
     * Sets the value of the mutationCount property.
     * 
     */
    public void setMutationCount(long value) {
        this.mutationCount = value;
    }

    /**
     * Gets the value of the survivorCount property.
     * 
     */
    public long getSurvivorCount() {
        return survivorCount;
    }

    /**
     * Sets the value of the survivorCount property.
     * 
     */
    public void setSurvivorCount(long value) {
        this.survivorCount = value;
    }

}
