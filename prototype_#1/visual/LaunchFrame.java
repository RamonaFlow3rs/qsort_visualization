package visual;

import core.SortAlgorithm;
import javafx.scene.control.Slider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



final class LaunchFrame extends JFrame implements ActionListener {
	
	/*---- Fields ----*/
	
	private final List<SortAlgorithm> algorithms;
	
	private final JTextField arraySizeInput;
	private final JTextField scaleInput;

	
	private final Choice algorithmInput;
	private final JButton runButton;
	private final JSlider speedSlider;
	
	
	/*---- Constructor ----*/
	
	public LaunchFrame(List<SortAlgorithm> algos) {




		super("Визуализация Quick Sort");
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});


		this.setSize(500, 200);
		this.setResizable(false);
		setLocationRelativeTo(null);

		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(gbl);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 0;
		gbc.ipady = 0;
		gbc.insets = new Insets(4, 4, 4, 4);
		gbc.weighty = 0;
		
		
		/*-- First column --*/
		gbc.gridx = 0;
		gbc.weightx = 1;
		

		{
			JLabel label = new JLabel("Алгоритм:");
			gbc.gridy = 0;
			gbl.setConstraints(label, gbc);
			this.add(label);
			
			label = new JLabel("Размер массива:");
			gbc.gridy = 1;
			gbl.setConstraints(label, gbc);
			this.add(label);
			
			label = new JLabel("Масштаб:");
			gbc.gridy = 2;
			gbl.setConstraints(label, gbc);
			this.add(label);
			
			label = new JLabel("Скорость:");
			gbc.gridy = 3;
			gbl.setConstraints(label, gbc);
			this.add(label);
		}
		
		
		/*-- Second column --*/
		gbc.gridx = 1;
		gbc.weightx = 2;
		

		algorithms = new ArrayList<>(algos);
		algorithmInput = new Choice();
		for (SortAlgorithm algo : algos)
			algorithmInput.add(algo.getName());
		gbc.gridy = 0;
		gbl.setConstraints(algorithmInput, gbc);
		this.add(algorithmInput);
		

		arraySizeInput = new JTextField("30");
		arraySizeInput.addActionListener(this);
		gbc.gridy = 1;
		gbl.setConstraints(arraySizeInput, gbc);
		this.add(arraySizeInput);
		

		scaleInput = new JTextField("12");
		scaleInput.addActionListener(this);
		gbc.gridy = 2;
		gbl.setConstraints(scaleInput, gbc);
		this.add(scaleInput);


		speedSlider = new JSlider();
		speedSlider.setMinimum(1);
		speedSlider.setMaximum(1000);
		gbc.gridy = 3;
		gbl.setConstraints(speedSlider, gbc);
		this.add(speedSlider);



		runButton = new JButton("Run");
		runButton.addActionListener(this);
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.weighty = 1;
		gbl.setConstraints(runButton, gbc);
		this.add(runButton);
		

		//this.pack();
		Rectangle rect = getGraphicsConfiguration().getBounds();
		this.setLocation(
			(rect.width - this.getWidth()) / 2,
			(rect.height - this.getHeight()) / 3);
		this.setVisible(true);
	}



	/*---- Methods ----*/

	public void actionPerformed(ActionEvent ev) {

		int size, scale, speed;

		try {
			size = Integer.parseInt(arraySizeInput.getText());
			scale = Integer.parseInt(scaleInput.getText());
			speed = speedSlider.getValue();
		} catch (NumberFormatException e) {
			return;
		}
		if (size <= 0 || scale <= 0 || speed <= 0 || Double.isInfinite(speed) || Double.isNaN(speed))
			return;
		

		final VisualSortArray array = new VisualSortArray(size, scale, speed);
		final SortAlgorithm algorithm = algorithms.get(algorithmInput.getSelectedIndex());

		new Thread() {
			public Thread thread = this;
			
			public void run() {
				initFrame();
			}
			
			private void initFrame() {

				final Frame sortFrame = new Frame(algorithm.getName());
				sortFrame.add(array.canvas);
				sortFrame.setResizable(false);
				sortFrame.pack();


				sortFrame.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						thread.interrupt();
						sortFrame.dispose();
					}
				});


				Rectangle rect = getGraphicsConfiguration().getBounds();
				sortFrame.setLocation(
					(rect.width - sortFrame.getWidth()) / 8,
					(rect.height - sortFrame.getHeight()) / 8);
				sortFrame.setVisible(true);
			}

		}.start();
	}

}
