package com.need.unknown.injection;

import com.need.unknown.view.impl.LoginFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = LoginViewModule.class)
public interface LoginViewComponent {
    void inject(LoginFragment fragment);
}