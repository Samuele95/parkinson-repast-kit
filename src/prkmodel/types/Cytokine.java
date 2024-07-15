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
 * Interface representing a cytokine in the nervous system. A cytokine is a small protein important 
 * in cell signaling, and it can target various cells in the nervous system.
 * <p>
 * This interface defines the behavior of cytokines, including targeting, 
 * interaction, and lifecycle management.
 * </p>
 * 
 * @param <T> the type of the target nervous system element
 * 
 * @see NervousSystemElement
 * @see Microglia
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public interface Cytokine<T extends NervousSystemElement> extends NervousSystemElement {

    /**
     * Retrieves the target nervous system element for this cytokine.
     * 
     * @return the target element of type T.
     */
    T getTarget();

    /**
     * Retrieves the parent microglia that produced this cytokine.
     * 
     * @return the parent {@link Microglia}.
     */
    Microglia getParentMicroglia();

    /**
     * Attaches the cytokine to its target element.
     * 
     * @throws IllegalStateException if the cytokine is already attached.
     */
    void attachToTargets();

    /**
     * Interacts with the target element, potentially altering its state or behavior.
     * 
     * @throws IllegalStateException if the cytokine is not attached to any target.
     */
    void interactWithTarget();

    /**
     * Checks if the cytokine is attached to its target.
     * 
     * @return true if the cytokine is attached to its target, false otherwise.
     */
    boolean isAttachedToTarget();

    /**
     * Performs the actions or behaviors of the cytokine in a simulation step.
     */
    void step();
}

