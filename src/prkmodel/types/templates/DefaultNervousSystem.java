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

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import prkmodel.repast.RepastNervousSystemElementFactory;
import prkmodel.types.ModelElementType;
import prkmodel.types.NervousSystem;
import prkmodel.types.NervousSystemElement;
import prkmodel.types.NeuronNetwork;
import prkmodel.utils.Builder;
import prkmodel.utils.NervousSystemElementFactory;
import repast.simphony.random.RandomHelper;

/**
 * Default implementation of the {@link NervousSystem} interface.
 * Represents the nervous system with default behaviors and properties.
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
 * @see NervousSystem
 * @see NervousSystemElement
 * @see NeuronNetwork
 * @see ModelElementType
 * @see Builder
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public class DefaultNervousSystem implements NervousSystem {
    
    private static final Logger logger = Logger.getLogger(NervousSystem.class.getName());
    
    private final Random rand;
    private final int xdim;
    private final int ydim;
    private final int zdim;
    
    private final Set<NervousSystemElement> elements;
    private NeuronNetwork nnetwork;
    private final double mitoTransferThreshold;
    private final int lewyBodyDegenerationThreshold;
    private final double baseDegenerationProbability;
    private final int mitochondriaPerNeuron;

    /**
     * Constructs a new {@code DefaultNervousSystem} with the specified dimensions and parameters.
     * 
     * @param xdim the X dimension of the nervous system
     * @param ydim the Y dimension of the nervous system
     * @param zdim the Z dimension of the nervous system
     * @param mitoTransferThreshold the mitochondria transfer threshold
     * @param lewyBodyDegenerationThreshold the lewy body degeneration threshold
     * @param baseDegenerationProbability the base degeneration probability
     * @param mitochondriaPerNeuron the number of mitochondria per neuron
     * @throws IllegalArgumentException if any dimension is invalid
     */
    public DefaultNervousSystem(int xdim, int ydim, int zdim, double mitoTransferThreshold, int lewyBodyDegenerationThreshold, double baseDegenerationProbability, int mitochondriaPerNeuron) {
        this.rand = new Random();
        this.nnetwork = new DefaultNeuronNetwork(this);
        this.xdim = xdim;
        this.ydim = ydim;
        this.zdim = zdim;
        this.elements = new HashSet<>();
        this.mitoTransferThreshold = mitoTransferThreshold;
        this.lewyBodyDegenerationThreshold = lewyBodyDegenerationThreshold;
        this.baseDegenerationProbability = baseDegenerationProbability;
        this.mitochondriaPerNeuron = mitochondriaPerNeuron;
        
        getFactory().setNervousSystem(this);
        setupLogger();
        loggingSystemInit();
    }
    
    /**
     * Builder class for {@link DefaultNervousSystem}.
     */
    public static class DefaultNervousSystemBuilder implements Builder<DefaultNervousSystem> {
        private int dim;
        private double mitoTransferThreshold;
        private int lewyBodyDegenerationThreshold;
        private double baseDegenerationProbability;
        private int mitochondriaPerNeuron;
        
        public DefaultNervousSystemBuilder() {
            this.dim = 0;
            this.mitoTransferThreshold = 0.0;
            this.lewyBodyDegenerationThreshold = 0;
            this.baseDegenerationProbability = 0.0;
            this.mitochondriaPerNeuron = 0;
        }
        
        @Override
        public DefaultNervousSystem build() {
            return new DefaultNervousSystem(
                    dim, dim, dim, 
                    mitoTransferThreshold, 
                    lewyBodyDegenerationThreshold,
                    baseDegenerationProbability, 
                    mitochondriaPerNeuron);
        }
        
        @Override
        public DefaultNervousSystemBuilder reset() {
            return new DefaultNervousSystemBuilder();
        }
        
        public int getDim() { return dim; }
        
        public double getMitoTransferThreshold() { return mitoTransferThreshold; }
        
        public int getLewyBodyDegenerationThreshold() { return lewyBodyDegenerationThreshold; }
        
        public double getBaseDegenerationProbability() { return baseDegenerationProbability; }
        
        public int getMitochondriaPerNeuron() { return mitochondriaPerNeuron; }
        
        public DefaultNervousSystemBuilder setDim(int dim) {
            this.dim = dim;
            return this;
        }

        public DefaultNervousSystemBuilder setMitoTransferThreshold(double mitoTransferThreshold) {
            this.mitoTransferThreshold = mitoTransferThreshold;
            return this;
        }

        public DefaultNervousSystemBuilder setLewyBodyDegenerationThreshold(int lewyBodyDegenerationThreshold) {
            this.lewyBodyDegenerationThreshold = lewyBodyDegenerationThreshold;
            return this;
        }

        public DefaultNervousSystemBuilder setBaseDegenerationProbability(double baseDegenerationProbability) {
            this.baseDegenerationProbability = baseDegenerationProbability;
            return this;
        }

        public DefaultNervousSystemBuilder setMitochondriaPerNeuron(int mitochondriaPerNeuron) {
            this.mitochondriaPerNeuron = mitochondriaPerNeuron;
            return this;
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getDimensionX() {
        return xdim;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getDimensionY() {
        return ydim;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getDimensionZ() {
        return zdim;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Logger getLogger() {
        return logger;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Random getPNRG() {
        return rand;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NervousSystemElementFactory getFactory() {
        return RepastNervousSystemElementFactory.INSTANCE;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<NervousSystemElement> getElementsAsStream() {
        return Stream.of(elements, getNeurons())
                .parallel()
                .flatMap(x -> x.stream());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NeuronNetwork getNeuronNetwork() {
        return nnetwork;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getMitochondriaPerNeuron() {
        return mitochondriaPerNeuron;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public double getMitoTransferThreshold() {
        return mitoTransferThreshold;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getLewyBodyDegenerationThreshold() {
        return lewyBodyDegenerationThreshold;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public double getbaseDegenerationProbability() {
        return baseDegenerationProbability;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void addElement(NervousSystemElement element) {
        elements.add(Objects.requireNonNull(element));
        logger.info(String.format("%s added to NervousSystem@%d", element, this.hashCode()));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NeuronNetwork setNeuronNetwork(NeuronNetwork network) {
        if (!Objects.requireNonNull(network).getNervousSystem().equals(this)) {
            throw new IllegalArgumentException("Network must be referring to this nervous system!");
        }
        return this.nnetwork = network;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void step() {
        getDeletedElementsAsStream().forEach(el -> RepastNervousSystemElementFactory.INSTANCE.getContext().remove(el));
        getElements().removeAll(getDeletedElements());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public DefaultNervousSystem addNeurons(int neuronSize) {
        getNeuronNetwork().addNeuronsAndConnect(neuronSize);
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public DefaultNervousSystem addAstrocytes(int astrocyteSize) {
        for (int i = 0; i < astrocyteSize; i++) {
            addElement((getFactory().create(ModelElementType.ASTROCYTE, RandomHelper.nextDoubleFromTo(0, 49), RandomHelper.nextDoubleFromTo(0, 49), RandomHelper.nextDoubleFromTo(0, 49))));
        }
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public DefaultNervousSystem addMicroglia(int microgliaSize) {
        for (int i = 0; i < microgliaSize; i++) {
            addElement((getFactory().create(ModelElementType.MICROGLIA, RandomHelper.nextDoubleFromTo(0, 49), RandomHelper.nextDoubleFromTo(0, 49), RandomHelper.nextDoubleFromTo(0, 49))));
        }
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public DefaultNervousSystem filterOutNeurons() {
        getNeurons().stream().filter(n -> nnetwork.associatedEdges(n).isEmpty()).forEach(n -> n.delete());
        return this;
    }
    
    private void setupLogger() {
        try {
            FileHandler fileHandler = new FileHandler("app.log", true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to set up file handler", e);
        }
    }
    
    private void loggingSystemInit() {
        logger.info(
                new StringBuilder()
                    .append("Nervous System created").append(System.lineSeparator())
                    .append(this.toString()).append(System.lineSeparator()).toString());
    }
    
    @Override
    public String toString() {
        return new StringBuilder()
                .append(String.format("NervousSystem@%d", this.hashCode())).append(System.lineSeparator())
                .append(String.format("%4s", getNeuronNetwork())).append(System.lineSeparator())
                .append(String.format("%4s", String.format("Astrocytes: %d", getAstrocytes().size()))).append(System.lineSeparator())
                .append(String.format("%4s", String.format("Microglia: %d", getMicroglia().size())))
                .toString();
    }
}
