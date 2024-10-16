package util.math;

import java.util.*;



public class Polynomial {
    public ArrayList<Monomial> monomials;
    public static final Polynomial one = new Polynomial(new Monomial(Complex.one));
    public Polynomial()
    {
        monomials=new ArrayList<>();
    }

    public Polynomial(Monomial monomial)
    {
        ArrayList<Monomial> monos=new ArrayList<>();
        monos.add(monomial);

        monomials=monos;
    }
    public Polynomial(ArrayList<Monomial> polynomial)
    {
        monomials=new ArrayList<>();
        monomials.addAll(polynomial);

        Compress();
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
        Compress();
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
        Map<Integer, Complex> exponentToCoefficentMap= new HashMap<>();
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
        for(Map.Entry<Integer, Complex> entry:exponentToCoefficentMap.entrySet())
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
        ArrayList<Integer> exponents=new ArrayList<>();
        for (Monomial monomial:monomials)
        {
            exponents.add(monomial.exponent);
        }
        Collections.sort(exponents);
        Collections.reverse(exponents);
        ArrayList<Monomial> newMonomials = new ArrayList<>();
        for(int exponent:exponents)
        {
            newMonomials.add(new Monomial(coeffientAtDegree(exponent),exponent));
        }
        monomials=newMonomials;


    }
    public Integer getDegree()
    {
        int degree=monomials[0].exponent;
        for(Monomial monomial:monomials)
        {
            if(degree<monomial.exponent &&monomial.coefficient!=Complex.zero)
            {
                degree=monomial.exponent;

            }

        }
        if(degree==-999999999)
        {
            throw new RuntimeException("ZERO");
        }
        return degree;
    }
    public Integer getLowestExponent()
    {
        Integer degree=999999999;
        for(Monomial monomial:monomials)
        {
            if(degree>monomial.exponent && monomial.coefficient.equals(new Complex(0.0)))
            {
                degree=monomial.exponent;

            }
        }
        return degree;
    }
    public Complex coeffientAtDegree(Integer exponent)
    {
        Map<Integer, Complex> exponentToCoefficentMap= new HashMap<>();
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
        return exponentToCoefficentMap.getOrDefault(exponent, Complex.zero);
    }
    public boolean isZero()
    {

        for(Monomial monomial:monomials)
        {

            if(!monomial.coefficient.equals(Complex.zero))
            {

                return false;
            }
        }
        return true;
    }

    public Polynomial div(Polynomial other)
    {
        if(other.isZero()){throw new ArithmeticException("Divided by zero");}
        Polynomial q = new Polynomial();
        Polynomial r=this.DeepCopy();
        int counter=0;
        while(!r.isZero()&&counter<32)
        {
            Monomial t = new Monomial(r.coeffientAtDegree(r.getDegree()),r.getDegree())/new Monomial(other.coeffientAtDegree(other.getDegree()),other.getDegree());

            q+=t;
            r=r-other*t;
            counter++;
        }

        q.Compress();
        return q;

    }
    public List<Complex> coefficients()
    {
        List<Complex> toReturn = new ArrayList<>();
        for(Monomial monomial : monomials)
        {
            toReturn.add(monomial.coefficient);
        }
        return toReturn;
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
        Polynomial r=this.DeepCopy();
        int counter=0;
        while(!r.isZero()&&r.getDegree()>=other.getDegree())
        {
            Monomial t = new Monomial(r.coeffientAtDegree(r.getDegree()),r.getDegree())/new Monomial(other.coeffientAtDegree(other.getDegree()),other.getDegree());

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
    // GCF of two polynomials
    public Complex plugIn(Complex x)
    {
        if(x==Complex.zero)
        {
            return Complex.zero;
        }
        Complex result=Complex.zero;
        for(Monomial monomial:monomials)
        {
            result+=monomial.coefficient*Complex.pow(x,new Complex(monomial.exponent));
        }
        return result;
    }
    public static Polynomial gcf(Polynomial input,Polynomial other)
    {
        Polynomial a = input.DeepCopy();
        Polynomial b = other.DeepCopy();
        if(a.isZero()) return b;
        if(b.isZero()) return a;
        int i=0;
        while(!b.isZero()&& i < 32)
        {

            Polynomial remainder = a % b;
            a=b;
            b=remainder;
            i++;
        }
        a.Compress();
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

    public Polynomial DeepCopy()
    {
        return new Polynomial(this.monomials);
    }

    public Polynomial derivative()
    {
        Polynomial toReturn=new Polynomial();
        for(Monomial monomial:monomials)
        {
            toReturn+=monomial.derivative();

        }
        return toReturn;
    }
    public List<Complex> solve()
    {
        List<Complex> roots = new ArrayList<>();
        Polynomial copy=this.DeepCopy();
        if(copy.getLowestExponent()<0)
        {
            copy*=new Monomial(Complex.one,-copy.getLowestExponent());

        }
        if(copy.coeffientAtDegree(0)==Complex.zero)
        {
            Polynomial reduced = this/(new Monomial("x"));
            roots.addAll(reduced.solve());
            roots.add(Complex.zero);
        }
        else if(copy.getDegree()==0)
        {
            roots.addAll(MathUtil.solveLinear(Complex.zero,copy.coeffientAtDegree(0)));
        }
        else if(copy.getDegree()==1)
        {
            roots.addAll(MathUtil.solveLinear(copy.coeffientAtDegree(1),copy.coeffientAtDegree(0)));
        } else if (copy.getDegree()==2) {
            roots.addAll(MathUtil.solveQuadratic(copy.coeffientAtDegree(2),copy.coeffientAtDegree(1),copy.coeffientAtDegree(0)));

        } else if (copy.getDegree()==3) {
            roots.addAll(MathUtil.solveCubic(copy.coeffientAtDegree(3),copy.coeffientAtDegree(2),copy.coeffientAtDegree(1),copy.coeffientAtDegree(0)));
        } else if (copy.getDegree()==4) {
            roots.addAll(MathUtil.solveQuartic(copy.coeffientAtDegree(4),copy.coeffientAtDegree(3),copy.coeffientAtDegree(2),copy.coeffientAtDegree(1),copy.coeffientAtDegree(0)));
        }
        else
        {

            Complex root = MathUtil.newtonMethod(this,0.00000001,new Complex(205,22));
            roots.add(root);
            Polynomial toDivide = new Polynomial("x");
            toDivide-=new Polynomial(new Monomial(root));
            Polynomial extra = this/toDivide;
            roots.addAll(extra.solve());
        }
        return roots;

    }


}
