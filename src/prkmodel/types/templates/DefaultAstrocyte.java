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

import prkmodel.types.Astrocyte;
import prkmodel.types.ModelElementType;
import prkmodel.types.NervousSystem;
import prkmodel.types.Neuron;

/**
 * Default implementation of the {@link Astrocyte} interface.
 * Represents an astrocyte in the nervous system with default behaviors and properties.
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
 * @see Astrocyte
 * @see NervousSystemElement
 * @see Neuron
 * @see ModelElementType
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public class DefaultAstrocyte extends AbstractNervousSystemElement implements Astrocyte {

    private boolean active;
    private int mitochondriaTransferCount;

    /**
     * Constructs a new {@code DefaultAstrocyte} with the specified nervous system and position.
     * 
     * @param nsystem the nervous system this astrocyte belongs to
     * @param x the X coordinate of the astrocyte
     * @param y the Y coordinate of the astrocyte
     * @param z the Z coordinate of the astrocyte
     * @throws NullPointerException if the nervous system is null
     * @throws IllegalArgumentException if the coordinates are invalid
     */
    public DefaultAstrocyte(NervousSystem nsystem, double x, double y, double z) {
        super(nsystem, ModelElementType.ASTROCYTE, x, y, z, false);
        this.active = true;
        this.mitochondriaTransferCount = 0;
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
    public void activate() {
        if (isActive())
            supportNeurons();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deactivate() {
        active = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActive() {
        return active;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void transferMitochondria(Neuron neuronNeedingMitochondria) {
        neuronNeedingMitochondria.getAdjacentNeuronsAsStream()
            .filter(Neuron::hasExcessMitochondria)
            .findAny()
            .ifPresent(neighborNeuron -> {
                neighborNeuron.sendMitochondria(neuronNeedingMitochondria);
                mitochondriaTransferCount++;
            });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void supportNeurons() {
        getNervousSystem().getRandomNeuron().ifPresent(n -> {
            n.getState().updateMyelin(getNervousSystem().getPNRG().nextInt(1, 5));
            n.getState().updateDopamine(getNervousSystem().getPNRG().nextInt(1, 5));
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return super.toString();
    }
}

