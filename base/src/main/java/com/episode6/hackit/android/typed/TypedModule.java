package com.episode6.hackit.android.typed;

import com.episode6.hackit.android.app.scope.ApplicationScope;
import com.episode6.hackit.android.typed.bundle.BundleModule;
import com.episode6.hackit.android.typed.intent.IntentModule;
import com.episode6.hackit.android.typed.preference.PrefModule;

import dagger.Module;

@Module(
    library = true,
    includes = {
        ApplicationScope.class,

        PrefModule.class,
        BundleModule.class,
        IntentModule.class})
public class TypedModule {
}
