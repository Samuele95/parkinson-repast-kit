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

package prkmodel.utils;

import prkmodel.types.ModelElementType;
import prkmodel.types.NervousSystem;
import prkmodel.types.NervousSystemElement;
import prkmodel.types.NeuronNetwork;

/**
 * Interface representing a factory for creating elements of the nervous system.
 * It provides methods to create and configure {@link NervousSystemElement} instances.
 * 
 * <p>This interface follows the <a href="https://en.wikipedia.org/wiki/Factory_method_pattern">Factory design pattern</a>, 
 * which promotes the creation of objects without specifying the exact class of object that will be created.</p>
 * 
 * @param <T> the type of object that this factory creates
 * 
 * @see ModelElementType
 * @see NervousSystem
 * @see NervousSystemElement
 * @see NeuronNetwork
 * @see <a href="https://en.wikipedia.org/wiki/Factory_method_pattern">Factory Method Pattern (Gang of Four)</a>
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public interface NervousSystemElementFactory {

    /**
     * Creates a new {@link NervousSystemElement} of the specified type at the given coordinates.
     * 
     * @param type the type of the element to create.
     * @param x the X coordinate for the element.
     * @param y the Y coordinate for the element.
     * @param z the Z coordinate for the element.
     * @return the created {@link NervousSystemElement}.
     * @throws NullPointerException if the type is null.
     * @throws IllegalArgumentException if the coordinates are invalid.
     */
    NervousSystemElement create(ModelElementType type, double x, double y, double z);

    /**
     * Sets the target element for this factory.
     * 
     * @param target the target {@link NervousSystemElement}.
     * @return the {@link NervousSystemElementFactory} instance.
     * @throws NullPointerException if the target is null.
     */
    NervousSystemElementFactory setTarget(NervousSystemElement target);

    /**
     * Sets the parent element for this factory.
     * 
     * @param parent the parent {@link NervousSystemElement}.
     * @return the {@link NervousSystemElementFactory} instance.
     * @throws NullPointerException if the parent is null.
     */
    NervousSystemElementFactory setParent(NervousSystemElement parent);

    /**
     * Sets the neuron network for this factory.
     * 
     * @param neuronNetwork the {@link NeuronNetwork}.
     * @return the {@link NervousSystemElementFactory} instance.
     * @throws NullPointerException if the neuron network is null.
     */
    NervousSystemElementFactory setNeuronNetwork(NeuronNetwork neuronNetwork);

    /**
     * Sets the nervous system for this factory.
     * 
     * @param nsystem the {@link NervousSystem}.
     * @return the {@link NervousSystemElementFactory} instance.
     * @throws NullPointerException if the nervous system is null.
     */
    NervousSystemElementFactory setNervousSystem(NervousSystem nsystem);

    /**
     * Retrieves the nervous system associated with this factory.
     * 
     * @return the {@link NervousSystem}.
     */
    NervousSystem getNervousSystem();

    /**
     * Retrieves the neuron network associated with this factory.
     * 
     * @return the {@link NeuronNetwork}.
     */
    NeuronNetwork getNeuronNetwork();

    /**
     * Retrieves the target element associated with this factory.
     * 
     * @return the target {@link NervousSystemElement}.
     */
    NervousSystemElement getTarget();

    /**
     * Retrieves the parent element associated with this factory.
     * 
     * @return the parent {@link NervousSystemElement}.
     */
    NervousSystemElement getParent();
}
