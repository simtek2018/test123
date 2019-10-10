
public class QnA {
	private String question;
	private String answer;
	private int timesAsked;
	private int totalCorrect;
	private int totalWrong;
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public String toString() {
		return ("Q: " + question + "\nA: " + answer + "\n");
	}
}
