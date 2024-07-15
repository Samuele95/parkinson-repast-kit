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

import prkmodel.types.NervousSystem;
import prkmodel.types.templates.DefaultAstrocyte;
import repast.simphony.engine.schedule.ScheduledMethod;

/**
 * Extends the functionality of {@link DefaultAstrocyte} to integrate with the Repast Simphony framework.
 * Represents an astrocyte in the simulation that provides support to neurons.
 * 
 * @see prkmodel.types.templates.DefaultAstrocyte
 * @see prkmodel.types.NervousSystem
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public final class RepastAstrocyte extends DefaultAstrocyte {

    /**
     * Constructs a new {@code RepastAstrocyte} with the specified nervous system and position.
     * 
     * @param nsystem the nervous system this astrocyte belongs to
     * @param x the X coordinate of the astrocyte
     * @param y the Y coordinate of the astrocyte
     * @param z the Z coordinate of the astrocyte
     * @throws NullPointerException if the nervous system is null
     * @throws IllegalArgumentException if the coordinates are invalid
     */
    public RepastAstrocyte(NervousSystem nsystem, double x, double y, double z) {
        super(nsystem, x, y, z);
        RepastNervousSystemElementFactory.INSTANCE.getContext().add(this);
        RepastNervousSystemElementFactory.INSTANCE.getSpace().moveTo(this, getPosX(), getPosY(), getPosZ());
    }

    /**
     * Activates the astrocyte, providing support to neurons.
     * This method is scheduled to run at each simulation step.
     * 
     * {@inheritDoc}
     */
    @Override
    @ScheduledMethod(start = 1, interval = 1)
    public void activate() {
        super.activate();
    }
}

