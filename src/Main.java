import util.math.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(new Polynomial("3x^2"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        String input = reader.readLine();
        String postfix = InfixToPostfix.infixToPostfix(addMull(input));
        System.out.println(postfix);
        ExpressionTreeNode root = ExpressionTree.buildTree(postfix);
        System.out.println(root.eval().numerator);





    }
    public static String addMull(String string)
    {
        StringBuilder toReturn=new StringBuilder();
        for (int i = 0; i <string.length()-1; i++) {
            char c1 = string[i];
            char c2 = string[i+1];
            toReturn.append(c1);
            if(!MathUtil.isOperator(String.valueOf(c1)) && !MathUtil.isOperator(String.valueOf(c2)))
            {
                if(c1==')'||c2=='(')
                {
                    toReturn.append('*');
                }
            }

        }
        toReturn.append(string[string.length()-1]);
        return toReturn.toString();
    }


}