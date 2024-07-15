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

import prkmodel.types.Microglia;
import prkmodel.types.Neuron;
import prkmodel.types.templates.DefaultAntiInflammatoryCytokine;
import repast.simphony.engine.schedule.ScheduledMethod;

/**
 * Extends the functionality of {@link DefaultAntiInflammatoryCytokine} to integrate with the Repast Simphony framework.
 * Represents an anti-inflammatory cytokine in the simulation that interacts with neurons.
 * 
 * @see prkmodel.types.templates.DefaultAntiInflammatoryCytokine
 * @see prkmodel.types.Neuron
 * @see prkmodel.types.Microglia
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public class RepastAntiInflammatoryCytokine extends DefaultAntiInflammatoryCytokine {

    /**
     * Constructs a new {@code RepastAntiInflammatoryCytokine} with the specified target neuron, parent microglia,
     * lifespan, and position.
     * 
     * @param target the target neuron for this cytokine
     * @param parentMicroglia the parent microglia that produced this cytokine
     * @param lifespan the lifespan of this cytokine
     * @param x the X coordinate of the cytokine
     * @param y the Y coordinate of the cytokine
     * @param z the Z coordinate of the cytokine
     * @throws NullPointerException if the target or parent microglia is null
     * @throws IllegalArgumentException if the coordinates are invalid
     */
    public RepastAntiInflammatoryCytokine(Neuron target, Microglia parentMicroglia, int lifespan, double x, double y, double z) {
        super(target.getNervousSystem(), target, parentMicroglia, lifespan, x, y, z);
        RepastNervousSystemElementFactory.INSTANCE.getContext().add(this);
        RepastNervousSystemElementFactory.INSTANCE.getSpace().moveTo(this, getPosX(), getPosY(), getPosZ());
    }

    /**
     * Schedules the cytokine to perform its actions at each simulation step.
     * This method is scheduled to run at each simulation step.
     * 
     * {@inheritDoc}
     */
    @ScheduledMethod(start = 1, interval = 1)
    public void step() {
        super.step();
    }

    /**
     * {@inheritDoc}
     * 
     * Updates the position of the cytokine and moves it within the Repast Simphony space.
     */
    @Override
    public void moveToPosition(double x, double y, double z) {
        super.moveToPosition(x, y, z);
        RepastNervousSystemElementFactory.INSTANCE.getSpace().moveTo(this, getPosX(), getPosY(), getPosZ());
    }

    /**
     * {@inheritDoc}
     * 
     * Deletes the cytokine from the Repast Simphony context.
     */
    @Override
    public boolean delete() {
        boolean result = super.delete();
        RepastNervousSystemElementFactory.INSTANCE.getContext().remove(this);
        return result;
    }
}

