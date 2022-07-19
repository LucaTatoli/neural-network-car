package logic;

import drawable.Point2D;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class NeuralNetwork {

    private float[] inputsLayer, outputsLayer, middleLayer;
    private boolean[] inputsActive, middleActive, outputsActive;
    private float outputsDiv, middleDiv;
    private Map<Point2D, Float> inputToMiddle, middleToOutput;

    public NeuralNetwork(int inputs, int outputs, int middle) {
        inputsLayer = new float[inputs];
        outputsLayer = new float[outputs];
        middleLayer = new float[middle];
        middleActive = new boolean[middle];
        inputsActive = new boolean[inputs];
        outputsActive = new boolean[outputs];
        middleToOutput = new HashMap<>();
        inputToMiddle = new HashMap<>();
        generateValues();
    }

    public NeuralNetwork(float[] inputsLayer, float[] outputsLayer, float[] middleLayer, Map<Point2D, Float> inputToMiddle, Map<Point2D, Float> middleToOutput) {
        this.inputsLayer = inputsLayer;
        this.outputsLayer = outputsLayer;
        this.middleLayer = middleLayer;
        this.inputToMiddle = inputToMiddle;
        this.middleToOutput = middleToOutput;
        this.middleActive = new boolean[middleLayer.length];
        this.inputsActive = new boolean[inputsLayer.length];
        this.outputsActive = new boolean[outputsLayer.length];

        for(int i = 0; i < inputsLayer.length; i++)
            for(int j = 0; j < middleLayer.length; j++)
                middleDiv += inputToMiddle.get(new Point2D(i, j));

        for(int i = 0; i < middleLayer.length; i++)
            for(int j = 0; j < outputsLayer.length; j++)
                outputsDiv += middleToOutput.get(new Point2D(i, j));
    }

    public float evaluateNeuron(NeuralNetworkLayer layer, int neuron) {
        float value = 0;
        switch (layer) {
            case INPUTS:
                value = inputsLayer[neuron];
                break;
            case OUTPUTS:
                value = 0;
                for(int i = 0; i < middleLayer.length; i++)
                    value += middleToOutput.get(new Point2D(i, neuron));
                break;
            case MIDDLE:
                value = 0;
                for(int i = 0; i < inputsLayer.length; i++)
                    value += inputToMiddle.get(new Point2D(i, neuron));
                break;
        }
        return value;
    }

    public void putInputs(float[] inputs) {
        for(int i = 0; i < inputsLayer.length; i++) {
            if(inputs[i] >= inputsLayer[i])
                inputsActive[i] = true;
            else
                inputsActive[i] = false;
        }

        float totalValue;
        for(int i = 0; i < middleLayer.length; i++) {
            totalValue = 0;
            for (int j = 0; j < inputsLayer.length; j++)
                if (inputsActive[j])
                    totalValue += inputToMiddle.get(new Point2D(j, i));
            totalValue /= middleDiv;
            if(totalValue > middleLayer[i])
                middleActive[i] = true;
            else
                middleActive[i] = false;
        }

        for(int i = 0; i < outputsLayer.length; i++) {
            totalValue = 0;
            for (int j = 0; j < middleLayer.length; j++)
                if (middleActive[j])
                    totalValue += middleToOutput.get(new Point2D(j, i));
            totalValue /= outputsDiv;
            if(totalValue > outputsLayer[i])
                outputsActive[i] = true;
            else
                outputsActive[i] = false;
        }
    }

    public boolean[] getOutputsActive() {
        return outputsActive;
    }

    public void generateValues() {
        Random r = new Random();
        float temp;
        for(int i = 0; i < inputsLayer.length; i++)
            inputsLayer[i] = r.nextFloat();

        for(int i = 0; i < outputsLayer.length; i++) {
            temp = r.nextFloat();
            outputsLayer[i] = temp;
            outputsDiv += temp;
            for(int j = 0; j < middleLayer.length; j++)
                middleToOutput.put(new Point2D(j, i), r.nextFloat());
        }

        for(int i = 0; i < middleLayer.length; i++) {
            temp = r.nextFloat();
            middleLayer[i] = temp;
            middleDiv += temp;
            for(int j = 0; j < inputsLayer.length; j++)
                inputToMiddle.put(new Point2D(j, i), r.nextFloat());
        }
    }

    public float[] getInputsLayer() {
        return inputsLayer;
    }

    public float[] getOutputsLayer() {
        return outputsLayer;
    }

    public float[] getMiddleLayer() {
        return middleLayer;
    }

    public static NeuralNetwork reproduce(NeuralNetwork n1, NeuralNetwork n2, boolean mutation) {
        NeuralNetwork son;
        float[] inputsLayer = new float[n1.getInputsLayer().length];
        float[] outputsLayer = new float[n1.getOutputsLayer().length];
        float[] middleLayer = new float[n1.getMiddleLayer().length];

        for(int i = 0; i < inputsLayer.length; i++)
            if(i < inputsLayer.length/2)
                inputsLayer[i] = n1.getInputsLayer()[i];
            else
                inputsLayer[i] = n2.getInputsLayer()[i];

        for(int i = 0; i < outputsLayer.length; i++)
            if(i < outputsLayer.length/2)
                outputsLayer[i] = n1.getOutputsLayer()[i];
            else
                outputsLayer[i] = n2.getOutputsLayer()[i];

        for(int i = 0; i < middleLayer.length; i++)
            if(i < middleLayer.length/2)
                middleLayer[i] = n1.getMiddleLayer()[i];
            else
                middleLayer[i] = n2.getMiddleLayer()[i];

        Random r = new Random();

        if(mutation && r.nextFloat() < 0.2) {

            switch (r.nextInt(3)) {
                case 0:
                    inputsLayer[r.nextInt(inputsLayer.length)] = r.nextFloat();
                    break;
                case 1:
                    outputsLayer[r.nextInt(outputsLayer.length)] = r.nextFloat();
                    break;
                case 2:
                    middleLayer[r.nextInt(middleLayer.length)] = r.nextFloat();
                    break;
            }
        }

        son = new NeuralNetwork(inputsLayer, outputsLayer, middleLayer, n1.getInputToMiddle(), n2.getMiddleToOutput());

        return son;
    }

    public Map<Point2D, Float> getInputToMiddle() {
        return inputToMiddle;
    }

    public Map<Point2D, Float> getMiddleToOutput() {
        return middleToOutput;
    }


    public enum NeuralNetworkLayer {
        INPUTS,
        OUTPUTS,
        MIDDLE
    }

}
