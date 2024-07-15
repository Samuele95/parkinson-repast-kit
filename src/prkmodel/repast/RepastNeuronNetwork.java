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

import prkmodel.types.NervousSystem;
import prkmodel.types.NetworkEdge;
import prkmodel.types.Neuron;
import prkmodel.types.templates.DefaultNeuronNetwork;
import repast.simphony.space.graph.RepastEdge;

/**
 * Extends the functionality of {@link DefaultNeuronNetwork} to integrate with the Repast Simphony framework.
 * Provides additional methods for adding and deleting edges in the network.
 * 
 * @see prkmodel.types.templates.DefaultNeuronNetwork
 * @see prkmodel.types.NervousSystem
 * @see prkmodel.types.NetworkEdge
 * @see prkmodel.types.Neuron
 * @see repast.simphony.space.graph.RepastEdge
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public final class RepastNeuronNetwork extends DefaultNeuronNetwork {

    /**
     * Constructs a new {@code RepastNeuronNetwork} with the specified nervous system.
     * 
     * @param nsystem the nervous system this network belongs to
     * @throws NullPointerException if the nervous system is null
     */
    public RepastNeuronNetwork(NervousSystem nsystem) {
        super(nsystem);
    }

    /**
     * {@inheritDoc}
     * 
     * Adds an edge between two neurons in the Repast network.
     */
    @Override
    public boolean addEdge(Neuron firstNeuron, Neuron secondNeuron) {
        boolean result = super.addEdge(firstNeuron, secondNeuron);
        RepastNervousSystemElementFactory.INSTANCE.getNetwork().addEdge(firstNeuron, secondNeuron);
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * Deletes an edge between two neurons in the Repast network.
     */
    @Override
    public boolean deleteEdge(NetworkEdge<Neuron> edge) {
        boolean result = super.deleteEdge(edge);
        RepastEdge<Object> targetEdge;
        if ((targetEdge = RepastNervousSystemElementFactory.INSTANCE.getNetwork().getEdge(edge.first(), edge.second())) != null) 
            RepastNervousSystemElementFactory.INSTANCE.getNetwork().removeEdge(targetEdge);
        if ((targetEdge = RepastNervousSystemElementFactory.INSTANCE.getNetwork().getEdge(edge.second(), edge.first())) != null) 
            RepastNervousSystemElementFactory.INSTANCE.getNetwork().removeEdge(targetEdge);     
        return result;
    }
}

