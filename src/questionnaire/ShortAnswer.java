package questionnaire;

/**
 * Represents a short-answer question.
 * The response must be no more than 280 characters long.
 */
public class ShortAnswer implements Question {
  private final String prompt;
  private final boolean required;
  private String answer;

  /**
   * A constructor for the short answer class.
   * @param prompt the short answer prompt.
   * @param required if the form is required.
   */
  public ShortAnswer(String prompt, boolean required) {
    this.prompt = prompt;
    this.required = required;
    this.answer = "";
  }

  /**
   * A getting method for the short answer prompt.
   * @return the short answer prompt.
   */
  @Override
  public String getPrompt() {
    return prompt;
  }

  /**
   * a function to check if the is required condition is true.
   * @return a bool indicating if the form was required.
   */
  @Override
  public boolean isRequired() {
    return required;
  }

  /**
   * An override of the response attribute defined in the question interface.
   * @param response the user's response.
   */
  @Override
  public void answer(String response) {
    if (response.length() <= 280) {
      this.answer = response;
    } else {
      throw new IllegalArgumentException("Answer exceeds 280 characters.");
    }
  }

  /**
   * An override of the get answer method.
   * @return the answer of the short answer.
   */
  @Override
  public String getAnswer() {
    return answer;
  }

  /**
   * An override of the method that copies questions.
   * @return the copied question.
   */
  @Override
  public Question copy() {
    ShortAnswer copy = new ShortAnswer(this.prompt, this.required);
    copy.answer = this.answer;
    return copy;
  }
}
