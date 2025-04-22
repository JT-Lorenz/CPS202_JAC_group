package teamExercises;

import java.awt.*;
import java.awt.event.*;
import java.util.Collections;

import javax.swing.*;

public class TeamExercise4b extends JFrame implements ActionListener
{
	static int height;
	static int width;
	static Container gameFrame;
	static JPanel mainPanel;
	public TeamExercise4b()
	{
		this.setSize(500, 400);
		gameFrame = getContentPane();
		gameFrame.setLayout(new BorderLayout());
		
		makeBoard(4);
		
		makeMenu();
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void makeBoard(int hw)
	{
		JButton [][] buttonArray = new JButton[hw][hw];
		mainPanel = new JPanel();
		height = hw;
		width = hw;
		mainPanel.setLayout(new GridLayout(height, width));
		gameFrame.add(mainPanel, BorderLayout.CENTER);
		for(int i = 0; i<height; i++)
		{
			for(int j = 0; j<width; j++)
			{
				int buttonNum = hw-j + hw* (hw-i-1)-1;
				String buttonText = buttonNum + "";
				buttonArray[i][j] = new JButton(buttonText);
				mainPanel.add(buttonArray[i][j]);
				buttonArray[i][j].addActionListener(this);
			}
		}
		
	}
	
	public void makeMenu()
	{
		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new GridLayout(15, 1));
		gameFrame.add(sidePanel, BorderLayout.EAST);
		
		JRadioButton x3 = new JRadioButton("3x3 Game");
		JRadioButton x4 = new JRadioButton("4x4 Game");
		JRadioButton x5 = new JRadioButton("5x5 Game");
		JRadioButton x6 = new JRadioButton("6x6 Game");
		JButton sO = new JButton("Start Over");
		
		sidePanel.add(x3);
		sidePanel.add(x4);
		sidePanel.add(x5);
		sidePanel.add(x6);
		sidePanel.add(sO);
		
		x3.addActionListener(this);
		x4.addActionListener(this);
		x5.addActionListener(this);
		x6.addActionListener(this);
		sO.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) 
	{
		
	}
	
	
	
}
