package numeredBorder;

import javax.swing.*;
import javax.swing.border.AbstractBorder;

import java.awt.*;

public class NumeredBorder extends AbstractBorder {  
    /**
	 * 
	 */
	private static final long serialVersionUID = 8686493396246189633L;
	private int lineHeight = 16;  
    private int characterHeight = 8;  
    private int characterWidth = 7;  
    private Color myColor;  
    private JViewport viewport;  
      
    public NumeredBorder() {  
        this.myColor = new Color(164, 164, 164);  
    }  
      
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {  
        if (this.viewport == null) {  
            searchViewport(c);  
        }  
          
        Point point;  
        if (this.viewport != null) {  
            point = this.viewport.getViewPosition();              
        } else {  
            point = new Point();  
        }  
          
        Color oldColor = g.getColor();  
        g.setColor(this.myColor);  
          
        double r = (double) height / (double) this.lineHeight;  
        int rows = (int) (r + 0.5);  
        String str = String.valueOf(rows);  
        int maxLenght = str.length();  
          
        int py;  
        int i = 0;  
        if (point.y > 0) {  
            i = point.y / this.lineHeight;  
        }  
          
        int lenght;  
        int px;  
        for( ; i < rows; i++) {
        	if(i<10)
        		str = String.valueOf("0"+i);
        	else
        		str = String.valueOf(+i);
            lenght = str.length();  
            lenght = maxLenght - lenght;  
              
            py = this.lineHeight * i + 14;  
            px = this.characterWidth * lenght + 2;  
            px += point.x;  
            g.drawString(str, px, py);  
        }         
          
        int left = this.calculateLeft(height) + 7;  
        left += point.x;  
        g.drawLine(left, 0, left, height);  
          
        g.setColor(oldColor);  
    }  
      
    public Insets getBorderInsets(Component c) {  
        int left = this.calculateLeft(c.getHeight()) + 10;  
        return new Insets(1, left, 1, 1);  
    }  
      
    public Insets getBorderInsets(Component c, Insets insets) {           
        insets.top = 1;  
        insets.left = this.calculateLeft(c.getHeight()) + 10;  
        insets.bottom = 1;  
        insets.right = 1;  
        return insets;  
    }  
      
    protected int calculateLeft(int height) {  
        double r = (double) height / (double) this.lineHeight;  
        int rows = (int) (r + 0.5);  
        String str = String.valueOf(rows);  
        int lenght = str.length();  
  
        return this.characterHeight * lenght;  
    }  
      
    protected void searchViewport(Component c) {  
        Container parent = c.getParent();  
        if (parent instanceof JViewport) {  
            this.viewport = (JViewport) parent;  
        }  
    }  
}  