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

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Interface representing a network of neurons within the nervous system.
 * It defines the structure and functionalities of a neuron network.
 * 
 * @see NervousSystem
 * @see Neuron
 * @see NetworkEdge
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public interface NeuronNetwork {

    /**
     * Retrieves the nervous system associated with this neuron network.
     * 
     * @return the {@link NervousSystem} instance associated with this neuron network.
     */
    NervousSystem getNervousSystem();

    /**
     * Retrieves all neurons in the network.
     * 
     * @return a set of neurons in the network.
     * @see Neuron
     */
    Set<Neuron> getNeurons();

    /**
     * Retrieves all edges in the neuron network.
     * 
     * @return a set of network edges connecting neurons.
     * @see NetworkEdge
     */
    Set<NetworkEdge<Neuron>> getNetwork();

    /**
     * Checks if two neurons are connected.
     * 
     * @param firstNeuron the first neuron.
     * @param secondNeuron the second neuron.
     * @return true if the neurons are connected, false otherwise.
     * @throws NullPointerException if either neuron is null.
     * @see Neuron
     */
    boolean connected(Neuron firstNeuron, Neuron secondNeuron);

    /**
     * Adds a new neuron to the network.
     * 
     * @return true if the neuron was successfully added, false otherwise.
     * @see Neuron
     */
    boolean addNeuron();

    /**
     * Adds the specified number of neurons to the network.
     * 
     * @param neuronSize the number of neurons to add.
     * @return the updated neuron network.
     * @throws IllegalArgumentException if neuronSize is negative.
     * @see Neuron
     */
    NeuronNetwork addNeurons(int neuronSize);

    /**
     * Adds an edge between two neurons in the network.
     * 
     * @param firstNeuron the first neuron.
     * @param secondNeuron the second neuron.
     * @return true if the edge was successfully added, false otherwise.
     * @throws NullPointerException if either neuron is null.
     * @see Neuron
     * @see NetworkEdge
     */
    boolean addEdge(Neuron firstNeuron, Neuron secondNeuron);

    /**
     * Deletes an edge between two neurons in the network.
     * 
     * @param firstNeuron the first neuron.
     * @param secondNeuron the second neuron.
     * @return true if the edge was successfully deleted, false otherwise.
     * @throws NullPointerException if either neuron is null.
     * @see Neuron
     * @see NetworkEdge
     */
    boolean deleteEdge(Neuron firstNeuron, Neuron secondNeuron);

    /**
     * Deletes all edges associated with a neuron in the network.
     * 
     * @param neuron the neuron whose edges are to be deleted.
     * @return true if the edges were successfully deleted, false otherwise.
     * @throws NullPointerException if the neuron is null.
     * @see Neuron
     * @see NetworkEdge
     */
    boolean deleteEdge(Neuron neuron);

    /**
     * Deletes a specific edge in the network.
     * 
     * @param edge the edge to be deleted.
     * @return true if the edge was successfully deleted, false otherwise.
     * @throws NullPointerException if the edge is null.
     * @see NetworkEdge
     */
    boolean deleteEdge(NetworkEdge<Neuron> edge);

    /**
     * Deletes a neuron from the network.
     * 
     * @param neuron the neuron to be deleted.
     * @return true if the neuron was successfully deleted, false otherwise.
     * @throws NullPointerException if the neuron is null.
     * @see Neuron
     */
    boolean deleteNeuron(Neuron neuron);

    /**
     * Generates random connections between neurons in the network.
     * 
     * @return the updated neuron network.
     */
    NeuronNetwork generateRandomConnections();

    /**
     * Retrieves a stream of edges associated with a neuron.
     * 
     * @param neuron the neuron whose associated edges are to be retrieved.
     * @return a stream of network edges.
     * @throws NullPointerException if the neuron is null.
     * @see Neuron
     * @see NetworkEdge
     */
    Stream<NetworkEdge<Neuron>> associatedEdgesAsStream(Neuron neuron);

    /**
     * Retrieves a set of edges associated with a neuron.
     * 
     * @param neuron the neuron whose associated edges are to be retrieved.
     * @return a set of network edges.
     * @throws NullPointerException if the neuron is null.
     * @see Neuron
     * @see NetworkEdge
     */
    default Set<NetworkEdge<Neuron>> associatedEdges(Neuron neuron) {
        return associatedEdgesAsStream(neuron).collect(Collectors.toSet());
    }

    /**
     * Checks if a neuron is part of the network.
     * 
     * @param neuron the neuron to check.
     * @return true if the neuron is part of the network, false otherwise.
     * @throws NullPointerException if the neuron is null.
     * @see Neuron
     */
    default boolean isPartOfNetwork(Neuron neuron) {
        return getNeurons().contains(neuron);
    }

    /**
     * Adds the specified number of neurons to the network and generates random connections between them.
     * 
     * @param neuronSize the number of neurons to add.
     * @return the updated neuron network.
     * @throws IllegalArgumentException if neuronSize is negative.
     * @see Neuron
     */
    default NeuronNetwork addNeuronsAndConnect(int neuronSize) {
        return addNeurons(neuronSize).generateRandomConnections();
    }
}

