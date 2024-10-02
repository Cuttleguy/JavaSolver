package util.math;

import java.util.*;

public class Polynomial {
    ArrayList<Monomial> monomials;

    public Polynomial()
    {
        monomials=new ArrayList<>();
    }
    public Polynomial(Monomial monomial)
    {
        monomials=new ArrayList<>();
        monomials.add(monomial);
    }
    public Polynomial(ArrayList<Monomial> polynomial)
    {
        monomials=polynomial;
    }
    public Polynomial(String string)
    {

        monomials=new ArrayList<>();
        ArrayList<String> tokens=new ArrayList<>();
        StringBuilder tokenBuilder= new StringBuilder();
        for (int i = string.length()-1; i >= 0; i--){
            char c = string.charAt(i);

            if(c=='+')
            {
                tokens.add(tokenBuilder.reverse().toString());
                tokenBuilder=new StringBuilder();
            }
            else if(c=='-')
            {
                tokens.add("-"+tokenBuilder.reverse().toString());
                tokenBuilder=new StringBuilder();
            }
            else if(c!=' ')
            {
                tokenBuilder.append(c);

            }


        }
        if(!tokenBuilder.isEmpty())
        {
            tokens.add(tokenBuilder.reverse().toString());
        }
        for(String token : tokens)
        {

            monomials.add(new Monomial(token));
        }
    }
    public String toString()
    {
        order();
        StringBuilder returnBuilder = new StringBuilder();
        int i = 0;
        for(Monomial monomial:monomials)
        {
            if(monomial.coefficient.imag==0.0 && monomial.coefficient.real < 0.0 || i<=0)
            {
                returnBuilder.append(monomial);

            }
            else{
                returnBuilder.append('+');
                returnBuilder.append(monomial);
            }
            i++;
        }
        return returnBuilder.toString();

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
        monomials.clear();
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
            for(Monomial otherMonomial:other.monomials)
            {
                toReturn.monomials.add(monomial*otherMonomial);
            }
        }
        toReturn.Compress();
        return toReturn;
    }
    public Polynomial div(Monomial other)
    {
        Polynomial toReturn = new Polynomial();
        for(Monomial monomial:monomials)
        {
            toReturn.monomials.add(monomial/other);
        }
        toReturn.Compress();
        return toReturn;
    }
    public void order()
    {
        ArrayList<Double> exponents=new ArrayList<>();
        for (Monomial monomial:monomials)
        {
            exponents.add(monomial.exponent);
        }
        Collections.sort(exponents);
        Collections.reverse(exponents);
        ArrayList<Monomial> newMonomials = new ArrayList<>();
        for(double exponent:exponents)
        {
            newMonomials.add(new Monomial(coeffientAtDegree(exponent),exponent));
        }
        monomials=newMonomials;


    }
    public double getDegree()
    {
        double degree=Double.NEGATIVE_INFINITY;
        for(Monomial monomial:monomials)
        {
            if(degree<monomial.exponent && !Objects.equals(monomial.coefficient, new Complex(0.0)))
            {
                degree=monomial.exponent;

            }
        }
        return degree;
    }
    public double getLowestExponent()
    {
        double degree=Double.POSITIVE_INFINITY;
        for(Monomial monomial:monomials)
        {
            if(degree>monomial.exponent && !Objects.equals(monomial.coefficient, new Complex(0.0)))
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
        Complex zero = new Complex(0.0);
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
        q.Compress();
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
    public Polynomial rem(Polynomial other)
    {
        if(false){throw new ArithmeticException("Divided by zero");}
        Polynomial q = new Polynomial();
        Polynomial r=this;
        int counter=0;
        while(!r.isZero()&&r.getDegree()>=other.getDegree())
        {
            Polynomial t = new Polynomial(new Monomial(r.coeffientAtDegree(r.getDegree()),0.0));
            q=q+t;
            r=r-other*t;
        }
        r.Compress();
        return r;
    }

    public Polynomial plus(Polynomial other)
    {
        Polynomial toReturn=new Polynomial(monomials);
        toReturn.monomials.addAll(other.monomials);
        toReturn.Compress();
        return toReturn;
    }
    public double getConstant()
    {

        for(Monomial monomial:monomials)
        {

            if(monomial.exponent!=0.0)
            {
                throw new RuntimeException("You didn't Check");
            }
            if(monomial.coefficient.imag!=0.0)
            {
                throw new RuntimeException("You Didn't Check");
            }
            return monomial.coefficient.real;
        }
        throw new RuntimeException("Zero");

    }
    public boolean isRealConstant()
    {
        if(!isConstant()) return false;
        for(Monomial monomial:monomials)
        {

            if(monomial.exponent!=0.0)
            {
                return false;
            }
            if(monomial.coefficient.imag!=0.0)
            {
                return false;
            }

        }
        return true;
    }
    public boolean isConstant()
    {

        for(Monomial monomial:monomials)
        {

            if(monomial.exponent!=0.0)
            {
                return false;
            }
        }
        return true;
    }
    public boolean divisible(Polynomial other)
    {
        return (this%other).isZero();
    }
    public Polynomial gcf(Polynomial other)
    {
        Polynomial a = this;
        Polynomial b = other;
        if(a.isZero()) return b;
        if(b.isZero()) return a;
        while(!b.isZero())
        {
            Polynomial remainder = a % b;
            a=b;
            b=remainder;
        }
        return a;
    }
    public boolean equals(Polynomial other)
    {

        for(Monomial monomial:monomials)
        {
            if(other.coeffientAtDegree(monomial.exponent)!=monomial.coefficient)
            {
                return false;
            }
        }
        for(Monomial monomial:other.monomials)
        {
            if(coeffientAtDegree(monomial.exponent)!=monomial.coefficient)
            {
                return false;
            }
        }
        return true;
    }




}
