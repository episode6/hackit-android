package com.episode6.hackit.android.baseapp.module;

import com.episode6.hackit.android.app.scope.ContextScope;
import com.episode6.hackit.android.app.scope.ServiceScope;
import com.episode6.hackit.android.baseapp.component.BaseService;

import dagger.Module;

@Module(
    library = true,
    addsTo = BaseApplicationModule.class,
    includes = {
        ContextScope.class,
        ServiceScope.class},
    injects = {BaseService.class})
public class BaseServiceModule {
}
