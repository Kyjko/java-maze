package maze;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Cell {
	int s = 20;
	int x, y;
	boolean top=true, bottom=true, right=true, left=true, visited=false;
	boolean current;
	public Cell(int x, int y) {
		this.x=x*s;
		this.y=y*s;
	}
	
	public void render(Graphics g) {
		
		if(current) {
			g.setColor(Color.yellow);
		} else {
			g.setColor(Color.white);
		}
		
		if(bottom) {
			g.drawLine(x, y+s, x+s, y+s);
		}
		if(left) {
			g.drawLine(x, y, x, y+s);
		}
		if(right) {
			g.drawLine(x+s, y, x+s, y+s);
		}
		if(top) {
			g.drawLine(x, y, x+s, y);
		}
		
	}
	
	public void highlight(Graphics g) {
		g.setColor(Color.pink);
		g.fillOval(x, y, s, s);
	}
	
	public Cell checkNbs() {
		ArrayList<Cell>nbs = new ArrayList<Cell>();
		try {
		if(Main.cells[x/s][y/s - 1].visited==false)nbs.add(Main.cells[x/s][y/s - 1]);
		if(Main.cells[x/s+1][y/s].visited==false)nbs.add(Main.cells[x/s+1][y/s]);
		if(Main.cells[x/s-1][y/s].visited==false)nbs.add(Main.cells[x/s-1][y/s]);
		if(Main.cells[x/s][y/s +1].visited==false)nbs.add(Main.cells[x/s][y/s + 1]);
		}catch(Exception ex) {
			
		}
		if(nbs.size() > 0) {
			return nbs.get(new Random().nextInt(nbs.size()));
		} else {
			return null;
		}
	}

}
