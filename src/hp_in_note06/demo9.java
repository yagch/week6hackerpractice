package hp_in_note06;
import javax.swing.*;
import java.awt.*;
public class demo9 extends JFrame{
Mypanel mypanel=null;
public static void main(String args[]){
demo9 a = new demo9();
}
public demo9(){
mypanel = new Mypanel();
this.add(mypanel);
this.setSize(400,400);
this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
this.setVisible(true);
}
class Mypanel extends JPanel{
public void paint(Graphics g){
g.drawOval(30, 30, 50, 50);
}
}
}