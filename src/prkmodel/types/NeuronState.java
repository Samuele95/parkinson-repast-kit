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
 * Interface representing the state of a neuron in the nervous system.
 * It defines the properties and behaviors related to the state of a neuron.
 * 
 * @see Neuron
 * @see NeuronStateDescription
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public interface NeuronState {

    /**
     * Retrieves the current state description of the neuron.
     * 
     * @return the {@link NeuronStateDescription} of the neuron.
     */
    NeuronStateDescription getType();

    /**
     * Retrieves the current dopamine level of the neuron.
     * 
     * @return the dopamine level as a double.
     */
    double getDopamineLevel();

    /**
     * Retrieves the current myelin level of the neuron.
     * 
     * @return the myelin level as an integer.
     */
    int getMyelinLevel();

    /**
     * Sets the state description of the neuron.
     * 
     * @param type the new {@link NeuronStateDescription} of the neuron.
     * @throws NullPointerException if the type is null.
     */
    void setType(NeuronStateDescription type);

    /**
     * Updates the dopamine level of the neuron by a specified amount.
     * 
     * @param t the amount to update the dopamine level by.
     */
    void updateDopamine(double t);

    /**
     * Updates the myelin level of the neuron by a specified amount.
     * 
     * @param t the amount to update the myelin level by.
     */
    void updateMyelin(int t);

    /**
     * Increases the dopamine production of the neuron.
     */
    void increaseDopamineProduction();
}



