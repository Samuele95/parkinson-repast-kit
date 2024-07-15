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
import prkmodel.types.ModelElementType;
import prkmodel.types.MoveableNervousSystemElement;
import prkmodel.types.NervousSystem;
import prkmodel.types.NervousSystemElement;

/**
 * Abstract base class for moveable nervous system elements.
 * Provides common functionality and properties for elements in the nervous system that can move.
 * 
 * <p>To implement a subclass:
 * <ul>
 *   <li>Ensure the constructor properly initializes inherited fields.
 *   <li>Override methods to provide specific behavior.
 *   <li>Document the purpose of any new methods or overridden methods.
 *   <li>Use {@code super} to call the superclass method when overriding to extend or modify its behavior.
 * </ul>
 * </p>
 * 
 * @see MoveableNervousSystemElement
 * @see NervousSystemElement
 * @see NervousSystem
 * @see ModelElementType
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public abstract class AbstractMoveableNervousSystemElement extends AbstractNervousSystemElement implements MoveableNervousSystemElement {
    
    /**
     * Constructs a new {@code AbstractMoveableNervousSystemElement} with the specified parameters.
     * 
     * @param nsystem the nervous system this element belongs to
     * @param type the type of the element
     * @param x the X coordinate of the element
     * @param y the Y coordinate of the element
     * @param z the Z coordinate of the element
     * @param randomOffset whether to apply random offsets to the coordinates
     * @throws NullPointerException if the nervous system or type is null
     */
    public AbstractMoveableNervousSystemElement(NervousSystem nsystem, ModelElementType type, double x, double y, double z, boolean randomOffset) {
        super(nsystem, type, x, y, z, randomOffset);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveToPosition(double x, double y, double z) {
        this.setPosX(x);
        this.setPosY(y);
        this.setPosZ(z);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move() {
        this.moveToPosition(
                getPosX() + getNervousSystem().getPNRG().nextDouble(-0.5, 0.5), 
                getPosY() + getNervousSystem().getPNRG().nextDouble(-0.5, 0.5), 
                getPosZ() + getNervousSystem().getPNRG().nextDouble(-0.5, 0.5)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveTowardsElement(NervousSystemElement target, double stepSize) {
        List<Double> distanceVector = this.getDistanceVector(target);
        this.moveToPosition(
                this.getPosX() + (distanceVector.get(0) * stepSize), 
                this.getPosY() + (distanceVector.get(1) * stepSize), 
                this.getPosZ() + (distanceVector.get(2) * stepSize)
        );
    }
}

