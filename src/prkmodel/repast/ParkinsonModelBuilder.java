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

import prkmodel.repast.RepastNervousSystem.RepastNervousSystemBuilder;
import prkmodel.statistics.DataCollector;
import prkmodel.statistics.DefaultDataCollector;
import prkmodel.types.NervousSystem;
import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.graph.NetworkBuilder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.graph.Network;

/**
 * Builder class for constructing the Parkinson's model in the Repast simulation environment.
 * This class initializes the simulation context, space, network, and components of the nervous system.
 * 
 * @see repast.simphony.dataLoader.ContextBuilder
 * @see prkmodel.types.NervousSystem
 * @see prkmodel.statistics.DataCollector
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 * 
 */
public class ParkinsonModelBuilder implements ContextBuilder<Object> {

    private NervousSystem nsystem;
    private Context<Object> context;
    private ContinuousSpace<Object> space;
    private Network<Object> network;
    private DataCollector dataCollector;

    /**
     * Builds and initializes the context for the Parkinson's model simulation.
     * 
     * @param context the simulation context.
     * @return the initialized context.
     */
    @Override
    public Context<Object> build(Context<Object> context) {
        setContext(context);

        // Create space for visualization in Repast Simphony
        setSpace(ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null)
                .createContinuousSpace(
                        "Space",
                        context,
                        new RandomCartesianAdder<>(),
                        new repast.simphony.space.continuous.WrapAroundBorders(),
                        50, 50, 50
                ));
        // Create network for visualization in Repast Simphony
        setNetwork(new NetworkBuilder<Object>("Neural Network", context, true).buildNetwork());
 
        // Init Nervous System with the Repast Simphony simulation hyperparameters
        Parameters params = RunEnvironment.getInstance().getParameters();
        setNervousSystem(new RepastNervousSystemBuilder()
        		.setContext(context)
        		.setSpace(space)
        		.setRepastNetwork(network)
        		.setDim(50)
        		.setMitoTransferThreshold((Double) params.getValue("MITO_TRANSFER_THRESHOLD"))
        		.setLewyBodyDegenerationThreshold((Integer) params.getValue("LEWYBODY_DEGENERATION_THRESHOLD"))
        		.setBaseDegenerationProbability((Double) params.getValue("BASE_DEGENERATION_PROBABILITY"))
        		.setMitochondriaPerNeuron((Integer) params.getValue("MITOCHONDRIA_PER_NEURON"))
        		.build()
        		.addNeurons((Integer) params.getValue("INITIAL_NEURONS"))
        		.addAstrocytes((Integer) params.getValue("INITIAL_ASTROCYTES"))
        		.addMicroglia((Integer) params.getValue("INITIAL_MICROGLIA")
        		));

        // Setup data collector in order to gather statistics
        setDataCollector(new DefaultDataCollector(nsystem, context));

        return context;
    }

    /**
     * Retrieves the nervous system.
     * 
     * @return the nervous system.
     */
    public NervousSystem getNervousSystem() {
        return nsystem;
    }

    /**
     * Retrieves the simulation context.
     * 
     * @return the context.
     */
    public Context<Object> getContext() {
        return context;
    }

    /**
     * Retrieves the continuous space.
     * 
     * @return the continuous space.
     */
    public ContinuousSpace<Object> getSpace() {
        return space;
    }

    /**
     * Retrieves the network.
     * 
     * @return the network.
     */
    public Network<Object> getNetwork() {
        return network;
    }

    /**
     * Retrieves the data collector.
     * 
     * @return the data collector.
     */
    public DataCollector getDataCollector() {
        return dataCollector;
    }

    /**
     * Sets the nervous system.
     * 
     * @param nsystem the nervous system to set.
     */
    public void setNervousSystem(NervousSystem nsystem) {
        this.nsystem = nsystem;
    }

    /**
     * Sets the simulation context.
     * 
     * @param context the context to set.
     */
    public void setContext(Context<Object> context) {
        this.context = context;
    }

    /**
     * Sets the continuous space.
     * 
     * @param space the space to set.
     */
    public void setSpace(ContinuousSpace<Object> space) {
        this.space = space;
    }

    /**
     * Sets the network.
     * 
     * @param network the network to set.
     */
    public void setNetwork(Network<Object> network) {
        this.network = network;
    }

    /**
     * Sets the data collector.
     * 
     * @param dataCollector the data collector to set.
     */
    public void setDataCollector(DataCollector dataCollector) {
        this.dataCollector = dataCollector;
    }
}


