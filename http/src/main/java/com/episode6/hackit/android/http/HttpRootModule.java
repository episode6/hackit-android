package com.episode6.hackit.android.ui;

import com.episode6.hackit.android.baseapp.module.BaseApplicationModule;

import dagger.Module;

@Module(
    complete = false,
    library = true,
    includes = {
        BaseApplicationModule.class})
public class HttpRootModule {
}
