package visual;

import core.SortAlgorithm;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


final class LaunchFrame extends JFrame implements ActionListener {
	
	/*---- Fields ----*/
	
	private final List<SortAlgorithm> algorithms;
	
	private final JTextField arraySizeInput;
	private final JTextField scaleInput;

	
	private final Choice algorithmInput;
	private final JSlider speedSlider;

	private final JTextField arrayField;
	
	/*---- Constructor ----*/
	
	LaunchFrame(List<SortAlgorithm> algos) {

		super("Визуализация Quick Sort");
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});


		this.setPreferredSize(new Dimension(500,230));
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

			label = new JLabel("Ввод массива:");
			gbc.gridy = 4;
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
		

		arraySizeInput = new JTextField("300");
		arraySizeInput.addActionListener(this);
		gbc.gridy = 1;
		gbl.setConstraints(arraySizeInput, gbc);
		this.add(arraySizeInput);
		

		scaleInput = new JTextField("3");
		scaleInput.addActionListener(this);
		gbc.gridy = 2;
		gbl.setConstraints(scaleInput, gbc);
		this.add(scaleInput);


		speedSlider = new JSlider();
		speedSlider.setMinimum(1);
		speedSlider.setMaximum(1000);
		speedSlider.setValue(100);
		gbc.gridy = 3;
		gbl.setConstraints(speedSlider, gbc);
		this.add(speedSlider);

		arrayField = new JTextField();
		arrayField.setSize(200, 10);
		gbc.gridy = 4;
		gbl.setConstraints(arrayField, gbc);
		this.add(arrayField);

		JButton runButton = new JButton("Run");
		runButton.addActionListener(this);
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 2;
		gbc.weighty = 1;
		gbl.setConstraints(runButton, gbc);
		this.add(runButton);


		JButton fileButton = new JButton("Считать из файла");
		fileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int ret = fileChooser.showDialog(null, "Открыть файл");
            if(ret == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();
                try {
                    Scanner scanner = new Scanner(file);
                    String str = scanner.nextLine();
                    arrayField.setText(str);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }


            }
        });

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.weighty = 1;
        gbl.setConstraints(fileButton, gbc);
        this.add(fileButton);

		this.pack();
		Rectangle rect = getGraphicsConfiguration().getBounds();
		this.setLocation(
			(rect.width - this.getWidth()) / 2,
			(rect.height - this.getHeight()) / 3);
		this.setVisible(true);
	}



	/*---- Methods ----*/


	public void actionPerformed(ActionEvent ev) {
		int size, scale, speed
				;

		try {
			size = Integer.parseInt(arraySizeInput.getText());
			scale = Integer.parseInt(scaleInput.getText());
			speed = speedSlider.getValue();
		} catch (NumberFormatException e) {
			return;
		}
		if (size <= 0 || scale <= 0 || speed <= 0 || Double.isInfinite(speed) || Double.isNaN(speed))
			return;
		final VisualSortArray array;

		if(!arrayField.getText().equals("")){

			String[] str = arrayField.getText().split(" ");
			int [] values = new int[str.length];
			for (int i = 0; i <str.length ; i++) {
				values[i] = Integer.parseInt(str[i]);
			}
		 array = new VisualSortArray(scale, speed, values);
		} else{
			array = new VisualSortArray(size, scale, speed);
		}


		final SortAlgorithm algorithm = algorithms.get(algorithmInput.getSelectedIndex());
		final int startDelay = 1000;  // In milliseconds
		new Thread() {
			public Thread thread = this;

			public void run() {
				initFrame();
				doSort();
			}

			private void initFrame() {
				// Do component layout
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

			private void doSort() {
				// Wait and sort
				try {
					Thread.sleep(startDelay);
					algorithm.sort(array);
				} catch (StopException|InterruptedException e) {
					return;
				}

				// Check and print
				String msg;
				try {
					array.assertSorted();
					msg = String.format("%s: %d comparisons, %d swaps",
							algorithm.getName(), array.getComparisonCount(), array.getSwapCount());
				} catch (AssertionError e) {
					msg = algorithm.getName() + ": Sorting failed";
				}
				System.out.println(msg);
			}
		}.start();
	}
}
