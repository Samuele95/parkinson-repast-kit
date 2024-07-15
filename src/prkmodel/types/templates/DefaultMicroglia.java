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

import java.util.Optional;
import prkmodel.types.Microglia;
import prkmodel.types.ModelElementType;
import prkmodel.types.NervousSystem;
import prkmodel.types.NervousSystemElement;
import prkmodel.types.Neuron;

/**
 * Default implementation of the {@link Microglia} interface.
 * Represents a microglia in the nervous system with default behaviors and properties.
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
 * @see Microglia
 * @see NervousSystemElement
 * @see NervousSystem
 * @see ModelElementType
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public class DefaultMicroglia extends AbstractNervousSystemElement implements Microglia {

    /**
     * Constructs a new {@code DefaultMicroglia} with the specified nervous system and position.
     * 
     * @param nsystem the nervous system this microglia belongs to
     * @param x the X coordinate of the microglia
     * @param y the Y coordinate of the microglia
     * @param z the Z coordinate of the microglia
     * @throws NullPointerException if the nervous system is null
     * @throws IllegalArgumentException if the coordinates are invalid
     */
    public DefaultMicroglia(NervousSystem nsystem, double x, double y, double z) {
        super(nsystem, ModelElementType.MICROGLIA, x, y, z, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void produceCytokines() {
        double probability = getNervousSystem().getPNRG().nextDouble(0, 1);
        //if (probability < 0.1) {
        //    return;
        if (probability < 0.5) {
            produceCytokine(false);
        } else if (detectLewyBodies()) {
            produceCytokine(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Produces a cytokine based on the specified type (pro-inflammatory or anti-inflammatory).
     * 
     * @param isProInflammatory whether the cytokine is pro-inflammatory
     */
    private void produceCytokine(boolean isProInflammatory) {
        Optional<? extends NervousSystemElement> target = selectRandomTarget(isProInflammatory);
        if (target.isPresent()) {
            NervousSystemElement cytokine = (isProInflammatory)
                    ? getNervousSystem().getFactory().setTarget(target.get()).setParent(this).create(ModelElementType.PROCYTOKINE, getPosX(), getPosY(), getPosZ())
                    : getNervousSystem().getFactory().setTarget((Neuron) target.get()).setParent(this).create(ModelElementType.ANTICYTOKINE, getPosX(), getPosY(), getPosZ());
            System.out.println("Cytokine created: " + cytokine + " with target: " + target.get());
        }
    }

    /**
     * Detects the presence of Lewy bodies in the nervous system.
     * 
     * @return true if Lewy bodies are detected, false otherwise
     */
    private boolean detectLewyBodies() {
        return getNervousSystem().getAliveNeurons().stream().anyMatch(n -> !n.getContainedLewyBodies().isEmpty());
    }

    /**
     * Selects a random target based on the specified type (pro-inflammatory or anti-inflammatory).
     * 
     * @param isProInflammatory whether the target selection is for a pro-inflammatory cytokine
     * @return an {@link Optional} containing the selected target, if present
     */
    private Optional<? extends NervousSystemElement> selectRandomTarget(boolean isProInflammatory) {
        return (isProInflammatory) ? getNervousSystem().getRandomElement() : getNervousSystem().getRandomNeuron();
    }
}

