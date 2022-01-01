public class ComplexNumber {
    double real_partial;
    double imaginary_partial;

    public ComplexNumber(double real, double img) {
        real_partial = real;
        imaginary_partial = img;
    }

    public double getRealPartial() {
        return real_partial;
    }

    public double getImaginaryPartial() {
        return imaginary_partial;
    }

    public void setRealPartial(double nb) {
        real_partial = nb;
    }

    public void setImaginaryPartial(double nb) {
        imaginary_partial = nb;
    }
}