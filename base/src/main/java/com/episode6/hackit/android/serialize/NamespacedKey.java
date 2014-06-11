package com.episode6.hackit.android.serialize;

import com.google.common.base.Preconditions;

public final class NamespacedKey {

  private final Namespace mNamespace;
  private final String mFullName;

  NamespacedKey(Namespace namespace, String fullName) {
    mNamespace = Preconditions.checkNotNull(namespace);
    mFullName = Preconditions.checkNotNull(fullName);
  }

  public Namespace getNamespace() {
    return mNamespace;
  }

  @Override
  public int hashCode() {
    return mFullName.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    return mFullName.equals(o.toString());
  }

  @Override
  public String toString() {
    return mFullName;
  }
}
