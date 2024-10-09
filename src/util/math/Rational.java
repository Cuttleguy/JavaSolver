package util.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Rational {
    public Polynomial numerator;
    public Polynomial denominator;
    public Rational(Polynomial newNumerator, Polynomial newDenominator)
    {
        if (newDenominator.isZero())
        {

            throw new ArithmeticException("Cannot Divide By Zero. Fraction is: "+newNumerator+"/"+newDenominator);
        }

        Polynomial gcf=Polynomial.gcf(newNumerator,newDenominator);

        numerator=newNumerator/gcf;
        denominator=newDenominator/gcf;
//        List<Complex> coefficients = newNum.coefficients();
//        List<Complex> denCoef = newDen.coefficients();
//        coefficients.addAll(denCoef);
//        double lcm=MathUtil.findLCMMultiplier(coefficients);
//        System.out.println("LCM: "+lcm);
//        numerator=newNum*(new Monomial(new Complex(lcm)));
//        denominator=newDen*(new Monomial(new Complex(lcm)));



    }
    public Complex plugIn(Complex x)
    {
        return numerator.plugIn(x)/denominator.plugIn(x);
    }
    public Rational derivative()
    {
        return new Rational(numerator.derivative()*denominator-numerator*denominator.derivative(),denominator*denominator);
    }

    public Rational(Polynomial newNumerator)
    {
        numerator=newNumerator;
        denominator=Polynomial.one;


    }
    public boolean isPoly()
    {
        boolean found = false;
        for(Monomial monomial : denominator.monomials)
        {
            if(!Objects.equals(monomial.coefficient, new Complex(0.0)))
            {
                if(found)
                {
                    return false;
                }
                else
                {
                    if(monomial.exponent!=0.0)
                    {
                        return false;
                    }
                    else {
                        found = true;
                    }
                }
            }
        }
        return true;
    }
    public String toString()
    {
        return "("+numerator.toString()+") / ("+denominator.toString()+")";
    }

    public static Rational pow(Rational a, Rational b)
    {
        if(!b.isPoly())
        {
            throw new RuntimeException("No Exponential Roots");
        }
        else if(!b.numerator.isConstant())
        {
            throw new RuntimeException("No Exponentials");
        } else if (b.numerator.getConstant()%1.0!=0.0) {
            throw new RuntimeException("No Rooting 2: "+b.numerator.getConstant());
        }
        else
        {
            int power = Double.valueOf(b.numerator.getConstant()).intValue();

            Rational toReturn= new Rational(new Polynomial(new Monomial(new Complex(1.0),0)));
            for (int i = 0; i < power; i++) {
                toReturn*=a;
            }
            return toReturn;
        }

    }
    public Rational plus(Rational other)
    {
        return new Rational(numerator*other.denominator+denominator*other.numerator,denominator*other.denominator);
    }
    public Rational minus(Rational other)
    {

        return this+ (-other);

    }
    public Rational unaryMinus()
    {

        return new Rational(-this.numerator,this.denominator);
    }

    public Rational times(Rational other)
    {
        return new Rational(numerator*other.numerator,denominator*other.denominator);
    }
    public Rational invert()
    {
        return new Rational(denominator,numerator);
    }
    public Rational div(Rational other)
    {

        return this*other.invert();
    }
    public Rational plus(Polynomial other)
    {
        return this+new Rational(other);
    }
    public Rational minus(Polynomial other)
    {
        return this-new Rational(other);
    }
    public Rational times(Polynomial other)
    {
        return this*new Rational(other);
    }
    public Rational div(Polynomial other)
    {
        return this/new Rational(other);
    }
    public List<Complex> solve() {
        List<Complex> numSolutions = numerator.solve();
        if (denominator.isConstant()) {
            return numSolutions;
        } else {
            List<Complex> denSolutions = denominator.solve();
            List<Complex> roots = new ArrayList<>();
            for (Complex numRoot : numSolutions) {
                if (!denSolutions.contains(numRoot)) {
                    roots.add(numRoot);
                }
            }
            return roots;
        }
    }

}
