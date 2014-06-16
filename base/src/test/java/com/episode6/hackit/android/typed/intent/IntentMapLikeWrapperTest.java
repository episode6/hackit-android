package com.episode6.hackit.android.typed.intent;

import android.content.Intent;

import com.episode6.hackit.android.testing.DefaultMockitoTest;
import com.episode6.hackit.android.testing.DefaultTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;

@RunWith(DefaultTestRunner.class)
public class IntentMapLikeWrapperTest extends DefaultMockitoTest {

  @Mock Intent mIntent;

  private IntentMapLikeWrapper mIntentMapLikeWrapper;

  @Before
  public void setup() {
    mIntentMapLikeWrapper = new IntentMapLikeWrapper(mIntent);
  }

  @Test
  public void testSetters() {
    mIntentMapLikeWrapper.putBool("bool", true);
    mIntentMapLikeWrapper.putInt("int", 10);
    mIntentMapLikeWrapper.putFloat("float", 11f);
    mIntentMapLikeWrapper.putLong("long", 12L);
    mIntentMapLikeWrapper.putString("string", "13");
    mIntentMapLikeWrapper.removeKey("removal");

    InOrder inOrder = Mockito.inOrder(mIntent);
    inOrder.verify(mIntent).putExtra("bool", true);
    inOrder.verify(mIntent).putExtra("int", 10);
    inOrder.verify(mIntent).putExtra("float", 11f);
    inOrder.verify(mIntent).putExtra("long", 12L);
    inOrder.verify(mIntent).putExtra("string", "13");
    inOrder.verify(mIntent).removeExtra("removal");
    inOrder.verifyNoMoreInteractions();
  }

  @Test
  public void testGetters() {
    mIntentMapLikeWrapper.getBool("bool");
    mIntentMapLikeWrapper.getInt("int");
    mIntentMapLikeWrapper.getFloat("float");
    mIntentMapLikeWrapper.getLong("long");
    mIntentMapLikeWrapper.getString("string");
    mIntentMapLikeWrapper.containsKey("contains");

    InOrder inOrder = Mockito.inOrder(mIntent);
    inOrder.verify(mIntent).getBooleanExtra(eq("bool"), anyBoolean());
    inOrder.verify(mIntent).getIntExtra(eq("int"), anyInt());
    inOrder.verify(mIntent).getFloatExtra(eq("float"), anyFloat());
    inOrder.verify(mIntent).getLongExtra(eq("long"), anyLong());
    inOrder.verify(mIntent).getStringExtra(eq("string"));
    inOrder.verify(mIntent).hasExtra("contains");
    inOrder.verifyNoMoreInteractions();
  }
}
