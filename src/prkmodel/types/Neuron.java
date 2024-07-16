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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Interface representing a neuron in the nervous system.
 * It extends the {@link NervousSystemElement} interface to include neuron-specific behaviors and properties.
 * 
 * @see NervousSystemElement
 * @see NeuronState
 * @see NeuronNetwork
 * @see ModelElementType
 * @see NeuronGuestElement
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public interface Neuron extends NervousSystemElement {

    /**
     * Retrieves the current state of the neuron.
     * 
     * @return the {@link NeuronState} of the neuron.
     */
    NeuronState getState();

    /**
     * Retrieves the neuron network associated with this neuron.
     * 
     * @return the {@link NeuronNetwork} instance associated with this neuron.
     */
    NeuronNetwork getNetwork();

    /**
     * Retrieves a stream of neurons adjacent to this neuron in the network.
     * 
     * @return a stream of adjacent neurons.
     * @see Neuron
     */
    Stream<Neuron> getAdjacentNeuronsAsStream();

    /**
     * Checks if the dopamine level of the neuron is low.
     * 
     * @return true if the dopamine level is low, false otherwise.
     */
    boolean isDopamineLevelLow();

    /**
     * Checks if the neuron has excess mitochondria.
     * 
     * @return true if the neuron has excess mitochondria, false otherwise.
     */
    boolean hasExcessMitochondria();

    /**
     * Determines if the neuron should degenerate based on its current state and conditions.
     * 
     * @return true if the neuron should degenerate, false otherwise.
     */
    boolean shouldDegenerate();

    /**
     * Generates a guest element of a specified type with a given probability.
     * 
     * @param elementType the type of the guest element to generate.
     * @param probability the probability of generating the guest element.
     * @return an optional containing the generated guest element if successful, otherwise an empty optional.
     * @see ModelElementType
     * @see NeuronGuestElement
     */
    Optional<NeuronGuestElement> generateGuestElement(ModelElementType elementType, double probability);

    /**
     * Deletes a specified guest element from the neuron.
     * 
     * @param element the guest element to delete.
     * @see NeuronGuestElement
     */
    void deleteGuestElement(NeuronGuestElement element);

    /**
     * Deletes a random guest element of a specified type from the neuron.
     * 
     * @param type the type of the guest element to delete.
     * @return true if a guest element was successfully deleted, false otherwise.
     * @see ModelElementType
     * @see NeuronGuestElement
     */
    boolean deleteRandomGuestElement(ModelElementType type);

    /**
     * Sends mitochondria to another specified neuron in need.
     * 
     * @param neuronNeedingMitochondria the neuron needing mitochondria.
     * @see Neuron
     */
    void sendMitochondria(Neuron neuronNeedingMitochondria);

    /**
     * Requests mitochondria from other neurons or astrocytes.
     */
    void requestMitochondria();

    /**
     * Performs the work or activity of the neuron in a simulation step.
     */
    void work();

    /**
     * {@inheritDoc}
     */
    @Override
    default NervousSystem getNervousSystem() {
        return getNetwork().getNervousSystem();
    }

    /**
     * Generates mitochondria within the neuron with a given probability.
     * 
     * @param probability the probability of generating mitochondria.
     * @return an optional containing the generated mitochondria if successful, otherwise an empty optional.
     * @see NeuronGuestElement
     */
    default Optional<NeuronGuestElement> generateMitochondria(double probability) {
        return generateGuestElement(ModelElementType.MITOCHONDRIA, probability);
    }

    /**
     * Generates Lewy bodies within the neuron with a given probability.
     * 
     * @param probability the probability of generating Lewy bodies.
     * @return an optional containing the generated Lewy body if successful, otherwise an empty optional.
     * @see NeuronGuestElement
     */
    default Optional<NeuronGuestElement> generateLewyBody(double probability) {
        return generateGuestElement(ModelElementType.LEWYBODY, probability);
    }
    
    /**
     * Generates a series of guest elements of a specified type with a given probability.
     * 
     * @param elementType the type of the guest element to generate.
     * @param probability the probability of generating the guest element.
     * @param number the number of elements to generate.
     * @see ModelElementType
     * @see NeuronGuestElement
     */
    default void generateGuestElements(ModelElementType elementType, double probability, int number) {
    	for (int i = 0; i <= number; i++) 
            generateGuestElement(elementType, probability);
    }

    /**
     * Retrieves a stream of mitochondria contained within the neuron.
     * 
     * @return a stream of contained mitochondria.
     * @see NeuronGuestElement
     */
    default Stream<NeuronGuestElement> getContainedMitochondriaAsStream() {
        return getNervousSystem().getGuestElementsAsStream()
                .filter(el -> el.getType().equals(ModelElementType.MITOCHONDRIA))
                .filter(el -> Objects.nonNull(el.getAttachedNeuron()) && Objects.equals(this, el.getAttachedNeuron()));
    }

    /**
     * Retrieves a stream of Lewy bodies contained within the neuron.
     * 
     * @return a stream of contained Lewy bodies.
     * @see NeuronGuestElement
     */
    default Stream<NeuronGuestElement> getContainedLewyBodiesAsStream() {
        return getNervousSystem().getGuestElementsAsStream()
                .filter(el -> el.getType().equals(ModelElementType.LEWYBODY))
                .filter(el -> Objects.nonNull(el.getAttachedNeuron()) && Objects.equals(this, el.getAttachedNeuron()));
    }

    /**
     * Retrieves all guest elements in the nervous system.
     * 
     * @return a list of guest elements.
     * @see NeuronGuestElement
     */
    default List<NeuronGuestElement> getGuestElements() {
        return getNervousSystem().getGuestElementsAsStream().toList();
    }

    /**
     * Retrieves all mitochondria contained within the neuron.
     * 
     * @return a list of contained mitochondria.
     * @see NeuronGuestElement
     */
    default List<NeuronGuestElement> getContainedMitochondria() {
        return getContainedMitochondriaAsStream().toList();
    }

    /**
     * Retrieves all Lewy bodies contained within the neuron.
     * 
     * @return a list of contained Lewy bodies.
     * @see NeuronGuestElement
     */
    default List<NeuronGuestElement> getContainedLewyBodies() {
        return getContainedLewyBodiesAsStream().toList();
    }

    /**
     * Retrieves all adjacent neurons in the network.
     * 
     * @return a list of adjacent neurons.
     * @see Neuron
     */
    default List<Neuron> getAdjacentNeurons() {
        return getAdjacentNeuronsAsStream().toList();
    }

    /**
     * Checks if the neuron is in a healthy state.
     * 
     * @return true if the neuron is healthy, false otherwise.
     * @see NeuronStateDescription
     */
    default boolean isHealthy() {
        return this.getState().getType().equals(NeuronStateDescription.HEALTHY);
    }

    /**
     * Checks if the neuron is degenerating.
     * 
     * @return true if the neuron is degenerating, false otherwise.
     * @see NeuronStateDescription
     */
    default boolean isDegenerating() {
        return this.getState().getType().equals(NeuronStateDescription.DEGENERATING);
    }

    /**
     * Checks if the neuron is dead.
     * 
     * @return true if the neuron is dead, false otherwise.
     * @see NeuronStateDescription
     */
    default boolean isDead() {
        return this.getState().getType().equals(NeuronStateDescription.DEAD);
    }

    /**
     * Checks if the neuron is myelinated.
     * 
     * @return true if the neuron is myelinated, false otherwise.
     * @see NeuronState
     */
    default boolean isMyelinated() {
        return this.getState().getMyelinLevel() > 0;
    }

    /**
     * Computes the ratio of Lewy bodies to mitochondria within the neuron.
     * 
     * @return the Lewy body to mitochondria ratio.
     * @see NeuronGuestElement
     */
    default double getLewyBodyMitochondriaRatio() {
        return getContainedLewyBodies().size() / (getContainedMitochondria().size() + 1.0);
    }
}



