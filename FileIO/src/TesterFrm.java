import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class TesterFrm extends JFrame implements ActionListener, KeyListener {
	private JTextArea questionTa;
	private JTextField responseTf;
	private JButton okBtn;
	private JButton viewBtn;
	private int qIndex;
	private ArrayList<QnA> lst;
	private int numRight = 0;
	private int numWrong = 0;
	private Color cntBackgroundColor = new Color(200, 200, 200);
	private Color qnaBackgroundColor = new Color(230, 230, 230);
	private Random rand = new Random(System.currentTimeMillis());
	private boolean endTestFlg = false;
	 

	public TesterFrm() {
		// make panels
		Container c = getContentPane();
		EtchedBorder brdr = new EtchedBorder();
		JPanel centerPnl = new JPanel();
		centerPnl.setBackground(cntBackgroundColor);
		centerPnl.setBorder(brdr);
		JPanel southPnl = new JPanel();
		southPnl.setBorder(brdr);
		JPanel questionPnl = new JPanel();
		questionPnl.setBackground(qnaBackgroundColor);
		questionPnl.setBorder(brdr);
		JPanel responsePnl = new JPanel();
		responsePnl.setBackground(qnaBackgroundColor);
		responsePnl.setBorder(brdr);
		JPanel buttonPnl = new JPanel();
		buttonPnl.setBorder(brdr);

		// make components
		questionTa = new JTextArea(3, 35);

		questionTa.setLineWrap(true);
		questionTa.setWrapStyleWord(true);
		questionTa.setBorder(brdr);
		responseTf = new JTextField(35);
		okBtn = new JButton("OK");
		viewBtn = new JButton("View");

		// add components to panels
		JLabel qLbl = new JLabel("Question:");
		JLabel rLbl = new JLabel("Response:");

		questionPnl.add(qLbl);
		questionPnl.add(questionTa);
		responsePnl.add(rLbl);
		responsePnl.add(responseTf);
		southPnl.add(okBtn);
		southPnl.add(viewBtn);
		centerPnl.add(questionPnl);
		centerPnl.add(responsePnl);
		c.add(centerPnl, BorderLayout.CENTER);
		c.add(southPnl, BorderLayout.SOUTH);

		// events
		okBtn.addActionListener(this);
		viewBtn.addActionListener(this);
		responseTf.addKeyListener(this);

		// window stuff
		setSize(500, 300);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void newQuestion() {
		if (!endTestFlg) {
			QnA qna = lst.get(qIndex);
			questionTa.setText(qna.getQuestion());
		}
	}

	public void compareResponse() {
		String res = responseTf.getText();
		String ans = lst.get(qIndex).getAnswer();
		res = res.toLowerCase();
		ans = ans.toLowerCase();
		res = res.trim();
		ans = ans.trim();

		if (res.equals(ans)) {
			lst.remove(qIndex);
			numRight++;
		} else {
			JOptionPane.showMessageDialog(this, "Wrong! It's: " + ans);
			numWrong++;
		}
		responseTf.setText("");
		if (lst.size() >= 1) {
			qIndex = rand.nextInt(lst.size());
		} else {
			endTest();
		}
	}

	public void endTest() {
		DecimalFormat df = new DecimalFormat("###.#");
		double score = 100 * (double) numRight / (numRight + numWrong);

		int input = JOptionPane.showConfirmDialog(null, "Score: " + df.format(score), 
				"Do you want to try again?",
				JOptionPane.YES_NO_OPTION);
		if (input == JOptionPane.YES_OPTION) {
			numRight = 0;
			numWrong = 0;
			endTestFlg = false;
			reStartTest();
			
		} else {
			System.exit(0);
		}
	}

	private void reStartTest() {
		System.out.println("reStartTest");
		FileUtils fobj = new FileUtils();
		String fName = fobj.getQuestionFile();
		ArrayList<QnA> lst = fobj.readFile(fName);
		
		setQnAList(lst);
		newQuestion();
	}

	public void setQnAList(ArrayList<QnA> lst) {
		this.lst = lst;
		qIndex = rand.nextInt(lst.size());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		if (src == okBtn) {
			System.out.println("Clicked!!!!!");
			if (!endTestFlg) {
				compareResponse();
				newQuestion();
			}
		}		
	}

	public static void main(String[] args) {
		TesterFrm tObj = new TesterFrm();
		FileUtils fobj = new FileUtils();
		// get file location
		String fName = fobj.getQuestionFile();
		ArrayList<QnA> lst = fobj.readFile(fName);
		tObj.setQnAList(lst);
		tObj.newQuestion();

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ENTER) {
			if (!endTestFlg) {
				compareResponse();
				newQuestion();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
