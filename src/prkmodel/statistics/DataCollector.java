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

import prkmodel.types.NervousSystem;
import repast.simphony.context.Context;

/**
 * Interface for collecting and retrieving various statistics from the nervous system simulation.
 * 
 * <p>Implementations of this interface are responsible for gathering data about the state and 
 * behavior of the nervous system and providing methods to access this data.
 * 
 * @see prkmodel.types.NervousSystem
 * @see repast.simphony.context.Context
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public interface DataCollector {

    /**
     * Returns the nervous system being monitored.
     * 
     * @return the nervous system
     */
    NervousSystem getNervousSystem();

    /**
     * Returns the simulation context.
     * 
     * @return the context
     */
    Context<Object> getContext();

    /**
     * Returns the count of healthy neurons.
     * 
     * @return the number of healthy neurons
     */
    long getHealthyNeuronCount();

    /**
     * Returns the count of degenerating neurons.
     * 
     * @return the number of degenerating neurons
     */
    long getDegeneratingNeuronCount();

    /**
     * Returns the count of dead neurons.
     * 
     * @return the number of dead neurons
     */
    long getDeadNeuronCount();
    
    /**
     * Returns the total energy level of all neurons.
     * 
     * @return the total neuron energy
     */
    double getTotalNeuronEnergy();

    /**
     * Returns the count of mitochondria transfers that have occurred.
     * 
     * @return the number of mitochondria transfers
     */
    int getMitochondriaTransferCount();

    /**
     * Returns the count of Lewy bodies present in the nervous system.
     * 
     * @return the number of Lewy bodies
     */
    long getLewyBodyCount();

    /**
     * Returns the count of pro-inflammatory cytokines present in the nervous system.
     * 
     * @return the number of pro-inflammatory cytokines
     */
    long getProInflammatoryCytokineCount();

    /**
     * Returns the count of anti-inflammatory cytokines present in the nervous system.
     * 
     * @return the number of anti-inflammatory cytokines
     */
    long getAntiInflammatoryCytokineCount();

    /**
     * Returns the total count of mitochondria present in the nervous system.
     * 
     * @return the total number of mitochondria
     */
    long getTotalMitochondriaCount();

    /**
     * Returns the ratio of Lewy bodies to mitochondria in the nervous system.
     * 
     * @return the ratio of Lewy bodies to mitochondria
     */
    long getLewyBodyMitochondriaRate();
}


