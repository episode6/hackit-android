package com.episode6.hackit.android.typed.intent;

import com.episode6.hackit.android.testing.DefaultMockitoTest;
import com.episode6.hackit.android.testing.DefaultTestRunner;
import com.episode6.hackit.chop.Chop;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Tests {@link com.episode6.hackit.android.typed.intent.ActionTranslatorImpl}
 */
@RunWith(DefaultTestRunner.class)
public class ActionTranslatorTest extends DefaultMockitoTest {

  public enum TestActionEnum {
    FIRST_ACTION,
    SECOND_ACTION,

    @SerializedName("some_weird_name")
    THIRD_ACTION
  }

  private ActionTranslator mActionTranslator;

  @Before
  public void setUp() {
    Gson gson = new GsonBuilder()
        .registerTypeAdapterFactory(new ActionEnumTypeAdapterFactory())
        .create();

    mActionTranslator = injectObjectWithMocks(ActionTranslatorImpl.class)
        .withInstance(ForIntentActionTranslator.class, Gson.class, gson)
        .create();
  }

  @Test
  public void testEnumEncode() {
    String expectedStringPrefix = testActionPrefix();

    String firstActionString = mActionTranslator.encodeAction(TestActionEnum.FIRST_ACTION);
    String secondActionString = mActionTranslator.encodeAction(TestActionEnum.SECOND_ACTION);
    String thirdActionString = mActionTranslator.encodeAction(TestActionEnum.THIRD_ACTION);

    Chop.d("firstAction: %s\nsecondAction: %s\nthirdAction: %s", firstActionString, secondActionString, thirdActionString);

    assertThat(firstActionString)
        .isEqualTo(expectedStringPrefix + "FIRST_ACTION");
    assertThat(secondActionString)
        .isEqualTo(expectedStringPrefix + "SECOND_ACTION");
    assertThat(thirdActionString)
        .isEqualTo("some_weird_name");
  }

  @Test
  public void testEnumDecode() {
    String actionPrefix = testActionPrefix();
    String firstActionString = actionPrefix + "FIRST_ACTION";
    String secondActionString = actionPrefix + "SECOND_ACTION";
    String thirdActionString = "some_weird_name";

    TestActionEnum firstAction = mActionTranslator.decodeAction(firstActionString, TestActionEnum.class);
    TestActionEnum secondAction = mActionTranslator.decodeAction(secondActionString, TestActionEnum.class);
    TestActionEnum thirdAction = mActionTranslator.decodeAction(thirdActionString, TestActionEnum.class);

    assertThat(firstAction)
        .isEqualTo(TestActionEnum.FIRST_ACTION);
    assertThat(secondAction)
        .isEqualTo(TestActionEnum.SECOND_ACTION);
    assertThat(thirdAction)
        .isEqualTo(TestActionEnum.THIRD_ACTION);
  }

  private String testActionPrefix() {
    return ActionTranslatorTest.class.getName() + "." + TestActionEnum.class.getSimpleName() + ".";
  }
}
