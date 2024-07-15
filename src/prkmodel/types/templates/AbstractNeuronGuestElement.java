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

import java.util.Objects;
import prkmodel.types.ModelElementType;
import prkmodel.types.Neuron;
import prkmodel.types.NeuronGuestElement;

/**
 * Abstract base class for elements that are guests of neurons.
 * Provides common functionality and properties for neuron guest elements in the nervous system.
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
 * <p>Subclasses could extend the following methods to make them more suited to the concrete implementations:</p>
 * <ul>
 *   <li>{@link #delete()}</li>
 * </ul>
 * </p>
 * 
 * @see NeuronGuestElement
 * @see Neuron
 * @see NervousSystemElement
 * @see NervousSystem
 * @see ModelElementType
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public abstract class AbstractNeuronGuestElement extends AbstractNervousSystemElement implements NeuronGuestElement {
    
    private Neuron neuron;

    /**
     * Constructs a new {@code AbstractNeuronGuestElement} with the specified parameters.
     * 
     * @param neuron the neuron this guest element is attached to
     * @param type the type of the element
     * @param x the X coordinate of the element
     * @param y the Y coordinate of the element
     * @param z the Z coordinate of the element
     * @param randomOffsets whether to apply random offsets to the coordinates
     * @throws NullPointerException if the neuron or type is null
     */
    public AbstractNeuronGuestElement(Neuron neuron, ModelElementType type, double x, double y, double z, boolean randomOffsets) {
        super(neuron.getNervousSystem(), type, x, y, z, randomOffsets);
        this.neuron = Objects.requireNonNull(neuron);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Neuron getAttachedNeuron() {
        return neuron;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete() {
        boolean result = super.delete();
        neuron = null;
        return result;
    }

    /**
     * Returns a string representation of the neuron guest element.
     * 
     * @return a string representation of the element
     */
    @Override
    public String toString() {
        return super.toString();
    }
}

