package questionnaire;

/**
 * Represents the five possible responses in a Likert scale.
 * Each response option has an associated numeric value and descriptive text label.
 */
enum LikertResponseOption {
  STRONGLY_DISAGREE(-2, "Strongly Disagree"),
  DISAGREE(-1, "Disagree"),
  NEUTRAL(0, "Neither Agree Nor Disagree"),
  AGREE(1, "Agree"),
  STRONGLY_AGREE(2, "Strongly Agree");

  private final int val;
  private final String txt;

  /**
   * Constructs a Likert response option with a numeric value and label.
   * @param val the numeric value associated with the response
   * @param txt the text description of the response
   */
  LikertResponseOption(int val, String txt) {
    this.val = val;
    this.txt = txt;
  }

  /**
   * Returns the numeric value of this Likert option.
   * @return the integer representation (e.g., -2 for Strongly Disagree)
   */
  int getValue() {
    return val;
  }

  /**
   * Returns the descriptive text label of this Likert option.
   * @return the full string label (e.g., "Strongly Agree")
   */
  String getText() {
    return txt;
  }
}
