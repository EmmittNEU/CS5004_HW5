package questionnaire;

import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {

  @Test
  public void testYesNoValidAnswers() {
    YesNo q = new YesNo("Are you over 18?", true);
    q.answer("yes");
    assertEquals("yes", q.getAnswer());

    q.answer("NO");
    assertEquals("NO", q.getAnswer());
  }

  @Test
  public void testYesNoInvalidAnswer() {
    YesNo q = new YesNo("Are you over 18?", true);
    assertThrows(IllegalArgumentException.class, () -> q.answer("maybe"));
  }

  @Test
  public void testShortAnswerValidLength() {
    ShortAnswer q = new ShortAnswer("Describe yourself", false);
    String ans = "This is a valid answer.";
    q.answer(ans);
    assertEquals(ans, q.getAnswer());
  }

  @Test
  public void testShortAnswerTooLong() {
    ShortAnswer q = new ShortAnswer("Describe yourself", false);
    String longAnswer = "a".repeat(281);
    assertThrows(IllegalArgumentException.class, () -> q.answer(longAnswer));
  }

  @Test
  public void testLikertValidAnswer() {
    Likert q = new Likert("I enjoy programming.", true);
    q.answer("Agree");
    assertEquals("Agree", q.getAnswer());

    q.answer("strongly disagree");
    assertEquals("Strongly Disagree", q.getAnswer());
  }

  @Test
  public void testLikertInvalidAnswer() {
    Likert q = new Likert("I enjoy programming.", true);
    assertThrows(IllegalArgumentException.class, () -> q.answer("sort of"));
  }

  @Test
  public void testQuestionCopy() {
    Question original = new ShortAnswer("Copy this?", true);
    original.answer("Copied!");
    Question copy = original.copy();

    assertNotSame(original, copy);
    assertEquals(original.getPrompt(), copy.getPrompt());
    assertEquals(original.getAnswer(), copy.getAnswer());
    assertEquals(original.isRequired(), copy.isRequired());
  }
}

