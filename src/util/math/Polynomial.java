package util.math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Polynomial {
    ArrayList<Monomial> monomials;
    public Polynomial()
    {
        monomials=new ArrayList<>();
    }
    public Polynomial(ArrayList<Monomial> polynomial)
    {
        monomials=polynomial;
    }

    public void Compress()
    {
        Map<Double, Complex> exponentToCoefficentMap= new HashMap<>();
        for(Monomial monomial : monomials)
        {
            if(exponentToCoefficentMap.containsKey(monomial.exponent))
            {
                exponentToCoefficentMap.set(monomial.exponent,exponentToCoefficentMap.get(monomial.exponent)+monomial.coefficient);

            }
            else
            {
                exponentToCoefficentMap.put(monomial.exponent,monomial.coefficient);
            }
        }
        for(Map.Entry<Double, Complex> entry:exponentToCoefficentMap.entrySet())
        {
            monomials.add(new Monomial(entry.getValue(),entry.getKey()));
        }
    }
    public Polynomial unaryMinus()
    {
        Polynomial toReturn = new Polynomial();
        for(Monomial monomial : monomials)
        {
            toReturn.monomials.add(new Monomial(-monomial.coefficient,monomial.exponent));
        }
        return toReturn;
    }
    public Polynomial minus(Polynomial other)
    {
        return this+ (-other);

    }
    public Polynomial times(Polynomial other)
    {
        Polynomial toReturn = new Polynomial();
        for(Monomial monomial:monomials)
        {
            for(Monomial otherMonomial:monomials)
            {
                toReturn.monomials.add(monomial*otherMonomial);
            }
        }
        return toReturn;
    }
    public Polynomial div(Monomial other)
    {
        Polynomial toReturn = new Polynomial();
        for(Monomial monomial:monomials)
        {
            toReturn.monomials.add(monomial/other);
        }
        return toReturn;
    }
    public double getDegree()
    {
        double degree=Double.NEGATIVE_INFINITY;
        for(Monomial monomial:monomials)
        {
            if(degree<monomial.exponent)
            {
                degree=monomial.exponent;

            }
        }
        return degree;
    }
    public Complex coeffientAtDegree(Double exponent)
    {
        Map<Double, Complex> exponentToCoefficentMap= new HashMap<>();
        for(Monomial monomial : monomials)
        {
            if(exponentToCoefficentMap.containsKey(monomial.exponent))
            {

                exponentToCoefficentMap.set(monomial.exponent,exponentToCoefficentMap.get(monomial.exponent)+monomial.coefficient);

            }
            else
            {
                exponentToCoefficentMap.put(monomial.exponent,monomial.coefficient);
            }
        }
        return exponentToCoefficentMap.get(exponent);
    }
    public boolean isZero()
    {
        Complex zero =new Complex(0.0);
        for(Monomial monomial:monomials)
        {

            if(monomial.coefficient!=zero)
            {
                return false;
            }
        }
        return true;
    }

    public Polynomial div(Polynomial other)
    {
        if(false){throw new ArithmeticException("Divided by zero");}
        Polynomial q = new Polynomial();
        Polynomial r=this;
        int counter=0;
        while(!r.isZero())
        {
            Monomial t = new Monomial(r.coeffientAtDegree(r.getDegree()),0.0);
            q+=t;
            r=r-other*t;
        }
        return q;

    }
    public Polynomial times(Monomial other)
    {
        Polynomial toReturn = new Polynomial();
        for(Monomial monomial:monomials)
        {
            toReturn.monomials.add(monomial*other);
        }
        return toReturn;
    }
    public Polynomial plus(Monomial other)
    {
        Polynomial toReturn = new Polynomial(monomials);
        toReturn.monomials.add(other);
        return toReturn;
    }


    public Polynomial plus(Polynomial other)
    {
        Polynomial toReturn=new Polynomial(monomials);
        toReturn.monomials.addAll(other.monomials);
        toReturn.Compress();
        return toReturn;
    }


}
