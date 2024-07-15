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

package prkmodel.statistics;

import java.util.Objects;
import prkmodel.types.ModelElementType;
import prkmodel.types.NervousSystem;
import prkmodel.types.NervousSystemElement;
import prkmodel.types.Neuron;
import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;

/**
 * Default implementation of the {@link DataCollector} interface for collecting 
 * data and statistics from the nervous system simulation. This class gathers 
 * various statistics about the state and activity of the nervous system and its 
 * components.
 * 
 * @see DataCollector
 * @see prkmodel.types.NervousSystem
 * @see prkmodel.types.Neuron
 * @see prkmodel.repast.RepastParkinsonModelElement
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public final class DefaultDataCollector implements DataCollector {
    private NervousSystem nsystem;
    private Context<Object> context;
    private long healthyNeuronCount;
    private long degeneratingNeuronCount;
    private long deadNeuronCount;
    private double totalNeuronEnergy;
    private int mitochondriaTransferCount;
    private long lewyBodyCount;
    private long proInflammatoryCytokineCount;
    private long antiInflammatoryCytokineCount;
    private long totalMitochondriaCount;
	private long lewyBodyMitochondriaRate;

    /**
     * Constructs a new {@code DefaultDataCollector} with the specified nervous system 
     * and context.
     * 
     * @param nsystem the nervous system to collect data from.
     * @param context the simulation context.
     */
    public DefaultDataCollector(NervousSystem nsystem, Context<Object> context) {
        this.nsystem = Objects.requireNonNull(nsystem);
        this.context = Objects.requireNonNull(context);
        
        context.add(this);
    }

    /**
     * Gathers statistics about the current state of the nervous system and its components.
     */
    @ScheduledMethod(start = 1, interval = 10)
    public void gatherStatistics() {
        // NEURONS STATISTICS
    	degeneratingNeuronCount = nsystem.getNeurons().stream().filter(Neuron::isDegenerating).count();
        deadNeuronCount = nsystem.getNeurons().stream().filter(Neuron::isDead).count();
        healthyNeuronCount = nsystem.getNeurons().stream().count() - degeneratingNeuronCount - deadNeuronCount;
        
        // LEWY BODY - MITOCHONDRIA STATISTICS
        totalNeuronEnergy = nsystem.getNeurons().stream().mapToDouble(n -> n.getState().getDopamineLevel()).sum();
        totalMitochondriaCount = nsystem.getGuestElementsAsStream().filter(el -> !el.isDeleted() && el.getType().equals(ModelElementType.MITOCHONDRIA)).count();
        lewyBodyCount = nsystem.getGuestElementsAsStream().filter(el -> !el.isDeleted() && el.getType().equals(ModelElementType.LEWYBODY)).count();
        lewyBodyMitochondriaRate = lewyBodyCount / totalMitochondriaCount;
       
        // CYTOKINE STATISTICS
        proInflammatoryCytokineCount = context.getObjectsAsStream(NervousSystemElement.class)
                .map(NervousSystemElement.class::cast)
                .filter(mel -> mel.getType().equals(ModelElementType.PROCYTOKINE))
                .count();
        antiInflammatoryCytokineCount = context.getObjectsAsStream(NervousSystemElement.class)
                .map(NervousSystemElement.class::cast)
                .filter(mel -> mel.getType().equals(ModelElementType.ANTICYTOKINE))
                .count();
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
    public Context<Object> getContext() {
        return context;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getHealthyNeuronCount() {
        return healthyNeuronCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getDegeneratingNeuronCount() {
        return degeneratingNeuronCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getDeadNeuronCount() {
        return deadNeuronCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getTotalNeuronEnergy() {
        return totalNeuronEnergy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMitochondriaTransferCount() {
        return mitochondriaTransferCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getLewyBodyCount() {
        return lewyBodyCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getProInflammatoryCytokineCount() {
        return proInflammatoryCytokineCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getAntiInflammatoryCytokineCount() {
        return antiInflammatoryCytokineCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getTotalMitochondriaCount() {
        return totalMitochondriaCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public long getLewyBodyMitochondriaRate() {
		return lewyBodyMitochondriaRate;
	}
}

