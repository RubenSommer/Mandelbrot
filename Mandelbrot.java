import java.awt.*;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame{
    private Turtle t;
    public int width = 200;
    public int height = 200;

    public Mandelbrot() {
        this.setTitle("Haus von Nikolaus");
        this.setPreferredSize(new Dimension(width, height));
        this.pack();
        this.setVisible(true);
    }

    public ComplexNumber squareComplexNumber(ComplexNumber nb) {
        double real = nb.getRealPartial()*nb.getRealPartial();
        double img = nb.getImaginaryPartial()*nb.getImaginaryPartial();
        ComplexNumber cnb = new ComplexNumber(real, img);
        return cnb;
    }

    public ComplexNumber addComplexNumbers(ComplexNumber nb1, ComplexNumber nb2) {
        double real = nb1.getRealPartial()+nb2.getRealPartial();
        double img = nb1.getImaginaryPartial()+nb2.getImaginaryPartial();
        ComplexNumber nb = new ComplexNumber(real,img);
        return nb;
    }

    public Boolean checkIfMandelbrot(ComplexNumber cInput, ComplexNumber zInput) {
        ComplexNumber c = cInput;
        ComplexNumber z = zInput;
        ComplexNumber zNew = new ComplexNumber(0,0);
        ComplexNumber result = new ComplexNumber(0,0);

        for(int i=0; i<100; i++) {
            zNew = squareComplexNumber(z);
            result = addComplexNumbers(zNew,c);
            //System.out.println("result: "+ z.getRealPartial()+ " " + z.getImaginaryPartial());
            z = result;
        }
        if(z.getImaginaryPartial() <2 && z.getImaginaryPartial() >-2 && z.getRealPartial() <2 && z.getRealPartial() > -2) {
            System.out.println("gehoert zur Mandelbrotmenge");
            return true;
        }
        else {
            System.out.println("gehoert nicht zur Mandelbrotmenge");
            return false;
        }
    }

    public void paint(Graphics g) {
        t=new Turtle(this,0, 0, 0);
        t.stiftfarbe(Color.red);
        ComplexNumber nb = new ComplexNumber(0,0);
        ComplexNumber z = new ComplexNumber(0,0);

        for(int i=0; i<width; i++) {  
            for(int j =0; j<height;  j++) {

                nb.setRealPartial(i/100);
                nb.setImaginaryPartial(j/100);
                if(checkIfMandelbrot(nb, z) == true) {
                    g.drawLine(i, j, i, j);
                }
                else {
                    System.out.println("false");
                }
            }
        }
    }

    public int iterZahl(final double cx, final double cy, int maxIt) {
        // bestimmt Anzahl der Iterationen
        int zaehler = 0;
        double zx = 0.0, zy = 0.0, tmp;
        do {
            tmp = zx*zx - zy*zy + cx;
            zy = 2*zx*zy + cy;
            zx = tmp;
            zaehler = zaehler + 1;
        } while (zx*zx + zy*zy <= 4.0 && zaehler < maxIt);
        return zaehler;
    }
}