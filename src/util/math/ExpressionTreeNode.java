package util.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ExpressionTreeNode {
    public String value;
    public Rational poly;
    public ExpressionTreeNode left, right;
    public boolean checked;
    public ExpressionTreeNode(String value) {
        this.value = value;
        this.left = this.right = null;
    }
    public ExpressionTreeNode(Rational poly) {
        this.poly = poly;
        this.left = this.right = null;
    }
    public List<ExpressionTreeNode> lastBranches()
    {
        Stack<ExpressionTreeNode> nodeStack=new Stack<>();
        ArrayList<ExpressionTreeNode> toReturn=new ArrayList<>();
        while (!nodeStack.isEmpty)
        {
            ExpressionTreeNode node = nodeStack.pop();
            if(node.left!=null && node.right !=null)
            {
                if(node.left.left==null && node.left.right == null && node.right.left==null&&node.right.right==null)
                {
                    toReturn.add(node);
                }
            }
            else
            {
                toReturn.add(node);
            }

            if(node.left!=null){
                nodeStack.push(left);
            }
            if(node.right!=null)
            {
                nodeStack.push(right);
            }
        }
        return toReturn;
    }
    public String toString()
    {
        if(this.left==null && this.right==null)
        {
            return this.value;
        }
        else
        {
            return "("+left.toString()+")"+this.value+"("+right.toString()+")";
        }

    }

    public Rational eval()
    {

        if(left==null||right==null)
        {


            return new Rational(new Polynomial(value));
        }
        else
        {



            return switch (value) {
                case "+" -> left.eval() + right.eval();
                case "-" -> left.eval() - right.eval();
                case "*" -> left.eval() * right.eval();
                case "/" -> left.eval() / right.eval();
                case "^" -> Rational.pow(left.eval(), right.eval());
                default -> throw new RuntimeException("Not an Operator or Leaf");
            };

        }

    }

}