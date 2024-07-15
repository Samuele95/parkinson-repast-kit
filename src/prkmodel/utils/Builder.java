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

package prkmodel.utils;

/**
 * Interface representing a builder pattern for creating instances of type T.
 * It provides methods to build an instance and reset the builder to its initial state.
 * 
 * <p>This interface follows the builder pattern, as described by Joshua Bloch in 
 * <a href="https://blogs.oracle.com/javamagazine/post/exploring-joshua-blochs-builder-design-pattern-in-java">
 * "Effective Java, 2nd Edition, Item 2: Builder Pattern"</a>. 
 * It is a flexible way to create complex objects step-by-step.</p>
 * 
 * <p>Additionally, this pattern is a well-known design pattern from the
 * <a href="https://en.wikipedia.org/wiki/Builder_pattern"> "Gang of Four" (GoF) design patterns</a>, 
 * which promotes the construction of complex objects step by step.</p>
 * 
 * @param <T> the type of object that this builder creates
 * 
 * @see <a href="https://blogs.oracle.com/javamagazine/post/exploring-joshua-blochs-builder-design-pattern-in-java">Exploring Joshua Bloch's Builder Design Pattern in Java</a>
 * @see <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder Pattern (Gang of Four)</a>
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public interface Builder<T> {

    /**
     * Builds and returns an instance of type T.
     * 
     * @return the built instance of type T.
     */
    T build();

    /**
     * Resets the builder to its initial state.
     * 
     * @return the {@link Builder} instance after reset.
     */
    Builder<T> reset();

    /**
     * Builds an instance of type T and then resets the builder to its initial state.
     * 
     * @return the {@link Builder} instance after build and reset.
     */
    default Builder<T> buildAndReset() {
        build();
        return reset();
    }
}

