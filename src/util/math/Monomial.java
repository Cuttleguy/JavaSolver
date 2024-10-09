package util.math;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Monomial {
    public Complex coefficient;
    public Integer exponent;

    public Monomial(Complex newCoefficient, Integer newExponent)
    {
        coefficient=newCoefficient;
        exponent=newExponent;
    }
    public Monomial(Complex newCoefficient)
    {
        coefficient=newCoefficient;
        exponent=0;
    }
    public Monomial(String string)
    {
        try {
            Pattern realWithExponent = Pattern.compile("(-?\\d+\\.?\\d*)?x\\^(-?\\d+)");
            Matcher rWEM = realWithExponent.matcher(string);
            if (rWEM.matches()) {

                if (rWEM.group(1) == null) {
                    coefficient = new Complex(1.0);
                } else {
                    coefficient = new Complex(Double.parseDouble(rWEM.group(1)));
                }

                exponent = Integer.parseInt(rWEM.group(2));
                return;

            }
            Pattern realWithoutExponent = Pattern.compile("(-?\\d+\\.?\\d*)?x");
            Matcher rwEM = realWithoutExponent.matcher(string);
            if (rwEM.matches()) {

                if (rwEM.group(1) == null) {
                    coefficient = new Complex(1.0);
                } else {
                    coefficient = new Complex(Double.parseDouble(rwEM.group(1)));
                }

                exponent = 1;

                return;

            }
            Pattern negativerealWithExponent = Pattern.compile("-x\\^(-?\\d+)");
            Matcher nrWEM = negativerealWithExponent.matcher(string);
            if (nrWEM.matches()) {

                coefficient = new Complex(-1);
                exponent = Integer.parseInt(nrWEM.group());
            }

            if (string.equals("-x")) {
                coefficient = new Complex(-1);
                exponent = 1;
                return;
            }
            Pattern realConstant = Pattern.compile("(-?\\d+\\.?\\d*)");
            Matcher rC = realConstant.matcher(string);

            if (rC.matches()) {


                if (rC.group(1) == null) {
                    coefficient = new Complex(1.0);
                } else {
                    coefficient = new Complex(Double.parseDouble(rC.group(1)));
                }

                exponent = 0;

                return;

            }
            Pattern imaginary = Pattern.compile("(-?\\d+\\.?\\d*)?i");
            Matcher iM = imaginary.matcher(string);
            if (rwEM.matches()) {

                if (iM.group(1) == null) {
                    coefficient = new Complex(1.0);
                } else {
                    coefficient = new Complex(Double.parseDouble(iM.group(1)));
                }

                exponent = 0;

                return;

            }
            if(string.equals("i"))
            {
                coefficient=Complex.i;
                exponent=0;
                return;
            } else if (string.equals("-i")) {
                coefficient = -Complex.i;
                exponent=0;
                return;
            }

            throw new RuntimeException(string+" doesn't match");
        }
        catch (IllegalStateException | NumberFormatException e)
        {
            throw new RuntimeException(e+"\n"+string);
        }
    }
    public String toString()
    {
        String coefficientStr;
        if(coefficient.equals(new Complex(1)) && exponent != 0)
        {
            coefficientStr="";
        }
        else if(coefficient.equals(new Complex(-1))&&exponent!=0)
        {
            coefficientStr="-";
        }
        else if(coefficient.real==0.0 || coefficient.imag==0.0)
        {
            coefficientStr=coefficient.toString();
        } else {
            coefficientStr="("+coefficient.toString()+")";
        }
        String exponentStr;
        exponentStr=exponent.toString();

        if(exponent==0.0)
        {
            return coefficientStr;
        } else if (exponent==1.0) {
            return coefficientStr+"x";
        }
        else
        {
            return coefficientStr+"x^"+exponentStr;
        }


    }
    Monomial times(Monomial other)
    {
        return new Monomial(coefficient*other.coefficient,exponent+other.exponent);
    }
    Monomial div(Monomial other)
    {
        return new Monomial(coefficient/other.coefficient,exponent-other.exponent);
    }
    public boolean equals(Monomial other)
    {
        return coefficient==other.coefficient&& Objects.equals(exponent, other.exponent);
    }
    public Monomial derivative()
    {
        return new Monomial(coefficient*exponent,exponent-1);
    }

}
