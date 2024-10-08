package util.math;
import java.util.*;
public class ExpressionTree {
    public static ExpressionTreeNode buildTree(String postfix) {
        Stack<ExpressionTreeNode> nodeStack = new Stack<>();
        StringBuilder tokenBuilder = new StringBuilder();
        for (int i = 0; i < postfix.length(); i++) {
            char c = postfix[i];
            if(MathUtil.isOperator(String.valueOf(c)))
            {
                if(!tokenBuilder.isEmpty())
                {
                    nodeStack.push(new ExpressionTreeNode(tokenBuilder.toString()));
                    tokenBuilder=new StringBuilder();
                }


                ExpressionTreeNode op2 = nodeStack.pop();


                ExpressionTreeNode op1 = nodeStack.pop();

                ExpressionTreeNode op = new ExpressionTreeNode(String.valueOf(c));
                op.left=op1;
                op.right=op2;

                nodeStack.push(op);


            }
            else if (c==' ') {
                if(!tokenBuilder.isEmpty)
                {
                    nodeStack.push(new ExpressionTreeNode(tokenBuilder.toString()));
                    tokenBuilder=new StringBuilder();
                }

            }
            else
            {

                tokenBuilder.append(c);
            }

        }
        return nodeStack.peek();

    }


}
