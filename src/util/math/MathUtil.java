package util.math;

import manifold.science.measures.SolidAngle;

import java.awt.event.ComponentListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class MathUtil {
    public static final Complex[] guesses = new Complex[]
            {
                new Complex(5,2),
                new Complex(3,2),
                new Complex(1,5),
                new Complex(205,22),
                new Complex(3,3)
            };
    public static double round(double value, int places) {
        try {
            if(value==Double.POSITIVE_INFINITY)
            {
                value=999999999;
            }
            else if(value==Double.NEGATIVE_INFINITY)
            {
                value=-999999999;
            }
            if (places < 0) throw new IllegalArgumentException();
            BigDecimal bd = BigDecimal.valueOf(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }
        catch(NumberFormatException e)
        {
            throw new RuntimeException(e + "\n"+value+"\n"+places);
        }
    }

    public static boolean isOperator(String token) {
        return "+-*/^".contains(token);
    }
    // Function to find the LCM of the denominators of real and imaginary parts
    public static double findLCMMultiplier(List<Complex> list) {
        BigInteger lcmReal = BigInteger.ONE;
        BigInteger lcmImag = BigInteger.ONE;

        for (Complex num : list) {
            // Get denominators of real and imaginary parts
            BigDecimal realDecimal = BigDecimal.valueOf(num.real);
            BigDecimal imagDecimal = BigDecimal.valueOf(num.imag);
            BigInteger realDenominator = getDenominator(realDecimal);
            BigInteger imagDenominator = getDenominator(imagDecimal);

            // Compute LCM of current real and imaginary denominators
            lcmReal = lcm(lcmReal, realDenominator);
            lcmImag = lcm(lcmImag, imagDenominator);
        }

        // Return the larger LCM to handle both real and imaginary parts
        return lcmReal.max(lcmImag).doubleValue();
    }
    // Function to get the denominator of a BigDecimal (as if it's a fraction)
    public static BigInteger getDenominator(BigDecimal decimal) {
        int scale = decimal.scale(); // Scale gives the number of decimal places
        // 10^scale is the denominator
        return BigInteger.TEN.pow(scale);
    }

    // Function to calculate LCM of two BigInteger numbers
    public static BigInteger lcm(BigInteger a, BigInteger b) {
        return a.multiply(b).divide(a.gcd(b)); // LCM(a, b) = (a * b) / GCD(a, b)
    }
    public static List<Complex> solveLinear(Complex a, Complex b)
    {
        List<Complex> roots=new ArrayList<>();
        roots.add(-b/a);
        return roots;
    }
    public static List<Complex> solveQuadratic(Complex a, Complex b, Complex c)
    {
        List<Complex> roots=new ArrayList<>();
        if(c==Complex.zero)
        {
            roots.addAll(solveLinear(a,b));
            roots.add(Complex.zero);

        }
        else
        {
            Complex discriminant = b * b - 4 * a * c;
            roots.add((Complex.round((-b+Complex.sqrt(discriminant))/(2*a),9)));
            roots.add((Complex.round((-b-Complex.sqrt(discriminant))/(2*a),9)));
        }
        return roots;
    }
    public static List<Complex> solveCubic(Complex a, Complex b, Complex c, Complex d)
    {
        List<Complex> roots=new ArrayList<>();
        List<Complex> rootsOfUnity = Complex.rootsOfUnity(3);
        if(d==Complex.zero)
        {
            roots.addAll(solveQuadratic(a,b,c));
            roots.add(Complex.zero);
        }
        else
        {
            Complex p =(3*a*c-b*b)/(3*a*a);
            Complex q = (2*b*b*b-9*a*b*c+27*a*a*d)/(27*a*a*a);
            Complex discriminant = Complex.sqrt((q/2)*(q/2)+(p/3)*(p/3)*(p/3));
            Complex term1 = Complex.cbrt(-q/2+discriminant);
            Complex term2 = Complex.cbrt(-q/2-discriminant);
            roots.add(Complex.round(term1+term2-b/(3*a),9));
            roots.add(Complex.round(rootsOfUnity[1]*term1+rootsOfUnity[2]*term2-b/(3*a),9));
            roots.add(Complex.round(rootsOfUnity[2]*term1+rootsOfUnity[1]*term2-b/(3*a),9));
        }

        return roots;
    }
    public static List<Complex> solveQuartic(Complex a, Complex b, Complex c, Complex d, Complex e)
    {
        List<Complex> roots=new ArrayList<>();
        if(e==Complex.zero)
        {
            roots.addAll(solveCubic(a,b,c,d));
            roots.add(Complex.zero);
        } else if (b==Complex.zero&&d==Complex.zero) {
            roots = solveQuadratic(Complex.one,c/a,e/a);
            List<Complex> newRoots=new ArrayList<>();
            for(Complex root : roots)
            {
                newRoots.add(Complex.sqrt(root));
                newRoots.add(-Complex.sqrt(root));
            }
            roots.clear();
            roots.addAll(newRoots);
        } else
        {
            Complex p = c/a-(3*b*b)/(8*a*a);
            Complex q = b*b*b/(8*a*a*a)-b*c/(2*a*a)+d/a;
            Complex r = e/a-3*b*b*b*b/(256*a*a*a*a)+b*b*c/(16*a*a)-b*d/(4*a*a);

            if(q==Complex.zero)
            {

                roots = solveQuadratic(Complex.one,p,r);
                List<Complex> newRoots=new ArrayList<>();
                for(Complex root : roots)
                {
                    newRoots.add(Complex.sqrt(root));
                    newRoots.add(-Complex.sqrt(root));
                }
                roots.clear();
                roots.addAll(newRoots);

            }
            else
            {
                List<Complex> resolvent = solveCubic(8*Complex.one,8*p,2*p*p-8*r,-q*q);
                Complex m=Complex.zero;

                for(Complex resoRoot:resolvent)
                {
                    if(!resoRoot.equals(Complex.zero))
                    {
                        m=resoRoot;
                        break;
                    }
                }

                roots=solveQuadratic(Complex.one,Complex.sqrt(2*m),p/2+m-q/(2*Complex.sqrt(2*m)));
                roots.addAll(solveQuadratic(Complex.one,-Complex.sqrt(2*m),p/2+m+q/(2*Complex.sqrt(2*m))));
            }
            List<Complex> newRoots=new ArrayList<>();

            for(Complex root : roots)
            {
                newRoots.add(Complex.round(root-b/(4*a),9));
            }
            roots.clear();
            roots.addAll(newRoots);


        }
        return roots;
    }
    public static Complex newtonMethod(Polynomial p, double tolerance,Complex initialGuess)
    {
        int counter = 0;
        Complex guess=initialGuess;
        while(counter < 1024 && p.plugIn(guess).magnitude()>tolerance)
        {

            Polynomial d = p.derivative();
            Complex slope = d.plugIn(guess);
            if(slope==Complex.zero)
            {
                throw new RuntimeException("Bad Guess: "+initialGuess);


            }
            Complex y = p.plugIn(guess);
            guess=guess-y/slope;

            counter++;

        }
        return Complex.round(guess,9);
    }
    public static boolean isDoubleParsable(String string)
    {
        try {
            Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isPolynomialParsable(String string)
    {
        try{
            new Polynomial(string);
        }
        catch(Exception e)
        {
            return false;
        }
        return true;
    }
    public static Complex BringRadical(Complex a)
    {
        Polynomial toSolve=new Polynomial("x^5+x");
        toSolve+=new Monomial(a);
        return newtonMethod(toSolve,0.0000001,new Complex(205,22));
    }


    public static String addMull(String string)
    {
        StringBuilder toReturn=new StringBuilder();
        for (int i = 0; i <string.length()-1; i++) {
            char c1 = string[i];
            char c2 = string[i+1];
            toReturn.append(c1);
            if(!isOperator(String.valueOf(c1)) && !isOperator(String.valueOf(c2)) && c1!=' ' && c2 !=' ')
            {
                if(c1=='i'||c2=='i')
                {
                    toReturn.append('*');
                }
                else if(c1==')'||c2=='(')
                {
                    toReturn.append('*');
                } else if (Character.isDigit(c1)^Character.isDigit(c2) && c1!='(' && c2 != ')') {
                    toReturn.append("*");
                }
            }

        }
        toReturn.append(string[string.length()-1]);
        return toReturn.toString();
    }
}


