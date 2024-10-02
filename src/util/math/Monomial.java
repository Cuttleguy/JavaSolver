package util.math;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Monomial {
    public Complex coefficient;
    public Double exponent;

    public Monomial(Complex newCoefficient, Double newExponent)
    {
        coefficient=newCoefficient;
        exponent=newExponent;
    }
    public Monomial(String string)
    {

        Pattern realWithExponent = Pattern.compile("(-?\\d+\\.?\\d*)?x\\^(-?\\d+\\.?\\d*)");
        Matcher rWEM = realWithExponent.matcher(string);
        if(rWEM.matches())
        {

            if(rWEM.group(1)==null)
            {
                coefficient=new Complex(1.0);
            }
            else
            {
                coefficient = new Complex(Double.parseDouble(rWEM.group(1)));
            }

            exponent=Double.parseDouble(rWEM.group(2));
            return;

        }
        Pattern realWithoutExponent = Pattern.compile("(-?\\d+\\.?\\d*)?x");
        Matcher rwEM = realWithoutExponent.matcher(string);
        if(rwEM.matches())
        {

            if(rwEM.group(1)==null)
            {
                coefficient=new Complex(1.0);
            }
            else
            {
                coefficient = new Complex(Double.parseDouble(rWEM.group(1)));
            }

            exponent=1.0;

            return;

        }
        Pattern negativerealWithExponent = Pattern.compile("-x\\^(-?\\d+\\.?\\d*)");
        Matcher nrWEM = negativerealWithExponent.matcher(string);
        if(nrWEM.matches())
        {

            coefficient=new Complex(-1);
            exponent=Double.parseDouble(nrWEM.group());
        }

        if(string.equals("-x"))
        {
            coefficient=new Complex(-1);
            exponent=1.0;
            return;
        }
        Pattern realConstant = Pattern.compile("(-?\\d+\\.?\\d*)");
        Matcher rC = realConstant.matcher(string);

        if(rC.matches())
        {


            if(rC.group(1)==null)
            {
                coefficient=new Complex(1.0);
            }
            else
            {
                coefficient = new Complex(Double.parseDouble(rC.group(1)));
            }

            exponent=0.0;

            return;

        }
        Pattern imaginary = Pattern.compile("(-?\\d+\\.?\\d*)?i");
        Matcher iM = realWithoutExponent.matcher(string);
        if(rwEM.matches())
        {

            if(iM.group(1)==null)
            {
                coefficient=new Complex(1.0);
            }
            else
            {
                coefficient = new Complex(Double.parseDouble(iM.group(1)));
            }

            exponent=0.0;

            return;

        }
        throw new RuntimeException("Doesn't Match");
    }
    public String toString()
    {
        String coefficientStr;
        if(coefficient.equals(new Complex(1)))
        {
            coefficientStr="";
        }
        else if(coefficient.equals(new Complex(-1)))
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
        if(exponent%1==0)
        {
            exponentStr=Integer.valueOf(exponent.intValue()).toString();
        }
        else
        {
            exponentStr=exponent.toString();
        }

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


}
