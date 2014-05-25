package com.episode6.hackit.android.baseapp.module;

import com.episode6.hackit.android.baseapp.component.BaseActionBarFragmentActivity;
import com.episode6.hackit.android.app.scope.ActivityScope;
import com.episode6.hackit.android.app.scope.ContextScope;
import com.episode6.hackit.android.baseapp.component.BaseFragmentActivity;

import dagger.Module;

@Module(
    library = true,
    addsTo = BaseApplicationModule.class,
    includes = {
        ContextScope.class,
        ActivityScope.class},
    injects = {
        BaseFragmentActivity.class,
        BaseActionBarFragmentActivity.class})
public class BaseActivityModule {


}
