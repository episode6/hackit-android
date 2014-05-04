package com.episode6.hackit.android.testing;

import com.episode6.hackit.android.testing.inject.MockInjector;
import com.episode6.hackit.chop.Chop;
import com.episode6.hackit.chop.tree.StdOutDebugTree;

import org.junit.runners.model.InitializationError;
import org.mockito.MockitoAnnotations;
import org.robolectric.AndroidManifest;
import org.robolectric.DefaultTestLifecycle;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.SdkConfig;
import org.robolectric.TestLifecycle;
import org.robolectric.annotation.Config;
import org.robolectric.res.Fs;

public class DefaultTestRunner extends RobolectricTestRunner {

  private static final StdOutDebugTree STD_OUT_TREE = new StdOutDebugTree();

  public DefaultTestRunner(Class<?> testClass) throws InitializationError {
    super(testClass);
  }

  @Override
  protected SdkConfig pickSdkVersion(AndroidManifest appManifest, Config config) {
    return new SdkConfig(18);
  }

  @Override protected AndroidManifest getAppManifest(Config config) {
    String manifestProperty = System.getProperty("android.manifest");
    if (config.manifest().equals(Config.DEFAULT) && manifestProperty != null) {
      String resProperty = System.getProperty("android.resources");
      String assetsProperty = System.getProperty("android.assets");
      return new AndroidManifest(Fs.fileFromPath(manifestProperty), Fs.fileFromPath(resProperty),
          Fs.fileFromPath(assetsProperty));
    }
    return super.getAppManifest(config);
  }

  @Override
  protected Class<? extends TestLifecycle> getTestLifecycleClass() {
    return TestLifeCycleWithAutoMockInjection.class;
  }

  public static class TestLifeCycleWithAutoMockInjection extends DefaultTestLifecycle {

    @Override
    public void prepareTest(Object test) {
      Chop.plantTree(STD_OUT_TREE);
      MockitoAnnotations.initMocks(test);
      MockInjector.init().withMocksFrom(test);
      super.prepareTest(test);
    }
  }
}