package com.need.unknown.injection;

import com.need.unknown.view.impl.RegisterActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = RegisterViewModule.class)
public interface RegisterViewComponent {
    void inject(RegisterActivity activity);
}