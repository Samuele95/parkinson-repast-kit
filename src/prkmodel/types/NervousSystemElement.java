/**
 * MIT License
 *
 * Copyright (c) 2024 Samuele95
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package prkmodel.types;

import java.util.List;

/**
 * Interface representing an element of the nervous system.
 * It defines the basic properties and behaviors of an element within the nervous system.
 * 
 * @see NervousSystem
 * @see ModelElementType
 * @see Neuron
 * @see prkmodel.types.Astrocyte
 * @see prkmodel.types.Microglia
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public interface NervousSystemElement {
    
    /**
     * Retrieves the nervous system which this element belongs to.
     * 
     * @return the {@link NervousSystem} instance which this element belongs to.
     */
    NervousSystem getNervousSystem();
    
    /**
     * Retrieves the type of this element.
     * 
     * @return the {@link ModelElementType} representing the type of this element.
     */
    ModelElementType getType();
    
    /**
     * Retrieves the position of this element in the form of a list of coordinates.
     * 
     * @return a {@link List} of {@link Double} representing the position coordinates (x, y, z).
     */
    List<Double> getPosition();
    
    /**
     * Retrieves the X coordinate of this element's position.
     * 
     * @return the X coordinate as a double.
     */
    double getPosX();
    
    /**
     * Retrieves the Y coordinate of this element's position.
     * 
     * @return the Y coordinate as a double.
     */
    double getPosY();
    
    /**
     * Retrieves the Z coordinate of this element's position.
     * 
     * @return the Z coordinate as a double.
     */
    double getPosZ();
    
    /**
     * Checks if this element has been marked as deleted.
     * 
     * @return true if the element is deleted, false otherwise.
     */
    boolean isDeleted();
    
    /**
     * Sets the X coordinate of this element's position.
     * If the value is negative, it defaults to 0.
     * 
     * @param x the new X coordinate.
     * @throws IllegalArgumentException if the coordinate is invalid.
     */
    void setPosX(double x);
    
    /**
     * Sets the Y coordinate of this element's position.
     * If the value is negative, it defaults to 0.
     * 
     * @param y the new Y coordinate.
     * @throws IllegalArgumentException if the coordinate is invalid.
     */
    void setPosY(double y);
    
    /**
     * Sets the Z coordinate of this element's position.
     * If the value is negative, it defaults to 0.
     * 
     * @param z the new Z coordinate.
     * @throws IllegalArgumentException if the coordinate is invalid.
     */
    void setPosZ(double z);
    
    /**
     * Marks this element as deleted.
     * 
     * @return true if the element was successfully marked as deleted, false otherwise.
     */
    boolean delete();
    
    /**
     * Computes the distance from this element to another specified element.
     * 
     * @param element the {@link NervousSystemElement} to compute the distance from.
     * @return the distance as a double.
     * @throws NullPointerException if the specified element is null.
     */
    double computeDistanceFrom(NervousSystemElement element);

    /**
     * Retrieves the distance vector from this element to another specified element.
     * 
     * @param element the {@link NervousSystemElement} to compute the distance vector from.
     * @return a {@link List} of {@link Double} representing the distance vector (dx, dy, dz).
     * @throws NullPointerException if the specified element is null.
     */
    List<Double> getDistanceVector(NervousSystemElement element);
}
