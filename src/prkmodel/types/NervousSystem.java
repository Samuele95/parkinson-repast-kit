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

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import prkmodel.utils.NervousSystemElementFactory;

/**
 * Interface representing the nervous system.
 * It defines the structure and functionalities of the nervous system in the simulation.
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public interface NervousSystem {

    /**
     * Gets the X dimension of the nervous system.
     * 
     * @return the X dimension
     */
    int getDimensionX();

    /**
     * Gets the Y dimension of the nervous system.
     * 
     * @return the Y dimension
     */
    int getDimensionY();

    /**
     * Gets the Z dimension of the nervous system.
     * 
     * @return the Z dimension
     */
    int getDimensionZ();

    /**
     * Gets the logger for logging purposes.
     * 
     * @return the logger
     */
    Logger getLogger();

    /**
     * Gets the pseudo-random number generator (PRNG).
     * 
     * @return the PRNG
     */
    Random getPNRG();

    /**
     * Gets the factory for creating nervous system elements.
     * 
     * @return the nervous system element factory
     * @see prkmodel.utils.NervousSystemElementFactory
     */
    NervousSystemElementFactory getFactory();

    /**
     * Gets a stream of all elements in the nervous system.
     * 
     * @return a stream of all elements
     * @see NervousSystemElement
     */
    Stream<NervousSystemElement> getElementsAsStream();

    /**
     * Gets the neuron network in the nervous system.
     * 
     * @return the neuron network
     * @see NeuronNetwork
     */
    NeuronNetwork getNeuronNetwork();

    /**
     * Gets the number of mitochondria per neuron.
     * 
     * @return the number of mitochondria per neuron
     */
    int getMitochondriaPerNeuron();

    /**
     * Gets the mitochondria transfer threshold.
     * 
     * @return the mitochondria transfer threshold
     */
    double getMitoTransferThreshold();

    /**
     * Gets the Lewy body degeneration threshold.
     * 
     * @return the Lewy body degeneration threshold
     */
    int getLewyBodyDegenerationThreshold();

    /**
     * Gets the base degeneration probability.
     * 
     * @return the base degeneration probability
     */
    double getbaseDegenerationProbability();

    /**
     * Adds an element to the nervous system.
     * 
     * @param element the element to be added
     * @throws NullPointerException if the element is null
     * @see NervousSystemElement
     */
    void addElement(NervousSystemElement element);

    /**
     * Adds the specified number of astrocytes to the nervous system.
     * 
     * @param astrocyteSize the number of astrocytes to add
     * @return the updated nervous system
     * @throws IllegalArgumentException if astrocyteSize is negative
     * @see Astrocyte
     */
    NervousSystem addAstrocytes(int astrocyteSize);

    /**
     * Adds the specified number of microglia to the nervous system.
     * 
     * @param microgliaSize the number of microglia to add
     * @return the updated nervous system
     * @throws IllegalArgumentException if microgliaSize is negative
     * @see Microglia
     */
    NervousSystem addMicroglia(int microgliaSize);

    /**
     * Adds the specified number of neurons to the nervous system.
     * 
     * @param neuronSize the number of neurons to add
     * @return the updated nervous system
     * @throws IllegalArgumentException if neuronSize is negative
     * @see Neuron
     */
    NervousSystem addNeurons(int neuronSize);

    /**
     * Sets the neuron network in the nervous system.
     * 
     * @param network the neuron network to set
     * @return the updated neuron network
     * @throws NullPointerException if the network is null
     * @see NeuronNetwork
     */
    NeuronNetwork setNeuronNetwork(NeuronNetwork network);

    /**
     * Filters out neurons from the nervous system.
     * 
     * @return the updated nervous system
     * @see Neuron
     */
    NervousSystem filterOutNeurons();

    /**
     * Performs a simulation step.
     */
    void step();

    /**
     * Gets all elements in the nervous system.
     * 
     * @return a set of all elements
     * @see NervousSystemElement
     */
    default Set<NervousSystemElement> getElements() {
        return getElementsAsStream().collect(Collectors.toSet());
    }

    /**
     * Gets a stream of active elements in the nervous system.
     * 
     * @return a stream of active elements
     * @see NervousSystemElement
     */
    default Stream<NervousSystemElement> getActiveElementsAsStream() {
        return getElementsAsStream().filter(el -> !el.isDeleted());
    }

    /**
     * Gets a stream of deleted elements in the nervous system.
     * 
     * @return a stream of deleted elements
     * @see NervousSystemElement
     */
    default Stream<NervousSystemElement> getDeletedElementsAsStream() {
        return getElementsAsStream().filter(NervousSystemElement::isDeleted);
    }

    /**
     * Gets all active elements in the nervous system.
     * 
     * @return a set of active elements
     * @see NervousSystemElement
     */
    default Set<NervousSystemElement> getActiveElements() {
        return getActiveElementsAsStream().collect(Collectors.toSet());
    }

    /**
     * Gets all deleted elements in the nervous system.
     * 
     * @return a set of deleted elements
     * @see NervousSystemElement
     */
    default Set<NervousSystemElement> getDeletedElements() {
        return getDeletedElementsAsStream().collect(Collectors.toSet());
    }

    /**
     * Gets a stream of all alive neurons in the nervous system.
     * 
     * @return a stream of alive neurons
     * @see Neuron
     */
    default Stream<Neuron> getAliveNeuronsAsStream() {
        return getNeurons().parallelStream().filter(n -> !n.isDead());
    }

    /**
     * Gets all alive neurons in the nervous system.
     * 
     * @return a set of alive neurons
     * @see Neuron
     */
    default Set<Neuron> getAliveNeurons() {
        return getAliveNeuronsAsStream().collect(Collectors.toSet());
    }

    /**
     * Gets all neurons in the nervous system.
     * 
     * @return a set of neurons
     * @see Neuron
     */
    default Set<Neuron> getNeurons() {
        return getNeuronNetwork().getNeurons();
    }

    /**
     * Gets a stream of all astrocytes in the nervous system.
     * 
     * @return a stream of astrocytes
     * @see Astrocyte
     */
    default Stream<Astrocyte> getAstrocytesAsStream() {
        return getElementsAsStream().filter(el -> (el instanceof Astrocyte)).map(Astrocyte.class::cast);
    }

    /**
     * Gets a stream of all microglia in the nervous system.
     * 
     * @return a stream of microglia
     * @see Microglia
     */
    default Stream<Microglia> getMicrogliaAsStream() {
        return getElementsAsStream().filter(el -> (el instanceof Microglia)).map(Microglia.class::cast);
    }

    /**
     * Gets a stream of all cytokines in the nervous system.
     * 
     * @return a stream of cytokines
     * @throws ClassCastException if an element is not of type {@code Cytokine<NervousSystemElement>}
     * @see Cytokine
     */
    @SuppressWarnings("unchecked")
    default Stream<Cytokine<NervousSystemElement>> getCytokinesAsStream() {
        return getElementsAsStream().filter(el -> (el instanceof Cytokine)).map(el -> ((Cytokine<NervousSystemElement>) el));
    }

    /**
     * Gets all astrocytes in the nervous system.
     * 
     * @return a set of astrocytes
     * @see Astrocyte
     */
    default Set<Astrocyte> getAstrocytes() {
        return getAstrocytesAsStream().collect(Collectors.toSet());
    }

    /**
     * Gets all microglia in the nervous system.
     * 
     * @return a set of microglia
     * @see Microglia
     */
    default Set<Microglia> getMicroglia() {
        return getMicrogliaAsStream().collect(Collectors.toSet());
    }

    /**
     * Gets all cytokines in the nervous system.
     * 
     * @return a set of cytokines
     * @see Cytokine
     */
    default Set<Cytokine<NervousSystemElement>> getCytokines() {
        return getCytokinesAsStream().collect(Collectors.toSet());
    }

    /**
     * Gets a stream of all guest elements in the nervous system.
     * 
     * @return a stream of guest elements
     * @see NeuronGuestElement
     */
    default Stream<NeuronGuestElement> getGuestElementsAsStream() {
        return getElementsAsStream().filter(el -> (el instanceof NeuronGuestElement)).map(NeuronGuestElement.class::cast);
    }

    /**
     * Gets all guest elements in the nervous system.
     * 
     * @return a list of guest elements
     * @see NeuronGuestElement
     */
    default List<NeuronGuestElement> getGuestElements() {
        return getGuestElementsAsStream().toList();
    }

    /**
     * Gets a random element from the nervous system.
     * 
     * @return an optional containing a random element if present, otherwise an empty optional
     * @throws IllegalArgumentException if there are no active elements
     * @see NervousSystemElement
     */
    default Optional<NervousSystemElement> getRandomElement() {
        Set<NervousSystemElement> activeElements = getActiveElements();
        if (activeElements.isEmpty()) {
            throw new IllegalArgumentException("No active elements in the nervous system.");
        }
        return activeElements.stream().skip(getPNRG().nextInt(activeElements.size())).findFirst();
    }

    /**
     * Gets a random neuron from the nervous system.
     * 
     * @return an optional containing a random neuron if present, otherwise an empty optional
     * @throws IllegalArgumentException if there are no alive neurons
     * @see Neuron
     */
    default Optional<Neuron> getRandomNeuron() {
        Set<Neuron> aliveNeurons = getAliveNeurons();
        if (aliveNeurons.isEmpty()) {
            throw new IllegalArgumentException("No alive neurons in the nervous system.");
        }
        return aliveNeurons.stream().skip(getPNRG().nextInt(aliveNeurons.size())).findFirst();
    }    
}


