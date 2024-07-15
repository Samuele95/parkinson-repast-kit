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
import prkmodel.types.NeuronNetwork;
import prkmodel.types.templates.DefaultNeuron;
import repast.simphony.engine.schedule.ScheduledMethod;

/**
 * Extends the functionality of {@link DefaultNeuron} to integrate with the Repast Simphony framework.
 * Provides additional methods for scheduling and deleting neurons in the Repast context.
 * 
 * @see prkmodel.types.templates.DefaultNeuron
 * @see prkmodel.types.NeuronNetwork
 * @see prkmodel.types.ModelElementType
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public final class RepastNeuron extends DefaultNeuron {

    /**
     * Constructs a new {@code RepastNeuron} with the specified neuron network and position.
     * 
     * @param neuronNetwork the neuron network this neuron belongs to
     * @param x the X coordinate of the neuron
     * @param y the Y coordinate of the neuron
     * @param z the Z coordinate of the neuron
     * @throws NullPointerException if the neuron network is null
     * @throws IllegalArgumentException if the coordinates are invalid
     */
    public RepastNeuron(NeuronNetwork neuronNetwork, double x, double y, double z) {
        super(neuronNetwork, ModelElementType.NEURON, x, y, z);

        RepastNervousSystemElementFactory.INSTANCE.getContext().add(this);
        RepastNervousSystemElementFactory.INSTANCE.getSpace().moveTo(this, getPosX(), getPosY(), getPosZ());
    }

    /**
     * Scheduled method to be called at regular intervals to perform neuron work.
     * Invokes the {@link DefaultNeuron#work()} method.
     */
    @ScheduledMethod(start = 1, interval = 1)
    public void step() {
        super.work();
    }

    /**
     * {@inheritDoc}
     * 
     * Deletes this neuron from the Repast context.
     */
    @Override
    public boolean delete() {
        boolean result = super.delete();
        RepastNervousSystemElementFactory.INSTANCE.getContext().remove(this);
        return result;
    }
}

