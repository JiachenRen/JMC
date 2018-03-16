package jmc.cas;

/**
 * Created by Jiachen on 16/05/2017.
 * Abstract parent of BinaryOperation and Unary Operation
 */
public abstract class Operation implements Operable, Nameable {
    private Operable leftHand;

    public Operation(Operable leftHand) {
        this.leftHand = leftHand;
    }

    /**
     * @param operable the Operable instance to be negated. IT IS NOT MODIFIED.
     * @return a new Operable instance that represents the negated version of the original
     */
    public static Operable negate(Operable operable) {
        return new BinaryOperation(new RawValue(-1), "*", operable);
    }

    public static Operable div(Number a, Number b) {
        return div(new RawValue(a), new RawValue(b));
    }

    public static Operation div(Operable o1, Operable o2) {
        return new BinaryOperation(o1.copy(), "/", o2.copy());
    }

    public static Operation mult(Number a, Number b) {
        return mult(new RawValue(a), new RawValue(b));
    }

    public static Operation mult(Operable o1, Operable o2) {
        return new BinaryOperation(o1.copy(), "*", o2.copy());
    }

    public static Operation mult(Operable a, Number b) {
        return mult(a, new RawValue(b));
    }

    public static Operation mult(Number a, Operable b) {
        return mult(new RawValue(a), b);
    }

    public static Operation add(Operable o1, Operable o2) {
        return new BinaryOperation(o1.copy(), "+", o2.copy());
    }

    public static Operation add(Operable o1, Number n) {
        return new BinaryOperation(o1.copy(), "+", new RawValue(n));
    }

    public static Operation sub(Operable o1, Operable o2) {
        return new BinaryOperation(o1.copy(), "-", o2.copy());
    }

    public static Operation exp(Number a, Number b) {
        return exp(new RawValue(a), new RawValue(b));
    }

    public static Operation exp(Operable o1, Operable o2) {
        return new BinaryOperation(o1.copy(), "^", o2.copy());
    }

    public static Operation exp(Operable a, Number b) {
        return exp(a, new RawValue(b));
    }

    public static Operation exp(Number a, Operable b) {
        return exp(new RawValue(a), b);
    }

    public abstract double eval(double x);

    /**
     * e.g. left hand of 2^x in a BinaryOperation is "2"
     * left hand of log<x> is "x"
     *
     * @return for BinaryOperation, the first arg is returned. For UnaryOperation, the only arg is returned.
     */
    public Operable getLeftHand() {
        return leftHand;
    }

    public Operable setLeftHand(Operable operable) {
        this.leftHand = operable;
        return this;
    }

    public abstract Operable toExponentialForm();

    /**
     * post operation: the operation itself is modified
     *
     * @return modified self.
     */
    public abstract Operable simplify();


    public Operation negate() {
        return Operation.mult(RawValue.ONE.negate(), this.copy());
    }

    public abstract Operation copy();

    public abstract Operation toAdditionOnly();
}
