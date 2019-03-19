import java.applet.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.math.*;

/*<applet code="Snake" width=600 height=400></applet>*/

public class Snake extends Applet implements KeyListener,Runnable
{
	Thread t;
	Image I;
	Font f1,f2;
	boolean gameover;
	int snake_x[]=new int[100];
	int snake_y[]=new int[100];
	int box_height,box_width;
	int snake_length;
	int tx,ty;
	int choice;
	int score;
	int level;
	int n,food_x,food_y;
	int move_right;
    int move_left;
    int move_up;
    int move_down;


	public void init()
	{
		addKeyListener(this);
		setBackground(Color.WHITE);
		move_right = 0;
        move_left = 0;
        move_up = 0;
		move_down = 0;
		box_height=400;
		box_width=600;
		snake_length = 1;
		food_x=300;
		food_y=190;
		snake_x[0]=snake_y[0]=250;
		for(int i=1;i<snake_length;i++)
		{
			snake_x[i]= snake_x[i-1] - 10;
			snake_y[i]= snake_y[i-1];
		}
		setSize(600, 400);
		I=getImage(getCodeBase(),"images/snake_board.png");
		f1 = new Font("Helvetica",Font.BOLD,40);
		f2 = new Font("Arial",Font.BOLD,25);
		gameover = false;
		set_level();
		repaint();
	}

	public void set_level()
	{
		Scanner sc = new Scanner(System.in);

		System.out.println("Select Your Difficulty:");
		System.out.println("1.Noob");
		System.out.println("2.Normal");
		System.out.println("3.Hard");
		System.out.println("4.Impossible");
		System.out.println("Enter You Choice:");
		choice = sc.nextInt();
		switch(choice)
		{
			case 1: level = 150;
					break;
			case 2: level = 100;
					break;
			case 3: level = 50;
					break;
			case 4: level = 25;
					break;
			default: System.out.println("Plese select the number from the list given ^_^");
		}

	}

	public void start()
	{
		t= new Thread(this);
		t.start();
	}

	void ChangeDirection(int up,int right,int down,int left)
    {
        move_up = up;
        move_right = right;
        move_down = down;
        move_left = left;
	}

	public void keyTyped(KeyEvent ke){}
	public void keyPressed(KeyEvent ke)
	{
		n=ke.getKeyCode();
		switch(n)
		{
			case KeyEvent.VK_UP:
			{
				if(move_down == 0)
				{
					ChangeDirection(1, 0, 0, 0);
					break;
				}
			}
			case KeyEvent.VK_DOWN:
			{
				if(move_up == 0)
				{
					ChangeDirection(0, 0, 1, 0);
					break;
				}
			}
			case KeyEvent.VK_LEFT:
			{
				if(move_right == 0)
				{
					ChangeDirection(0, 0, 0, 1);
					break;
				}
			}
			case KeyEvent.VK_RIGHT:
			{
				if(move_left == 0)
				{
					ChangeDirection(0, 1, 0, 0);
					break;
				}
			}
		}
	}
	public void keyReleased(KeyEvent ke){}

	public void findFood()
    {
		int flag =0;
        food_x = (int)(Math.random() * 329)%1000;
        food_y = (int)(Math.random() * 329)%1000;;
		if(!(food_x%10==0 && food_y%10==0))
		{
			tx=food_x%10;
			ty=food_y%10;
			food_x-=tx;
			food_y-=ty;
		}
		if(!(food_x>65 && food_x<530 && food_y>40 && food_y<360))
		{
			findFood();
		}
		for(int f=0;f<snake_length;f++)
		{
			if(food_x == snake_x[f] && food_y == snake_y[f])
			{
				flag=1;
				break;
			}
		}
		if(flag == 1)
		{
			findFood();
		}
	}


	public void draw_head(Graphics g)
    {
		g.setColor(Color.BLACK);
        g.fillRoundRect(snake_x[0],snake_y[0], 8,8, 2, 2);
        g.setColor(Color.WHITE);
        
        if(move_right == 1)
        {
            g.fillOval(snake_x[0]+4, snake_y[0]+1, 3, 3);
            g.fillOval(snake_x[0]+4, snake_y[0]+4, 3, 3);  
        }
        if(move_left == 1)
        {
            g.fillOval(snake_x[0]+1,snake_y[0]+1, 3, 3);
            g.fillOval(snake_x[0]+1,snake_y[0]+4, 3, 3);
        }
        if(move_up == 1)
        {
            g.fillOval(snake_x[0]+1,snake_y[0]+1, 3, 3);
			g.fillOval(snake_x[0]+4, snake_y[0]+1, 3, 3);
        }
        if(move_down == 1)
        {
			g.fillOval(snake_x[0]+1,snake_y[0]+4, 3, 3);
			g.fillOval(snake_x[0]+4, snake_y[0]+4, 3, 3);
        }
    }
	
	public void draw_snake(Graphics g)
	{
		g.setColor(Color.BLACK);
		for(int i=1;i<snake_length;i++)
		{
			g.fillRoundRect(snake_x[i],snake_y[i],8,8,2,2);   
		}

		for(int i=snake_length-2;i>=0;i--)
		{
			snake_x[i+1]=snake_x[i];
			snake_y[i+1]=snake_y[i];
		}
	}

	public void move_snake()
    {
        if(move_right == 1)
        {
            if(snake_x[0] < box_width)
            {
                snake_x[0]+=10;
            }
        }
        else if(move_left == 1)
        {
            if(snake_x[0] > 0)
            {
                snake_x[0]-=10;
            }
        }
        else if(move_down == 1)
        {
            if(snake_y[0] < box_height)
            {
                snake_y[0]+=10;
            }
        }
        else if(move_up == 1)
        {
            if(snake_y[0] > 0)
            {
                snake_y[0]-=10;
            }
		}

		if(move_down == 1 || move_left == 1 || move_right == 1 || move_up == 1)
		{
		if(!(snake_x[0]>65 && snake_x[0]<530 && snake_y[0]>40 && snake_y[0]<360))
		{
			ChangeDirection(0,0,0,0);
			gameover = true;
		}
		
		for(int g=1;g<snake_length;g++)
		{
			if(snake_x[0] == snake_x[g] && snake_y[0] == snake_y[g])
			{
				ChangeDirection(0,0,0,0);
				gameover = true;
			}
		}
		}
    }

	public void draw_food(Graphics g)
	{
		g.setColor(Color.RED);
		g.fillOval(food_x, food_y, 8,8);
	}

	public void paint(Graphics g)
	{
		g.drawImage(I,0,0,this);
		if(!(gameover))
		{
			this.draw_head(g);
			this.draw_snake(g);
			this.draw_food(g);
		}
		this.move_snake();		

		if(snake_x[0]==food_x && snake_y[0]==food_y)
		{
			findFood();
			snake_length++;
			score = snake_length*10;
			showStatus("Score: " +score);
		}

		if(gameover)
		{
			g.setFont(f1);
			g.setColor(Color.BLACK);
			g.drawString("GAME OVER", 195, 170);
			g.setFont(f2);
			g.drawString("Score:"+score ,250, 195);
		}
	}

	public void run()
	{
		while(!(gameover))
		{
			try
			{
				Thread.sleep(level);
				repaint();
			}
			catch(InterruptedException e){}
		}
	}
}