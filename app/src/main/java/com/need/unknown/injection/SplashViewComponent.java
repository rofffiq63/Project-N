package com.need.unknown.injection;

import com.need.unknown.view.impl.SplashActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = SplashViewModule.class)
public interface SplashViewComponent {
    void inject(SplashActivity activity);
}