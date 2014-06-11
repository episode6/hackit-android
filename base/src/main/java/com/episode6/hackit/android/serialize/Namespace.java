package com.episode6.hackit.android.serialize;

import com.google.common.base.Preconditions;

public abstract class Namespace {

  private final String mDelineater;
  private final String mName;

  protected Namespace(String delineater) {
    mDelineater = Preconditions.checkNotNull(delineater);
    mName = null;
  }

  protected Namespace(Namespace parentNamespace, String name) {
    Preconditions.checkNotNull(parentNamespace);

    mDelineater = parentNamespace.mDelineater;
    mName = parentNamespace.buildName(name);
  }

  private String buildName(String newNameSegment) {
    Preconditions.checkNotNull(newNameSegment);
    if (isAnonymous()) {
      return newNameSegment;
    }
    return toString() + mDelineater + newNameSegment;
  }

  public boolean isAnonymous() {
    return mName == null;
  }

  protected NamespacedKey buildKey(String newNameSegment) {
    return new NamespacedKey(this, buildName(newNameSegment));
  }

  @Override
  public String toString() {
    return mName == null ? "" : mName;
  }
}
