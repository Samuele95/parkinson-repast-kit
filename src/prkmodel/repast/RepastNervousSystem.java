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

import prkmodel.types.templates.DefaultNervousSystem;
import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.graph.Network;

/**
 * Extension of the {@link DefaultNervousSystem} class tailored for the Repast Simphony framework.
 * It incorporates additional Repast-specific features such as scheduled methods and integration with
 * the Repast context, space, and network.
 * 
 * @see prkmodel.types.templates.DefaultNervousSystem
 * @see repast.simphony.context.Context
 * @see repast.simphony.space.continuous.ContinuousSpace
 * @see repast.simphony.space.graph.Network
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public final class RepastNervousSystem extends DefaultNervousSystem {
    
    /**
     * Constructs a new {@code RepastNervousSystem} with the specified dimensions and parameters.
     * 
     * @param xdim the X dimension of the nervous system
     * @param ydim the Y dimension of the nervous system
     * @param zdim the Z dimension of the nervous system
     * @param mitoTransferThreshold the mitochondria transfer threshold
     * @param lewyBodyDegenerationThreshold the lewy body degeneration threshold
     * @param baseDegenerationProbability the base degeneration probability
     * @param mitochondriaPerNeuron the number of mitochondria per neuron
     * @throws IllegalArgumentException if any dimension is invalid
     */
    public RepastNervousSystem(int xdim, int ydim, int zdim, double mitoTransferThreshold, int lewyBodyDegenerationThreshold, double baseDegenerationProbability, int mitochondriaPerNeuron) {
        super(xdim, ydim, zdim, mitoTransferThreshold, lewyBodyDegenerationThreshold, baseDegenerationProbability, mitochondriaPerNeuron);
        setNeuronNetwork(new RepastNeuronNetwork(this));
        RepastNervousSystemElementFactory.INSTANCE.getContext().add(this);
    }
    
    /**
     * Builder class for {@link RepastNervousSystem}.
     */
    public static class RepastNervousSystemBuilder extends DefaultNervousSystemBuilder {
        
        /**
         * {@inheritDoc}
         */
        @Override
        public RepastNervousSystem build() {
            return new RepastNervousSystem(
                    getDim(), getDim(), getDim(), 
                    getMitoTransferThreshold(), 
                    getLewyBodyDegenerationThreshold(),
                    getBaseDegenerationProbability(), 
                    getMitochondriaPerNeuron());
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public RepastNervousSystemBuilder reset() {
            return new RepastNervousSystemBuilder();
        }
        
        /**
         * Sets the context for the Repast nervous system.
         * 
         * @param context the Repast context
         * @return this builder
         */
        public RepastNervousSystemBuilder setContext(Context<Object> context) {
            RepastNervousSystemElementFactory.INSTANCE.setContext(context);
            return this;
        }

        /**
         * Sets the space for the Repast nervous system.
         * 
         * @param space the Repast continuous space
         * @return this builder
         */
        public RepastNervousSystemBuilder setSpace(ContinuousSpace<Object> space) {
            RepastNervousSystemElementFactory.INSTANCE.setSpace(space);
            return this;
        }
        
        /**
         * Sets the network for the Repast nervous system.
         * 
         * @param repastNetwork the Repast network
         * @return this builder
         */
        public RepastNervousSystemBuilder setRepastNetwork(Network<Object> repastNetwork) {
            RepastNervousSystemElementFactory.INSTANCE.setNetwork(repastNetwork);
            return this;
        }
    }
    
    /**
     * Scheduled method to perform regular steps in the simulation.
     * This method is called by the Repast Simphony scheduler at regular intervals.
     * If there are no neurons left, the simulation run will end.
     */
    @ScheduledMethod(start = 1, interval = 100, priority=ScheduleParameters.FIRST_PRIORITY)
    public void step() { 
        if (getNeurons().size() == 0) {
            RunEnvironment.getInstance().endRun();
            return;
        }
        super.step(); 
    }
    
    /**
     * {@inheritDoc}
     * 
     * Additionally removes the mitochondria of neurons with no adjacent neurons from the Repast context.
     */
    @Override
    public RepastNervousSystem filterOutNeurons() {
        getNeurons().stream()
            .filter(n -> n.getAdjacentNeurons().isEmpty())
            .flatMap(n -> n.getContainedMitochondriaAsStream())
            .forEach(el -> RepastNervousSystemElementFactory.INSTANCE.getContext().remove(el));
        super.filterOutNeurons();
        return this;
    }
}

