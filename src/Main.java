import util.math.Complex;
import util.math.Monomial;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

//        String latex = reader.readLine();
//        TeXFormula formula = new TeXFormula(latex);
//        TeXParser parser = new TeXParser(latex,formula);
//
//        System.out.println(parser.);

//        System.out.print("Path:");
//        String path = reader.readLine();
//        TeXFormula formula = new TeXFormula(latex);
//        TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY,40);
//        BufferedImage bimg = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
//
//        Graphics2D g2d = bimg.createGraphics();
//        g2d.setColor(Color.white);
//        g2d.fillRect(0,0,icon.getIconWidth(),icon.getIconHeight());
//        JLabel jl = new JLabel();
//        jl.setForeground(new Color(0, 0, 0));
//        icon.paintIcon(jl, g2d, 0, 0);
//
//        File out = new File(path);
//        ImageIO.write(bimg, "png", out);
        Complex i = new Complex(0.0,1.0);
        Complex power = new Complex(7,0.0);
        Complex result = Complex.pow(i,i);
        System.out.println(result);
        Monomial monomial = new Monomial("blah x^3");
    }


}