package questionnaire;

/**
 * Represents a Likert-scale question.
 */
public class Likert implements Question {

  private final String prompt;
  private final boolean required;
  private String answer;

  /**
   * Constructs a new Likert question with the given prompt and required flag.
   * @param prompt   the question text
   * @param required true if the question is mandatory, false if optional
   */
  public Likert(String prompt, boolean required) {
    this.prompt = prompt;
    this.required = required;
    this.answer = "";
  }

  /**
   * Returns the prompt of the question.
   *
   * @return the question text
   */
  @Override
  public String getPrompt() {
    return prompt;
  }

  /**
   * Indicates whether this question is required.
   *
   * @return true if required, false otherwise
   */
  @Override
  public boolean isRequired() {
    return required;
  }

  /**
   * Sets the answer for this question if it matches one of the valid Likert responses.
   * The comparison is case-insensitive.
   * @param response the user response
   * @throws IllegalArgumentException if the response is not a valid Likert option
   */
  @Override
  public void answer(String response) {
    for (LikertResponseOption option : LikertResponseOption.values()) {
      if (option.getText().equalsIgnoreCase(response)) {
        this.answer = option.getText();
        return;
      }
    }
    throw new IllegalArgumentException("Invalid Likert response.");
  }

  /**
   * Returns the stored answer, or an empty string if unanswered.
   * @return the answer text
   */
  @Override
  public String getAnswer() {
    return answer;
  }

  /**
   * Returns a deep copy of this Likert question, including its prompt, required flag,
   * and any existing answer.
   * @return a copy of the current question
   */
  @Override
  public Question copy() {
    Likert copy = new Likert(this.prompt, this.required);
    copy.answer = this.answer;
    return copy;
  }
}
