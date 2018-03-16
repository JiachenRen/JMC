package jmc.cas;

import java.util.ArrayList;

/**
 * Created by Jiachen on 16/05/2017.
 * Constants
 */
public class Constants {
    private static ArrayList<Constant> constants;
    public static final Constant E = getConstant("e");
    public static final Constant PI = getConstant("pi");
    public static final Constant π = getConstant("π");

    static {
        constants = new ArrayList<>();
        define("e", () -> Math.E);
        define("pi", () -> Math.PI);
        define("∞", () -> Double.POSITIVE_INFINITY);
        define("rand", Math::random);
        define("π", () -> Math.PI);
    }

    public static boolean contains(String symbol) {
        for (Constant constant : constants) {
            if (constant.getName().equals(symbol))
                return true;
        }
        return false;
    }

    /**
     * add or define a Constant object into the static ArrayList Constants.
     * TODO debug
     *
     * @param name          the name of the constant
     * @param computedConst the new computed const instance
     */
    public static void define(String name, ComputedConst computedConst) {
        boolean defined = false;
        for (Constant constant : constants) {
            if (constant.getName().equals(name)) {
                constant.computedConst = computedConst;
                defined = true;
            }
        }
        if (!defined) constants.add(new Constant(name, computedConst));
    }

    public static double valueOf(String constant) {
        for (Constant c : constants) {
            if (c.getName().equals(constant))
                return c.computedConst.compute();
        }
        return 0.0;
    }

    public static ArrayList<Constant> list() {
        return constants;
    }

    public static Constant getConstant(String name) {
        for (Constant constant : constants) {
            if (constant.getName().equals(name))
                return constant;
        }
        throw new JMCException("constant + \"" + name + "\" does not exist");
    }

    public interface ComputedConst {
        double compute();
    }

    public static class Constant extends Variable implements Nameable {
        private ComputedConst computedConst;

        public Constant(String name, ComputedConst computedConst) {
            super(name);
            this.computedConst = computedConst;

        }

        Constant(Constant other) {
            super(other.getName());
            this.computedConst = other.computedConst;
        }

        public double eval(double x) {
            return computedConst.compute();
        }

        public String toString() {
            return getName();
        }

        @Override
        public Constant copy() {
            return new Constant(getName(), computedConst);
        }

        public int complexity() {
            return 2;
        }

        public boolean equals(Operable other) {
            return other instanceof Constant && (((Constant) other).getName().equals(getName())
                    || other.val() == this.val());
        }

        public Operable plugIn(Variable var, Operable nested) {
            return new Constant(this);
        }

        @Override
        public double val() {
            return computedConst.compute();
        }

        public int numNodes() {
            return 1;
        }
    }
}
