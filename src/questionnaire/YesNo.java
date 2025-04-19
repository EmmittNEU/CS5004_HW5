package questionnaire;

/**
 * Represents a Yes/No question that can be answered with either "Yes" or "No"
 * (case-insensitive). This class implements the {@link Question} interface.
 * It stores the question prompt, whether the question is required, and the user's answer.
 */
public class YesNo implements Question {

  private final String prompt;
  private final boolean required;
  private String answer;

  /**
   * Constructs a new YesNo question with a prompt and required flag.
   *
   * @param prompt   the text of the question
   * @param required true if the question is required, false otherwise
   */
  public YesNo(String prompt, boolean required) {
    this.prompt = prompt;
    this.required = required;
    this.answer = "";
  }

  /**
   * Returns the prompt text of this question.
   *
   * @return the question prompt
   */
  @Override
  public String getPrompt() {
    return prompt;
  }

  /**
   * Indicates whether this question is required to be answered.
   *
   * @return true if the question is required, false if optional
   */
  @Override
  public boolean isRequired() {
    return required;
  }

  /**
   * Stores the user's answer if it is a valid Yes/No response.
   * Valid responses are "yes" or "no" (case-insensitive).
   *
   * @param response the user's response
   * @throws IllegalArgumentException if the response is not "yes" or "no"
   */
  @Override
  public void answer(String response) {
    if (response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("no")) {
      this.answer = response;
    } else {
      throw new IllegalArgumentException("Answer must be 'yes' or 'no'");
    }
  }

  /**
   * Returns the answer to this question.
   *
   * @return the answer as a String, or an empty string if unanswered
   */
  @Override
  public String getAnswer() {
    return answer;
  }

  /**
   * Creates a deep copy of this YesNo question, including the prompt,
   * required flag, and any answer that has been given.
   *
   * @return a new YesNo object with the same values
   */
  @Override
  public Question copy() {
    YesNo copy = new YesNo(this.prompt, this.required);
    copy.answer = this.answer;
    return copy;
  }
}