package questionnaire;

/**
 * Represents a general interface for different types of questions in a questionnaire.
 * All questions have a prompt, a required status, and accept an answer based on specific rules.
 */
public interface Question {
  String getPrompt();
  boolean isRequired();
  void answer(String response);
  String getAnswer();
  Question copy();
}
