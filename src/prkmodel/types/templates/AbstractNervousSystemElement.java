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

package prkmodel.types.templates;

import java.util.List;
import java.util.Objects;
import prkmodel.types.ModelElementType;
import prkmodel.types.NervousSystem;
import prkmodel.types.NervousSystemElement;

/**
 * Abstract base class for nervous system elements.
 * Provides common functionality and properties for elements in the nervous system.
 * 
 * <p>To implement a subclass:
 * <ul>
 *   <li>Ensure the constructor properly initializes inherited fields.
 *   <li>Override methods to provide specific behavior.
 *   <li>Document the purpose of any new methods or overridden methods.
 *   <li>Use {@code super} to call the superclass method when overriding to extend or modify its behavior.
 * </ul>
 * 
 * @see NervousSystemElement
 * @see NervousSystem
 * @see ModelElementType
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public abstract class AbstractNervousSystemElement implements NervousSystemElement {
    
    private final NervousSystem nsystem;
    private final ModelElementType type;
    private double x;
    private double y;
    private double z;
    private boolean deleted;
    
    /**
     * Constructs a new {@code AbstractNervousSystemElement} with the specified parameters.
     * 
     * @param nsystem the nervous system this element belongs to
     * @param type the type of the element
     * @param x the X coordinate of the element
     * @param y the Y coordinate of the element
     * @param z the Z coordinate of the element
     * @param randomOffsets whether to apply random offsets to the coordinates
     * @throws NullPointerException if the nervous system or type is null
     * @throws IllegalArgumentException if the coordinates are invalid
     */
    public AbstractNervousSystemElement(NervousSystem nsystem, ModelElementType type, double x, double y, double z, boolean randomOffsets) {
        this.nsystem = Objects.requireNonNull(nsystem);
        this.type = Objects.requireNonNull(type);
        this.deleted = false;
        // If randomOffsets is true, it sets the position with a little offset w.r.t. the given x,y,z value
        setPosX(x + ((randomOffsets) ? nsystem.getPNRG().nextDouble(-1, 1) : 0.0));
        setPosY(y + ((randomOffsets) ? nsystem.getPNRG().nextDouble(-1, 1) : 0.0));
        setPosZ(z + ((randomOffsets) ? nsystem.getPNRG().nextDouble(-1, 1) : 0.0));
        
        nsystem.addElement(this);
        nsystem.getLogger().info(new StringBuilder(String.format("%s created", this)).append(System.lineSeparator()).toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NervousSystem getNervousSystem() {
        return nsystem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelElementType getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Double> getPosition() {
        return List.of(x, y, z);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPosX() {
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPosY() {
        return y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPosZ() {
        return z;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosX(double x) {
    	if (x > getNervousSystem().getDimensionX())
    		throw new IllegalArgumentException("Invalid X coordinate");
        this.x = Math.max(x, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosY(double y) {
    	if (y > getNervousSystem().getDimensionY())
    		throw new IllegalArgumentException("Invalid Y coordinate");
        this.y = Math.max(y, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosZ(double z) {
    	if (z > getNervousSystem().getDimensionZ())
    		throw new IllegalArgumentException("Invalid Z coordinate");
        this.z = Math.max(z, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete() {
        nsystem.getLogger().info(new StringBuilder(String.format("%s deleted", this)).append(System.lineSeparator()).toString());
        return deleted = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Double> getDistanceVector(NervousSystemElement element) {
        return List.of(
                element.getPosX() - x,
                element.getPosY() - y,
                element.getPosZ() - z
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double computeDistanceFrom(NervousSystemElement element) {
        return Math.sqrt(getDistanceVector(element).stream().mapToDouble(i -> Math.pow(i, 2)).sum());
    }

    /**
     * Returns a string representation of the nervous system element.
     * 
     * @return a string representation of the element
     */
    @Override
    public String toString() {
        return String.format("%s@%d pos:(%.2f; %.2f; %.2f)", type, this.hashCode(), x, y, z);
    }
}

