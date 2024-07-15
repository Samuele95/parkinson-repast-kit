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
import prkmodel.types.Microglia;
import prkmodel.types.ModelElementType;
import prkmodel.types.NervousSystem;
import prkmodel.types.NervousSystemElement;
import prkmodel.types.Neuron;

/**
 * Implementation of a pro-inflammatory cytokine in the nervous system.
 * Represents a cytokine that targets various elements of the nervous system to induce inflammatory responses.
 * 
 * <p>This class extends {@link AbstractCytokine} and provides specific behavior for interacting with different types of targets.
 * 
 * <p>To extend this class:
 * <ul>
 *   <li>Ensure the constructor properly initializes inherited fields.
 *   <li>Override methods to provide specific behavior if needed.
 *   <li>Document the purpose of any new methods or overridden methods.
 *   <li>Use {@code super} to call the superclass method when overriding to extend or modify its behavior.
 * </ul>
 * </p>
 * 
 * @see AbstractCytokine
 * @see Cytokine
 * @see NervousSystemElement
 * @see NervousSystem
 * @see ModelElementType
 * @see Microglia
 * @see Neuron
 * @see Astrocyte
 * 
 * @param <T> the type of {@link NervousSystemElement} that this cytokine targets
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public class DefaultProInflammatoryCytokine<T extends NervousSystemElement> extends AbstractCytokine<T> {

    /**
     * Constructs a new {@code DefaultProInflammatoryCytokine} with the specified parameters.
     * 
     * @param nsystem the nervous system this cytokine belongs to
     * @param target the target of the cytokine
     * @param parentMicroglia the parent microglia that produced this cytokine
     * @param lifespan the lifespan of the cytokine
     * @param x the X coordinate of the cytokine
     * @param y the Y coordinate of the cytokine
     * @param z the Z coordinate of the cytokine
     * @throws NullPointerException if the nervous system, target, or parent microglia is null
     * @throws IllegalArgumentException if the coordinates are invalid
     */
    public DefaultProInflammatoryCytokine(NervousSystem nsystem, T target, Microglia parentMicroglia, int lifespan, double x, double y, double z) {
        super(nsystem, ModelElementType.PROCYTOKINE, target, parentMicroglia, lifespan, x, y, z);
    }

    /**
     * Interacts with the target to induce an inflammatory response.
     * 
     * <p>If the target is a neuron, it will either reduce myelin or delete a mitochondrion.
     * If the target is an astrocyte, it will be deactivated.
     * If the target is another microglia, it will produce more cytokines.
     * 
     * {@inheritDoc}
     */
    @Override
    public void interactWithTarget() {
        if (getTarget() instanceof Neuron && getNervousSystem().getPNRG().nextDouble(0, 1) < 0.4) {
            Neuron target = (Neuron) getTarget();
            if (target.isMyelinated()) {
                target.getState().updateMyelin(-getNervousSystem().getPNRG().nextInt(1, 5));
                return;
            }
            target.deleteRandomGuestElement(ModelElementType.MITOCHONDRIA);
        } else if (getTarget() instanceof Astrocyte) {
            ((Astrocyte) getTarget()).deactivate();
        } else if (getTarget() instanceof Microglia) {
            ((Microglia) getTarget()).produceCytokines();
        }
    }
}

