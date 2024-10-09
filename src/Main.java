import util.math.*;

import java.util.Comparator;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {


        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //x^5-(6+19i)*x^4-(131-114i)*x^3+(786+389i)*x^2+(420-2334i)*x-2520
//        System.out.println(MathUtil.newtonMethod(new Polynomial("x^5-25x^4+245x^3-1175x^2+2754x-2520"),0.000000001,new Complex(205,22)));

        System.out.print("Input Equation: ");
        String input = reader.readLine();
        String[] sides = input.split("=");
        Rational simp1;
        if(MathUtil.isPolynomialParsable(sides[0]))
        {
            simp1=new Rational(new Polynomial(sides[0]));
        }
        else
        {
            String postfix1 = InfixToPostfix.infixToPostfix(MathUtil.addMull(sides[0]));

            ExpressionTreeNode root1 = ExpressionTree.buildTree(postfix1);

            simp1=root1.eval();
        }
        Rational simp2;
        if(sides.length>1) {



            if (MathUtil.isPolynomialParsable(sides[1]))
                simp2 = new Rational(new Polynomial(sides[1]));
            else {
                String postfix2 = InfixToPostfix.infixToPostfix(MathUtil.addMull(sides[1]));

                ExpressionTreeNode root2 = ExpressionTree.buildTree(postfix2);

                simp2 = root2.eval();

            }
        }
        else
        {
            simp2=new Rational(new Polynomial("0"));
        }


        Rational oneSide= simp1-simp2;
        System.out.println("Simplified Equation: "+oneSide+" = 0");
        List<Complex> roots = oneSide.solve();
        roots.sort(new Comparator<Complex>() {
            @Override
            public int compare(Complex o1, Complex o2) {
                if(o1>o2)
                {
                    return 1;
                } else if (o1==o2) {
                    return 0;
                } else{
                    return -1;

                }
            }
        });
        for(Complex root : roots)
        {
            System.out.println("Root: "+root);
        }



    }


}