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

package prkmodel.repast;

import prkmodel.types.ModelElementType;
import prkmodel.types.Neuron;
import prkmodel.types.templates.AbstractNeuronGuestElement;

/**
 * Extends the functionality of {@link AbstractNeuronGuestElement} to integrate with the Repast Simphony framework.
 * Represents elements like mitochondria or Lewy bodies associated with neurons in the simulation.
 * 
 * @see prkmodel.types.templates.AbstractNeuronGuestElement
 * @see prkmodel.types.Neuron
 * @see prkmodel.types.ModelElementType
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public final class RepastNeuronGuestElement extends AbstractNeuronGuestElement {

    /**
     * Constructs a new {@code RepastNeuronGuestElement} with the specified neuron, type, and position.
     * 
     * @param neuron the neuron to which this guest element is attached
     * @param type the type of the guest element (e.g., mitochondria or Lewy body)
     * @param x the X coordinate of the guest element
     * @param y the Y coordinate of the guest element
     * @param z the Z coordinate of the guest element
     * @throws NullPointerException if the neuron or type is null
     * @throws IllegalArgumentException if the coordinates are invalid
     */
    public RepastNeuronGuestElement(Neuron neuron, ModelElementType type, double x, double y, double z) {
        super(neuron, type, x, y, z, true);
        RepastNervousSystemElementFactory.INSTANCE.getContext().add(this);
        RepastNervousSystemElementFactory.INSTANCE.getSpace().moveTo(this, getPosX(), getPosY(), getPosZ());
    }
}

