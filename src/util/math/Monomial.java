package util.math;

public class Monomial {
    public Complex coefficient;
    public Complex exponent;
    public Monomial(Complex newCoefficient, Complex newExponent)
    {
        coefficient=newCoefficient;
        exponent=newExponent;
    }

    public String toString()
    {
        String coefficientStr;
        if(coefficient.real==0.0 || coefficient.imag==0.0)
        {
            coefficientStr=coefficient.toString();
        }
        else {
            coefficientStr="("+coefficient.toString()+")";
        }
        String exponentStr;
        if(exponent.real==0.0 || exponent.imag==0.0)
        {
            exponentStr=exponent.toString();
        }
        else {
            exponentStr="("+exponent.toString()+")";
        }
        return coefficientStr+"x^"+exponentStr;
    }

}
