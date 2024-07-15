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

/**
 * Interface representing an astrocyte in the nervous system. An astrocyte is a type of glial cell 
 * in the nervous system that provides support and protection for neurons, 
 * as well as targeting cytokines.
 * 
 * <p>
 * It extends the {@link NervousSystemElement} interface to include astrocyte-specific behaviors and properties.
 * </p>
 * @see NervousSystemElement
 * @see Neuron
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public interface Astrocyte extends NervousSystemElement {

    /**
     * Retrieves the number of mitochondria transfers this astrocyte has performed.
     * 
     * @return the number of mitochondria transfers.
     */
    int getMitochondriaTransferCount();

    /**
     * Activates the astrocyte, enabling its functionalities such as neuron support 
     * and mitochondria transfer.
     */
    void activate();

    /**
     * Deactivates the astrocyte, disabling its functionalities.
     */
    void deactivate();

    /**
     * Checks if the astrocyte is active.
     * 
     * @return true if the astrocyte is active, false otherwise.
     */
    boolean isActive();

    /**
     * Transfers mitochondria to a specified neuron in need.
     * <p>
     * Mitochondria transfer is essential for maintaining neuronal health and 
     * function, particularly in neurons that are under stress or have impaired 
     * mitochondria.
     * </p>
     * 
     * @param neuronNeedingMitochondria the neuron needing mitochondria.
     * @throws NullPointerException if the neuronNeedingMitochondria is null.
     * @see Neuron
     */
    void transferMitochondria(Neuron neuronNeedingMitochondria);

    /**
     * Provides support to neurons, potentially influencing their state or behavior.
     * <p>
     * This may include functions such as maintaining the extracellular ion balance, 
     * supplying nutrients, and modulating synaptic activity.
     * </p>
     */
    void supportNeurons();
}



