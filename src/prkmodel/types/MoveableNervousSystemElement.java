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

package prkmodel.types;

/**
 * Interface representing a moveable element of the nervous system.
 * It extends the {@link NervousSystemElement} interface to include movement-related behaviors.
 * 
 * @see NervousSystemElement
 * @see NervousSystem
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public interface MoveableNervousSystemElement extends NervousSystemElement {
    
    /**
     * Moves this element to the specified position.
     * 
     * @param x the X coordinate to move to.
     * @param y the Y coordinate to move to.
     * @param z the Z coordinate to move to.
     * @throws IllegalArgumentException if the coordinates are invalid.
     */
    void moveToPosition(double x, double y, double z);

    /**
     * Moves this element in a predefined manner.
     */
    void move();

    /**
     * Moves this element towards the specified target element by a given step size.
     * 
     * @param target the {@link NervousSystemElement} to move towards.
     * @param stepSize the step size for the movement.
     * @throws NullPointerException if the target element is null.
     * @throws IllegalArgumentException if the step size is invalid.
     */
    void moveTowardsElement(NervousSystemElement target, double stepSize);
}

