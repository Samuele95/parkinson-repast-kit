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

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import prkmodel.types.ModelElementType;
import prkmodel.types.NervousSystem;
import prkmodel.types.NetworkEdge;
import prkmodel.types.Neuron;
import prkmodel.types.NeuronNetwork;
import repast.simphony.random.RandomHelper;

/**
 * Default implementation of the {@link NeuronNetwork} interface.
 * Represents the network of neurons in the nervous system with default behaviors and properties.
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
 * @see NeuronNetwork
 * @see NervousSystem
 * @see NetworkEdge
 * @see Neuron
 * @see ModelElementType
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public class DefaultNeuronNetwork implements NeuronNetwork {

    private final Set<Neuron> neurons;
    private final Set<NetworkEdge<Neuron>> network;
    private final NervousSystem nsystem;

    /**
     * Constructs a new {@code DefaultNeuronNetwork} with the specified nervous system.
     * 
     * @param nsystem the nervous system this network belongs to
     * @throws NullPointerException if the nervous system is null
     */
    public DefaultNeuronNetwork(NervousSystem nsystem) {
        this.nsystem = Objects.requireNonNull(nsystem);
        this.neurons = new HashSet<>();
        this.network = new HashSet<>();
        
        nsystem.getFactory().setNeuronNetwork(this);
        loggingNetworkInit();
    }

    /**
     * Represents an edge in the neuron network.
     */
    private record NeuronNetworkEdge(Neuron first, Neuron second) implements NetworkEdge<Neuron> {
        public NeuronNetworkEdge(Neuron first, Neuron second) {
            this.first = Objects.requireNonNull(first);
            this.second = Objects.requireNonNull(second);
        }
        
        @Override 
        public boolean equals(Object o) {
            if (o == this)
                return true;
            if (!(o instanceof NeuronNetworkEdge))
                return false;
            NeuronNetworkEdge pn = (NeuronNetworkEdge) o;
            return (pn.first.equals(this.first) || pn.first.equals(this.second)) &&
                   (pn.second.equals(this.second) || pn.second.equals(this.first));
        }
        
        @Override 
        public int hashCode() {
            return Objects.hash(first, second);
        }
        
        @Override
        public String toString() {
            return String.format("NeuronNetworkEdge: (%s - %s)", first, second);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NervousSystem getNervousSystem() {
        return nsystem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Neuron> getNeurons() {
        return neurons;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<NetworkEdge<Neuron>> getNetwork() {
        return network;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean connected(Neuron firstNeuron, Neuron secondNeuron) { 
        if (isPartOfNetwork(firstNeuron) && isPartOfNetwork(secondNeuron))
            return network.contains(new NeuronNetworkEdge(firstNeuron, secondNeuron));
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<NetworkEdge<Neuron>> associatedEdgesAsStream(Neuron neuron) {
        return network.parallelStream()
                .filter(ed -> Objects.equals(neuron, ed.first()) || Objects.equals(ed.second(), neuron));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEdge(Neuron firstNeuron, Neuron secondNeuron) {
        if (isPartOfNetwork(firstNeuron) && isPartOfNetwork(secondNeuron)) {
            NetworkEdge<Neuron> edge = new NeuronNetworkEdge(firstNeuron, secondNeuron);
            nsystem.getLogger().info(String.format("%s added", edge));
            return network.add(edge);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteEdge(NetworkEdge<Neuron> edge) {
        if (isPartOfNetwork(Objects.requireNonNull(edge).first()) && isPartOfNetwork(edge.second())) {
            nsystem.getLogger().info(String.format("%s deleted", edge));
            return network.remove(edge);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteEdge(Neuron firstNeuron, Neuron secondNeuron) {
        return deleteEdge(new NeuronNetworkEdge(firstNeuron, secondNeuron));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteEdge(Neuron neuron) {
        return associatedEdgesAsStream(neuron)
                .map(ed -> deleteEdge(ed))
                .allMatch(result -> result.equals(true));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteNeuron(Neuron neuron) {
        if (neurons.remove(Objects.requireNonNull(neuron)))
            return deleteEdge(neuron);
        neuron.delete();
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addNeuron() {
        return this.getNeurons().add((Neuron) getNervousSystem().getFactory().create(ModelElementType.NEURON, RandomHelper.nextDoubleFromTo(0, 49), RandomHelper.nextDoubleFromTo(0, 49), RandomHelper.nextDoubleFromTo(0, 49)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DefaultNeuronNetwork addNeurons(int neuronSize) {
        for (int i = 0; i < neuronSize; i++) 
            addNeuron();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DefaultNeuronNetwork generateRandomConnections() {
        for (Neuron n1: getNeurons()) {
            for (Neuron n2: getNeurons()) {
                if ((!n1.equals(n2)) && (RandomHelper.nextDoubleFromTo(0, 1) < 0.05))
                    addEdge(n1, n2);
            }
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new StringBuilder()
                .append(String.format("NeuronNetwork@%d", this.hashCode())).append(System.lineSeparator())
                .append(String.format("%4s", String.format("Neurons: %d", getNeurons().size()))).append(System.lineSeparator())
                .append(String.format("%4s", String.format("Connections: %d", network.size())))
                .toString();
    }

    private void loggingNetworkInit() {
        nsystem.getLogger().info(
                new StringBuilder()
                    .append("Neuron Network created").append(System.lineSeparator())
                    .append(this.toString()).append(System.lineSeparator()).toString());
    }
}

