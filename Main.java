package maze;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import java.util.ArrayList;


import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Main extends Canvas implements Runnable{
    public static int w, h;
    boolean running=false;
    static int frameCount=-500;
    float zoom=1;
    int s = 20;
    static Cell[][]cells;
    
    ArrayList<Cell>stack = new ArrayList<Cell>();
  
    Cell current;
    
    Thread t;
    public Main() {
        JFrame f = new JFrame("Útvesztö generátor");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        w=dim.width/2;
        h=dim.height/2;
      
        cells=new Cell[w/s][h/s];
        
        f.setSize(w, h);
        f.setLocationRelativeTo(null);
        f.add(this);
        f.setVisible(true);
        f.setResizable(false);
        f.setFocusable(true);
        f.requestFocus();
        start();
       
        for(int i = 0; i < cells.length; i++) {
        	for(int j = 0; j < cells[0].length; j++) {
        		cells[i][j] = new Cell(i, j);
        	}
        }
        
        current=cells[1][1];
     
    }
   
    public synchronized void stop(Thread t) {
        try {
            t.join();
        }catch(InterruptedException e) {
           
        }
        running=false;
    }
   
    public void run() {
        while(running) {
            try {
				render();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
   
    public synchronized void start() {
    	running=true;
        t = new Thread(this);
        t.start();
        
    }
   
    public void render() throws InterruptedException {
    	
        BufferStrategy bs = this.getBufferStrategy();
        if(bs==null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
       
        g.setColor(Color.black);
        g.fillRect(0, 0, w, h);

        for(int i = 0; i < cells.length; i++) {
        	for(int j = 0; j < cells[0].length; j++) {
        		cells[i][j].render(g);
        	}
        }
        
        current.visited=true;
        
        current.highlight(g);
        
        current.current=true;
        Cell next = current.checkNbs();
        if(next!=null) {
        	next.visited=true;
        	next.current=true;
        	current.current=false;
        	stack.add(current);
        	removeWalls(current, next);
        	current=next;
        } else if(stack.size()>0){
        	Cell cell = stack.remove(stack.size()-1);
        	current=cell;
        }
        
        Thread.sleep(5);
        
        g.dispose();
        bs.show();
        
       
        
     
    }
    
    public void removeWalls(Cell a, Cell b) {
    	if(a.x/s - b.x/s == 1) {
    		a.left=false;
    		b.right=false;
    	} else if(a.x/s - b.x/s == -1) {
    		a.right=false;
    		b.left=false;
    	}
    	
    	if(a.y/s - b.y/s == 1) {
    		a.top=false;
    		b.bottom=false;
    	} else if(a.y/s - b.y/s == -1) {
    		a.bottom=false;
    		b.top=false;
    	}
    }
   
    public static void main(String[] args) {
        new Main();
    }
    
}