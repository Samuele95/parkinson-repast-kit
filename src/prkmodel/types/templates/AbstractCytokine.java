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

import java.util.Objects;
import prkmodel.types.Cytokine;
import prkmodel.types.Microglia;
import prkmodel.types.ModelElementType;
import prkmodel.types.NervousSystem;
import prkmodel.types.NervousSystemElement;

/**
 * Abstract base class for cytokines in the nervous system.
 * Represents a cytokine with default behaviors and properties.
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
 * @param <T> the type of {@link NervousSystemElement} that this cytokine targets
 * 
 * @see Cytokine
 * @see NervousSystemElement
 * @see NervousSystem
 * @see ModelElementType
 * @see Microglia
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public abstract class AbstractCytokine<T extends NervousSystemElement> extends AbstractMoveableNervousSystemElement implements Cytokine<T> {
    
    private final T target;
    private final Microglia parentMicroglia;
    private int lifespan;
    private boolean attached;

    /**
     * Constructs a new {@code AbstractCytokine} with the specified nervous system, type, target, parent microglia, lifespan, and position.
     * 
     * @param nsystem the nervous system this cytokine belongs to
     * @param type the type of the cytokine
     * @param target the target of the cytokine
     * @param parentMicroglia the parent microglia that produced this cytokine
     * @param lifespan the lifespan of the cytokine
     * @param x the X coordinate of the cytokine
     * @param y the Y coordinate of the cytokine
     * @param z the Z coordinate of the cytokine
     * @throws NullPointerException if the nervous system, type, target, or parent microglia is null
     * @throws IllegalArgumentException if the coordinates are invalid or the target is not present in the nervous system
     */
    public AbstractCytokine(NervousSystem nsystem, ModelElementType type, T target, Microglia parentMicroglia, int lifespan, double x, double y, double z) {
        super(nsystem, type, x, y, z, true);
        if (!getNervousSystem().getElements().contains(Objects.requireNonNull(target)) && !getNervousSystem().getNeurons().contains(Objects.requireNonNull(target))) {
            throw new IllegalArgumentException("Target must be present in nervous system");
        }
        this.target = Objects.requireNonNull(target);
        this.parentMicroglia = Objects.requireNonNull(parentMicroglia);
        this.lifespan = lifespan;
        this.attached = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getTarget() {
        return target;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Microglia getParentMicroglia() {
        return parentMicroglia;
    }

    /**
     * Gets the lifespan of the cytokine.
     * 
     * @return the lifespan of the cytokine
     */
    public int getLifespan() {
        return lifespan;
    }

    /**
     * Updates the lifespan of the cytokine by the specified amount.
     * 
     * @param update the amount to update the lifespan by
     * @return the updated lifespan
     */
    public int updateLifespan(int update) {
        return lifespan += update;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAttachedToTarget() {
        return attached;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void step() {
        if ((updateLifespan(-1) <= 0) || !getNervousSystem().getElements().contains(getTarget())) {
            delete();
        } else if (!isAttachedToTarget()) {
            moveTowardsElement(target, 0.5);
            attachToTargets();
        } else {
            interactWithTarget();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attachToTargets() {
        if (this.computeDistanceFrom(target) <= 1.0) 
            attached = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new StringBuilder(super.toString()).append(System.lineSeparator())
                .append(String.format("%4s", String.format("Target: %s", target))).toString();
    }
}

