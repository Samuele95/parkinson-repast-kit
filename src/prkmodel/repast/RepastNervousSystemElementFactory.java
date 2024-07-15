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

import java.util.Objects;

import prkmodel.types.Microglia;
import prkmodel.types.ModelElementType;
import prkmodel.types.NervousSystem;
import prkmodel.types.NervousSystemElement;
import prkmodel.types.Neuron;
import prkmodel.types.NeuronNetwork;
import prkmodel.utils.NervousSystemElementFactory;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.graph.Network;

/**
 * Factory class for creating instances of various nervous system elements in the Repast Simphony framework.
 * This class implements the singleton pattern using an enum.
 * 
 * @see prkmodel.utils.NervousSystemElementFactory
 * @see prkmodel.types.ModelElementType
 * @see repast.simphony.context.Context
 * @see repast.simphony.space.continuous.ContinuousSpace
 * @see repast.simphony.space.graph.Network
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public enum RepastNervousSystemElementFactory implements NervousSystemElementFactory {
    INSTANCE;
    
    private NervousSystem nsystem;
    private NervousSystemElement target;
    private NervousSystemElement parent;
    private Context<Object> context;
    private ContinuousSpace<Object> space;
    private Network<Object> network;
    private NeuronNetwork neuronNetwork;

    /**
     * {@inheritDoc}
     */
    @Override
    public NervousSystemElement create(ModelElementType type, double x, double y, double z) {
        if (Objects.isNull(context) || Objects.isNull(space))
            throw new NullPointerException("You need a context and a space to initialize a Repast Nervous System Element.");
        if (type.equals(ModelElementType.NEURON) && Objects.nonNull(neuronNetwork) && Objects.nonNull(network))
            return new RepastNeuron(neuronNetwork, x, y, z);
        if (type.equals(ModelElementType.ASTROCYTE))
            return new RepastAstrocyte(nsystem, x, y, z);
        if (type.equals(ModelElementType.MICROGLIA))
            return new RepastMicroglia(nsystem,  x, y, z);
        if (type.equals(ModelElementType.PROCYTOKINE) && (parent instanceof Microglia))
            return new RepastProInflammatoryCytokine<>(target, (Microglia) parent, context, space, 50, x, y, z);
        if (type.equals(ModelElementType.ANTICYTOKINE) && (target instanceof Neuron) && (parent instanceof Microglia))
            return new RepastAntiInflammatoryCytokine((Neuron) target, (Microglia) parent, 50, x, y, z);
        if ((type.equals(ModelElementType.LEWYBODY) || type.equals(ModelElementType.MITOCHONDRIA)) && (parent instanceof Neuron))
            return new RepastNeuronGuestElement((Neuron) parent, type, x, y, z);
        throw new IllegalArgumentException("Cannot instantiate the requested element. Some required parameter is missing or wrong.");
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
    public NeuronNetwork getNeuronNetwork() {
        return neuronNetwork;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NervousSystemElement getTarget() {
        return target;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NervousSystemElement getParent() {
        return parent;
    }

    /**
     * Returns the current Repast context.
     * 
     * @return the current Repast context
     */
    public Context<Object> getContext() {
        return context;
    }

    /**
     * Returns the current Repast continuous space.
     * 
     * @return the current Repast continuous space
     */
    public ContinuousSpace<Object> getSpace() {
        return space;
    }

    /**
     * Returns the current Repast network.
     * 
     * @return the current Repast network
     */
    public Network<Object> getNetwork() {
        return network;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NervousSystemElementFactory setNervousSystem(NervousSystem nsystem) {
        this.nsystem = Objects.requireNonNull(nsystem);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NervousSystemElementFactory setTarget(NervousSystemElement target) {
        this.target = Objects.requireNonNull(target);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NervousSystemElementFactory setParent(NervousSystemElement parent) {
        this.parent = Objects.requireNonNull(parent);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NervousSystemElementFactory setNeuronNetwork(NeuronNetwork neuronNetwork) {
        this.neuronNetwork = Objects.requireNonNull(neuronNetwork);
        return this;
    }

    /**
     * Sets the Repast context for this factory.
     * 
     * @param context the Repast context to set
     * @return this factory instance
     */
    public RepastNervousSystemElementFactory setContext(Context<Object> context) {
        this.context = Objects.requireNonNull(context);
        return this;
    }

    /**
     * Sets the Repast continuous space for this factory.
     * 
     * @param space the Repast continuous space to set
     * @return this factory instance
     */
    public RepastNervousSystemElementFactory setSpace(ContinuousSpace<Object> space) {
        this.space = Objects.requireNonNull(space);
        return this;
    }

    /**
     * Sets the Repast network for this factory.
     * 
     * @param network the Repast network to set
     * @return this factory instance
     */
    public RepastNervousSystemElementFactory setNetwork(Network<Object> network) {
        this.network = Objects.requireNonNull(network);
        return this;
    }
}

