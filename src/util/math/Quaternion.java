package util.math;

public class Quaternion extends Complex{
    public double jmag;
    public double kmag;
    public Quaternion(double newReal) {
        this.real = newReal;
        this.imag = 0.0;
    }
    public Quaternion() {
        this.real = 0.0;
        this.imag = 0.0;
    }

    public Quaternion(double newReal, double newImag) {
        this.real = newReal;
        this.imag = newImag;
    }
    public Quaternion(double newReal,double newImag, double newJmag, double newKmag)
    {
        real=newReal;
        imag=newImag;
        jmag=newJmag;
        kmag=newKmag;
    }

    @Override
    public double magnitude()
    {
        return Math.sqrt(real*real+imag*imag+jmag*jmag+kmag*kmag);
    }
    @Override
    public Quaternion conjugate()
    {
        return new Quaternion(
                real,
                -imag,
                -jmag,
                -kmag
        );
    }
    public Quaternion invert()
    {
        return conjugate()/(magnitude()*magnitude());
    }


    public Quaternion plus(Quaternion other)
    {
        return new Quaternion(
                real+other.real,
                imag+other.imag,
                jmag+other.jmag,
                kmag+other.kmag
        );
    }
    public Quaternion minus(Quaternion other)
    {
        return new Quaternion(
                real-other.real,
                imag-other.imag,
                jmag-other.jmag,
                kmag-other.kmag
        );
    }
    public Quaternion times(Quaternion other)
    {
        return new Quaternion(
                real*other.real-imag*other.imag-jmag*other.jmag-kmag*other.kmag,
                real*other.imag+imag*other.real+imag*other.jmag-jmag*other.imag,
                real*other.jmag-imag*other.kmag+jmag*other.real+kmag*other.imag,
                real*other.kmag+imag*other.jmag-jmag*other.imag+kmag*other.real
        );
    }
    public Quaternion div(double other)
    {
        return new Quaternion(
                real/other,
                imag/other,
                jmag/other,
                kmag/other
        );
    }
    public Quaternion div(Quaternion other)
    {
        return this*other.invert();
    }




}
