//(C) Alfred Hermes & Barbara Leipolz-Schumacher //
import java.awt.*;
import java.awt.event.*;
class Turtle
{
    protected double posX, posY;
    protected double winkel;
    protected Color farbe=Color.black;
    protected Container c;
    protected double urX, urY;
    protected boolean stiftUnten;
    protected Graphics g;

    protected Button b=new Button("Löschen");
    protected Button vorB, drehB, hochB, geheNachB, loeschB, aktualisierB;
    protected TextField vorTF, drehTF, geheZuXTF, geheZuYTF;
    protected Choice farbauswahl;
    protected Checkbox stiftCB;            

    public Turtle(Container cont){
        this(cont, 0, 0, 0);
    }

    public Turtle(Container cont, double x, double y, double richtung){
        posX=x; posY=y; winkel=richtung; 
        urX=x; urY=y; 
        c=cont;
        g=c.getGraphics();
        stiftUnten = true;
    }

    public void zumAnfang(){
        posX=urX; posY=urY;
    }

    public void vor(double l){
        g.setColor(farbe);
        double neuX = posX + Math.cos(bogen(winkel))*l;    
        double neuY = posY + Math.sin(bogen(winkel))*l; 
        if (stiftUnten) g.drawLine((int) posX, (int) posY, (int) neuX, (int) neuY);
        posX = neuX; posY = neuY;
    }

    public void drehe(double grad){
        winkel += grad;
    }

    public void fuelleKreis(double radius){
        int r = (int) (radius+0.5);
        g.setColor(farbe);
        g.fillOval((int) (posX -radius), (int) (posY-radius), 2*r, 2*r);    
    }

    public void loesche(){
        zumAnfang();
        winkel=0;
        int x=c.getBounds().width;
        int y=c.getBounds().height;
        g.clearRect(0, 0, x, y);

    }

    public void inRichtung(double grad){
        winkel = grad;
    }

    public void geheZu(double neuX,double neuY){
        g.setColor(farbe);
        if (stiftUnten) g.drawLine((int) posX, (int) posY, (int) neuX, (int) neuY);
        posX = neuX; posY = neuY;
    }

    public void stiftfarbe(Color c){
        farbe = c;
    }

    public boolean stiftHoch(){
        stiftUnten=false;
        return false;
    }

    public boolean stiftAb(){
        stiftUnten=true;
        return true;
    }

    public double liesRichtung (){
        return winkel;
    }

    public double liesX (){
        return posX;
    }

    public double liesY (){
        return posY;
    }

    public Color liesFarbe (){
        return (Color)farbe;
    }

    private double bogen(double winkel){
        return winkel*Math.PI/180; 
    }

    public void interaktiv(){
        aktualisierB=new Button("Aktualisieren");
        Label la=new Label(); 
        vorB=new Button("vor:");  vorTF=new TextField(4); vorTF.setText("0");
        drehB=new Button("drehe:"); drehTF=new TextField(4);drehTF.setText("0");
        geheNachB=new Button("geheZu:"); 
        geheZuXTF=new TextField(4);geheZuXTF.setText(String.valueOf(urX));
        geheZuYTF=new TextField(4);geheZuYTF.setText(String.valueOf(urY));
        stiftCB=new Checkbox("StiftAb"); 
        stiftCB.setState(true);
        loeschB=new Button("loesche");
        farbauswahl = new Choice();
        farbauswahl.addItem("Farbauswahl");
        farbauswahl.addItem("rot/red");
        farbauswahl.addItem("gelb/yellow");
        farbauswahl.addItem("gruen/green");
        farbauswahl.addItem("blau/blue");
        farbauswahl.addItem("orange");
        farbauswahl.addItem("schwarz/black");
        farbauswahl.addItem("weiss/white");

        farbauswahl.addItemListener(new Farbabfrage());

        Aktionswaechter aw = new Aktionswaechter();
        loeschB.addActionListener(aw);
        vorB.addActionListener(aw);
        geheNachB.addActionListener(aw);
        drehB.addActionListener(aw);
        aktualisierB.addActionListener(aw);

        stiftCB.addItemListener(new ItemListener(){
                public void itemStateChanged(ItemEvent e){
                    stiftUnten = stiftCB.getState(); 
                }
            });

        c.add (aktualisierB);
        c.add (vorB); c.add(vorTF);
        c.add (drehB); c.add(drehTF);
        c.add(geheNachB); c.add(geheZuXTF);c.add(geheZuYTF);
        c.add (stiftCB);
        c.add (farbauswahl);
        c.add (loeschB); 
    }

    class Farbabfrage implements ItemListener {
        public void itemStateChanged(ItemEvent e){
            int f = farbauswahl.getSelectedIndex();
            switch (f) { 
                case 0: break;
                case 1: stiftfarbe(Color.red); break;
                case 2: stiftfarbe(Color.yellow); break;
                case 3: stiftfarbe(Color.green); break;
                case 4: stiftfarbe(Color.blue); break;
                case 5: stiftfarbe(Color.orange); break;
                case 6: stiftfarbe(Color.black); break;
                case 7: stiftfarbe(Color.white); break;
            } // switch
        }
    }//Farbabfrage.class

    class Aktionswaechter implements ActionListener {
        public void actionPerformed(ActionEvent e){
            Object quelle = e.getSource();
            if (quelle == loeschB) {
                loesche(); 
            }
            else if (quelle == vorB) {
                double v = Double.valueOf(vorTF.getText()).doubleValue();
                vor(v);
            }
            else if (quelle == geheNachB) {
                double x = Double.valueOf(geheZuXTF.getText()).doubleValue();
                double y = Double.valueOf(geheZuYTF.getText()).doubleValue();
                geheZu(x,y);
            }
            else if (quelle == drehB) {
                double w = Double.valueOf(drehTF.getText()).doubleValue();
                drehe(w);
            }
            else if (quelle == aktualisierB) {
                geheZuXTF.setText(String.valueOf((int)posX));
                geheZuYTF.setText(String.valueOf((int)posY));
                drehTF.setText(String.valueOf((int)winkel));
                stiftCB.setState(stiftUnten);
                int k=0;
                if(farbe.equals(Color.red)) k=1;
                else if (farbe.equals(Color.yellow)) k=2;
                else if (farbe.equals(Color.green)) k=3;
                else if (farbe.equals(Color.blue)) k=4;
                else if (farbe.equals(Color.orange)) k=5;
                else if (farbe.equals(Color.black)) k=6;
                else if (farbe.equals(Color.white)) k=7;
                farbauswahl.select(k);
            }
        }
    }//Aktionswaechter.class

}//Turtle.class

