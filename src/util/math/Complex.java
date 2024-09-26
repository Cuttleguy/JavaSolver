package util.math;

public class Complex {
    public double real;
    public double imag;
    public Complex(double newReal) {
        this.real = newReal;
        this.imag = 0.0;
    }
    public Complex() {
        this.real = 0.0;
        this.imag = 0.0;
    }

    public Complex(double newReal, double newImag) {
        this.real = newReal;
        this.imag = newImag;
    }

    public Complex plus(Complex other) {
        return new Complex(this.real + other.real, this.imag + other.imag);
    }

    public Complex minus(Complex other) {
        return new Complex(this.real - other.real, this.imag - other.imag);
    }

    public Complex times(Complex other) {
        return new Complex(
                this.real * other.real - this.imag * other.imag,
                this.real * other.imag + this.imag * other.real
        );
    }

    public Complex div(double other) {
        if (other == 0) throw new ArithmeticException("Division by zero");
        return new Complex(this.real / other, this.imag / other);
    }

    public Complex div(Complex other) {
        double denominator = other.real * other.real + other.imag * other.imag;
        if (denominator == 0) throw new ArithmeticException("Division by zero");
        return this.times(new Complex(other.real / denominator, -other.imag / denominator));
    }

    public double magnitude() {
        return Math.sqrt(real * real + imag * imag);
    }
    public double argument()
    {
        return Math.atan2(imag,real);
    }
    public static Complex pow(Complex a, Complex b)
    {
        double mag = a.magnitude();
        double arg = a.argument();
        double log_mag=Math.log(mag);
        Complex log_a=new Complex(log_mag,arg);
        Complex b_log_a=log_a*b;
        double e_real=Math.exp(b_log_a.real);
        return round(new Complex(e_real*Math.cos(b_log_a.imag),e_real*Math.sin(b_log_a.imag)),10);
    }
    public static Complex round(Complex c,int digits)
    {
        return new Complex(MathUtil.round(c.real,digits),MathUtil.round(c.imag,digits));
    }
    public boolean equals(Complex other)
    {
        return real==other.real && imag==other.imag;
    }


    public String toString(){
        if(imag == 0.0)
        {
            return Double.toString(real);
        } else if (real==0.0) {
            return Double.toString(imag)+"i";
        } else if (imag==1.0) {
            return Double.toString(real)+" + i";
        }
        else if(imag==-1.0)
        {
            return Double.toString(real)+" - i";
        } else if (imag<0.0) {
            return Double.toString(real)+" - "+Double.toString(Math.abs(imag))+"i";
        } else {
            return Double.toString(real)+" + "+Double.toString(imag)+"i";
        }

    }
}
