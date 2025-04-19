package questionnaire;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * An implementation of the {@link Questionnaire} interface using a map-based structure.
 * This class allows adding, removing, sorting, filtering, and answering questions
 * while preserving the order in which questions are added or sorted.
 */
public class QuestionnaireImpl implements Questionnaire {

  /**
   * Stores questions with unique string identifiers.
   */
  private final Map<String, Question> questionMap = new LinkedHashMap<>();

  /**
   * Maintains the current display order of questions for sequential access.
   */
  private List<String> sortedKeys = new ArrayList<>();

  /**
   * Constructs an empty questionnaire with no questions.
   */
  public QuestionnaireImpl() {
    this.sortedKeys = new ArrayList<>();
  }

  /**
   * Adds a new question to the questionnaire.
   *
   * @param identifier the unique identifier for the question
   * @param q          the question to be added
   * @throws IllegalArgumentException if the identifier is null, empty, or already used
   */
  @Override
  public void addQuestion(String identifier, Question q) {
    if (identifier == null || identifier.isEmpty()) {
      throw new IllegalArgumentException("Identifier must not be null or empty");
    }
    if (questionMap.containsKey(identifier)) {
      throw new IllegalArgumentException("Identifier must be unique.");
    }
    questionMap.put(identifier, q);
    sortedKeys.add(identifier);
  }

  /**
   * Removes a question from the questionnaire by its identifier.
   *
   * @param identifier the identifier of the question to remove
   * @throws NoSuchElementException if the identifier does not exist
   */
  @Override
  public void removeQuestion(String identifier) {
    if (!questionMap.containsKey(identifier)) {
      throw new NoSuchElementException("Identifier not found.");
    }
    questionMap.remove(identifier);
    sortedKeys.remove(identifier);
  }

  /**
   * Retrieves a question based on its position in the current order.
   *
   * @param num the 1-based index of the question
   * @return the corresponding question
   * @throws IndexOutOfBoundsException if the index is out of bounds
   */
  @Override
  public Question getQuestion(int num) {
    if (num < 1 || num > sortedKeys.size()) {
      throw new IndexOutOfBoundsException("Invalid question number.");
    }
    return questionMap.get(sortedKeys.get(num - 1));
  }

  /**
   * Retrieves a question by its unique identifier.
   *
   * @param identifier the identifier of the question
   * @return the corresponding question
   * @throws NoSuchElementException if the identifier is not found
   */
  @Override
  public Question getQuestion(String identifier) {
    if (!questionMap.containsKey(identifier)) {
      throw new NoSuchElementException("Identifier not found.");
    }
    return questionMap.get(identifier);
  }

  /**
   * Gets a list of all required questions in their current order.
   *
   * @return list of required questions
   */
  @Override
  public List<Question> getRequiredQuestions() {
    List<Question> result = new ArrayList<>();
    for (String key : sortedKeys) {
      Question q = questionMap.get(key);
      if (q.isRequired()) result.add(q);
    }
    return result;
  }

  /**
   * Gets a list of all optional questions in their current order.
   *
   * @return list of optional questions
   */
  @Override
  public List<Question> getOptionalQuestions() {
    List<Question> result = new ArrayList<>();
    for (String key : sortedKeys) {
      Question q = questionMap.get(key);
      if (!q.isRequired()) result.add(q);
    }
    return result;
  }

  /**
   * Determines whether all required questions have been answered.
   *
   * @return true if all required questions have non-empty answers, false otherwise
   */
  @Override
  public boolean isComplete() {
    return getRequiredQuestions().stream().allMatch(q -> !q.getAnswer().isEmpty());
  }

  /**
   * Returns a list of all answers in the current question order.
   *
   * @return list of answers as strings
   */
  @Override
  public List<String> getResponses() {
    List<String> responses = new ArrayList<>();
    for (String key : sortedKeys) {
      responses.add(questionMap.get(key).getAnswer());
    }
    return responses;
  }

  /**
   * Creates a new questionnaire with only the questions that satisfy a given predicate.
   * All questions are deep copied.
   *
   * @param pq the predicate to filter questions
   * @return a new filtered {@link Questionnaire}
   */
  @Override
  public Questionnaire filter(Predicate<Question> pq) {
    QuestionnaireImpl filtered = new QuestionnaireImpl();
    for (String key : sortedKeys) {
      Question q = questionMap.get(key);
      if (pq.test(q)) {
        filtered.addQuestion(key, q.copy());
      }
    }
    return filtered;
  }

  /**
   * Sorts the questions in the questionnaire based on a given comparator.
   * Affects the order used in {@link #getQuestion(int)} and output.
   *
   * @param comp a comparator for sorting questions
   */
  @Override
  public void sort(Comparator<Question> comp) {
    sortedKeys.sort((a, b) -> comp.compare(questionMap.get(a), questionMap.get(b)));
  }

  /**
   * Folds over all questions using a binary function and a seed value.
   *
   * @param bf   the folding function to apply to each question and result
   * @param seed the initial value
   * @param <R>  the result type
   * @return the final folded result
   */
  @Override
  public <R> R fold(BiFunction<Question, R, R> bf, R seed) {
    R result = seed;
    for (String key : sortedKeys) {
      result = bf.apply(questionMap.get(key), result);
    }
    return result;
  }

  /**
   * Converts the questionnaire into a readable string format.
   * Each question is shown with its prompt and answer, separated by two newlines.
   * No trailing newlines after the final answer.
   *
   * @return string representation of the questionnaire
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < sortedKeys.size(); i++) {
      Question q = questionMap.get(sortedKeys.get(i));
      sb.append("Question: ").append(q.getPrompt()).append("\n\n");
      sb.append("Answer: ").append(q.getAnswer());
      if (i < sortedKeys.size() - 1) {
        sb.append("\n\n");
      }
    }
    return sb.toString();
  }
}
