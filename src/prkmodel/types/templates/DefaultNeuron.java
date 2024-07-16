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

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import prkmodel.types.ModelElementType;
import prkmodel.types.Neuron;
import prkmodel.types.NeuronGuestElement;
import prkmodel.types.NeuronNetwork;
import prkmodel.types.NeuronState;
import prkmodel.types.NeuronStateDescription;

/**
 * Default implementation of the {@link Neuron} interface.
 * Represents a neuron in the nervous system with default behaviors and properties.
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
 * @see Neuron
 * @see NervousSystemElement
 * @see NeuronState
 * @see NeuronNetwork
 * @see ModelElementType
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public class DefaultNeuron extends AbstractNervousSystemElement implements Neuron {

    private final NeuronState state;
    private final NeuronNetwork network;

    /**
     * Constructs a new {@code DefaultNeuron} with the specified network, type, and position.
     * 
     * @param network the neuron network this neuron belongs to
     * @param type the type of the neuron
     * @param x the X coordinate of the neuron
     * @param y the Y coordinate of the neuron
     * @param z the Z coordinate of the neuron
     * @throws NullPointerException if the network or type is null
     * @throws IllegalArgumentException if the coordinates are invalid
     */
    public DefaultNeuron(NeuronNetwork network, ModelElementType type, double x, double y, double z) {
        super(Objects.requireNonNull(network).getNervousSystem(), type, x, y, z, false);
        this.network = network;
        this.state = new DefaultNeuronState();
        this.generateGuestElements(ModelElementType.MITOCHONDRIA, 2.0, getNervousSystem().getMitochondriaPerNeuron());
    }

    /**
     * Represents the state of a neuron in the nervous system.
     */
    private class DefaultNeuronState implements NeuronState {
        private NeuronStateDescription description;
        private double dopamineLevel;
        private int myelin;

        /**
         * Constructs a new {@code DefaultNeuronState} with default values.
         */
        DefaultNeuronState() {
            this.description = NeuronStateDescription.HEALTHY;
            this.dopamineLevel = 100;
            this.myelin = 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void updateDopamine(double t) {
            dopamineLevel = Math.max(dopamineLevel += t, 0.0);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public double getDopamineLevel() {
            return dopamineLevel;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getMyelinLevel() {
            return myelin;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public NeuronStateDescription getType() {
            return description;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void updateMyelin(int t) {
            this.myelin += t;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void increaseDopamineProduction() {
            if (isDegenerating()) 
                dopamineLevel += getNervousSystem().getPNRG().nextDouble(0, (dopamineLevel / 2)); // arbitrary increase
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setType(NeuronStateDescription type) {
            this.description = Objects.requireNonNull(type);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NeuronState getState() {
        return state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NeuronNetwork getNetwork() {
        return network;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDopamineLevelLow() {
        return state.getDopamineLevel() < getNervousSystem().getMitoTransferThreshold();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasExcessMitochondria() {
        if (isDopamineLevelLow()) 
            return false;
        double requiredMitochondria = (100.0 - state.getDopamineLevel()) / 10.0; // Example function
        return getContainedMitochondria().size() > requiredMitochondria;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean shouldDegenerate() {
        double degenerationProbability = 1.25 * getLewyBodyMitochondriaRatio();
        return getNervousSystem().pickProbability() < degenerationProbability;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<NeuronGuestElement> generateGuestElement(ModelElementType elementType, double probability) {
        if (getNetwork().getNervousSystem().pickProbability() < probability && (elementType.equals(ModelElementType.LEWYBODY) || elementType.equals(ModelElementType.MITOCHONDRIA)))
            return Optional.of((NeuronGuestElement) getNervousSystem().getFactory().setParent(this).create(elementType, getPosX(), getPosY(), getPosZ()));
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteGuestElement(NeuronGuestElement element) {
        getNervousSystem().getGuestElementsAsStream()
            .filter(el -> el.equals(Objects.requireNonNull(element)) && el.getAttachedNeuron().equals(this))
            .findFirst()
            .ifPresent(NeuronGuestElement::delete);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteRandomGuestElement(ModelElementType type) {
        Optional<NeuronGuestElement> randomElement;
        Stream<NeuronGuestElement> sourceStream = (Objects.isNull(type))
            ? getNervousSystem().getGuestElementsAsStream()
            : getNervousSystem().getGuestElementsAsStream().filter(el -> el.getType().equals(type));
        return ((randomElement = sourceStream.findAny()).filter(el -> el.isAttached() && el.getAttachedNeuron().equals(this)).isPresent())
            ? randomElement.get().delete()
            : false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMitochondria(Neuron neuronNeedingMitochondria) {
        if (deleteRandomGuestElement(ModelElementType.MITOCHONDRIA)) {
            neuronNeedingMitochondria.generateGuestElement(ModelElementType.MITOCHONDRIA, 2.0);
            System.out.println("Mitochondria transferred from " + this + " to " + neuronNeedingMitochondria);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void requestMitochondria() {
        getNervousSystem().getAstrocytesAsStream().findAny().ifPresent(ac -> ac.transferMitochondria(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void work() {
        if (isDead() || isDeleted()) 
            return;
        if (!isDegenerating() && shouldDegenerate()) 
            state.setType(NeuronStateDescription.DEGENERATING);
        if (isDegenerating() && !shouldDegenerate()) 
            state.setType(NeuronStateDescription.HEALTHY);

        double scaledRatio = 10 * getLewyBodyMitochondriaRatio();
        state.updateDopamine((!isDegenerating()) ? scaledRatio : -scaledRatio);

        generateMitochondria(0.5);
        generateLewyBody((isDegenerating()) ? 0.6 : 0.1);

        if (state.getDopamineLevel() < getNervousSystem().getMitoTransferThreshold()) 
            requestMitochondria();
        if (isDegenerating() && state.getDopamineLevel() <= 0) {
            System.out.println("Neuron dying: " + this);
            delete();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete() {
        state.setType(NeuronStateDescription.DEAD);
        getNervousSystem().getGuestElementsAsStream().forEach(NeuronGuestElement::delete);
        network.associatedEdges(this).forEach(edge -> network.deleteEdge(edge));
        return super.delete();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<Neuron> getAdjacentNeuronsAsStream() {
        return network.associatedEdgesAsStream(this).map(ed -> ((ed.first().equals(this)) ? ed.first() : ed.second()));
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
