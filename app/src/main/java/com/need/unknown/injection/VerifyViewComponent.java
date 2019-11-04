package com.need.unknown.injection;

import com.need.unknown.view.impl.VerifyFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = VerifyViewModule.class)
public interface VerifyViewComponent {
    void inject(VerifyFragment fragment);
}