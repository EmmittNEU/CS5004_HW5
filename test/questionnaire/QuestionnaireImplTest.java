package questionnaire;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

public class QuestionnaireImplTest {

  QuestionnaireImpl questionnaire;

  @BeforeEach
  public void setUp() {
    questionnaire = new QuestionnaireImpl();
    questionnaire.addQuestion("q1", new YesNo("Do you like coffee?", true));
    questionnaire.addQuestion("q2", new ShortAnswer("Describe your morning routine", false));
    questionnaire.addQuestion("q3", new Likert("I feel productive today.", true));
  }

  @Test
  public void testAddAndGetByNumber() {
    Assertions.assertEquals("Do you like coffee?", questionnaire.getQuestion(1).getPrompt());
    Assertions.assertEquals("Describe your morning routine", questionnaire.getQuestion(2).getPrompt());
  }

  @Test
  public void testAddAndGetByIdentifier() {
    Assertions.assertEquals("I feel productive today.", questionnaire.getQuestion("q3").getPrompt());
  }

  @Test
  public void testRemoveQuestion() {
    questionnaire.removeQuestion("q2");
    Assertions.assertThrows(NoSuchElementException.class, () -> questionnaire.getQuestion("q2"));
  }

  @Test
  public void testIsCompleteFalseInitially() {
    Assertions.assertFalse(questionnaire.isComplete());
  }

  @Test
  public void testIsCompleteTrueAfterAnswers() {
    questionnaire.getQuestion("q1").answer("yes");
    questionnaire.getQuestion("q3").answer("Neutral");
    Assertions.assertTrue(questionnaire.isComplete());
  }

  @Test
  public void testGetRequiredAndOptionalQuestions() {
    List<Question> required = questionnaire.getRequiredQuestions();
    List<Question> optional = questionnaire.getOptionalQuestions();

    Assertions.assertEquals(2, required.size());
    Assertions.assertEquals(1, optional.size());
  }

  @Test
  public void testFilter() {
    Questionnaire filtered = questionnaire.filter(q -> !q.isRequired());
    Assertions.assertEquals(1, filtered.getOptionalQuestions().size());
    Assertions.assertEquals("Describe your morning routine", filtered.getQuestion(1).getPrompt());
  }

  @Test
  public void testFold() {
    questionnaire.getQuestion("q1").answer("No");
    questionnaire.getQuestion("q3").answer("Agree");
    int len = questionnaire.fold((q, sum) -> sum + q.getAnswer().length(), 0);
    Assertions.assertEquals("No".length() + "Agree".length(), len);
  }

  @Test
  public void testToStringFormatting() {
    questionnaire.getQuestion("q1").answer("yes");
    questionnaire.getQuestion("q2").answer("Wake up and run.");
    questionnaire.getQuestion("q3").answer("Agree");

    String expected = String.join("\n\n",
            "Question: Do you like coffee?\n\nAnswer: yes",
            "Question: Describe your morning routine\n\nAnswer: Wake up and run.",
            "Question: I feel productive today.\n\nAnswer: Agree"
    );

    Assertions.assertEquals(expected, questionnaire.toString());
  }

  @Test
  public void testSort() {
    questionnaire.sort((q1, q2) -> q1.getPrompt().compareTo(q2.getPrompt()));
    Assertions.assertEquals("Describe your morning routine", questionnaire.getQuestion(1).getPrompt());
    Assertions.assertEquals("Do you like coffee?", questionnaire.getQuestion(2).getPrompt());
    Assertions.assertEquals("I feel productive today.", questionnaire.getQuestion(3).getPrompt());
  }
}