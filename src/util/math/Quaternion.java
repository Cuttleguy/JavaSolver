package util.math;

public class Quaternion extends Complex{
    public double jmag;
    public double kmag;
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

    }



}
