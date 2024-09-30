package util.math;

public class Rational {
    public Polynomial numerator;
    public Polynomial denominator;
    public Rational(Polynomial newNumerator, Polynomial newDenominator)
    {
        if (newDenominator.isZero()) throw new ArithmeticException("Cannot Divide By Zero");
        if(newNumerator.divisible(newDenominator))
        {
            new Rational(newNumerator/newDenominator);
        }
        else
        {
            numerator=new
        }
    }

    public Rational(Polynomial newNumerator)
    {
        numerator=newNumerator;
        denominator=new Polynomial()+new Monomial(new Complex(1,0),0.0);

    }

    public Rational plus()
    {

    }
    public Rational unaryMinus()
    {
        return()
    }

    public Rational times(Rational other)
    {

    }
}
