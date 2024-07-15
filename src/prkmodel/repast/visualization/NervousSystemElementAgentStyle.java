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

package prkmodel.repast.visualization;

import java.awt.Color;
import java.awt.Font;

import org.jogamp.java3d.Shape3D;

import prkmodel.types.ModelElementType;
import prkmodel.types.NervousSystemElement;
import repast.simphony.visualization.visualization3D.AppearanceFactory;
import repast.simphony.visualization.visualization3D.ShapeFactory;
import repast.simphony.visualization.visualization3D.style.Style3D;
import repast.simphony.visualization.visualization3D.style.TaggedAppearance;
import repast.simphony.visualization.visualization3D.style.TaggedBranchGroup;

/**
 * Defines the 3D style for Parkinson's model elements in the Repast simulation.
 * This class implements {@link Style3D} to provide appearance, label, and 
 * transformation details for visualizing model elements.
 * 
 * @see Style3D
 * @see prkmodel.types.NervousSystemElement
 * @see ModelElementType
 * @see AppearanceFactory
 * @see ShapeFactory
 * 
 * @author Samuele95
 * @version 1.0
 * @since 2024-07-03
 */
public class NervousSystemElementAgentStyle implements Style3D<NervousSystemElement> {

    /**
     * {@inheritDoc}
     */
    @Override
    public TaggedBranchGroup getBranchGroup(NervousSystemElement agent, TaggedBranchGroup taggedGroup) {
        if (taggedGroup == null || taggedGroup.getTag() == null) {
            taggedGroup = new TaggedBranchGroup("DEFAULT");
            Shape3D sphere = ShapeFactory.createSphere(.03f, "DEFAULT");
            sphere.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
            taggedGroup.getBranchGroup().addChild(sphere);

            return taggedGroup;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float[] getRotation(NervousSystemElement agent) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLabel(NervousSystemElement agent, String currentLabel) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color getLabelColor(NervousSystemElement agent, Color currentColor) {
        return Color.YELLOW;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Font getLabelFont(NervousSystemElement agent, Font currentFont) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LabelPosition getLabelPosition(NervousSystemElement agent, LabelPosition currentPosition) {
        return LabelPosition.NORTH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getLabelOffset(NervousSystemElement agent) {
        return .035f;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaggedAppearance getAppearance(NervousSystemElement agent, TaggedAppearance taggedAppearance, Object shapeID) {
        if (taggedAppearance == null) {
            taggedAppearance = new TaggedAppearance();
        }
        
        if (agent.isDeleted())
        	AppearanceFactory.setMaterialAppearance(taggedAppearance.getAppearance(), new Color(0, 0, 0, 0));
        if (agent.getType().equals(ModelElementType.NEURON))
            AppearanceFactory.setMaterialAppearance(taggedAppearance.getAppearance(), Color.blue);
        if (agent.getType().equals(ModelElementType.MITOCHONDRIA))
            AppearanceFactory.setMaterialAppearance(taggedAppearance.getAppearance(), Color.yellow);
        if (agent.getType().equals(ModelElementType.MICROGLIA))
            AppearanceFactory.setMaterialAppearance(taggedAppearance.getAppearance(), Color.pink);
        if (agent.getType().equals(ModelElementType.LEWYBODY))
            AppearanceFactory.setMaterialAppearance(taggedAppearance.getAppearance(), Color.magenta);
        if (agent.getType().equals(ModelElementType.PROCYTOKINE))
            AppearanceFactory.setMaterialAppearance(taggedAppearance.getAppearance(), Color.red);
        if (agent.getType().equals(ModelElementType.ANTICYTOKINE))
            AppearanceFactory.setMaterialAppearance(taggedAppearance.getAppearance(), Color.white);
        if (agent.getType().equals(ModelElementType.ASTROCYTE))
            AppearanceFactory.setMaterialAppearance(taggedAppearance.getAppearance(), Color.cyan);

        return taggedAppearance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float[] getScale(NervousSystemElement agent) {
        return null;
    }
}

