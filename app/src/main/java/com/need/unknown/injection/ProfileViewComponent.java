package com.need.unknown.injection;

import com.need.unknown.view.impl.ProfileFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = ProfileViewModule.class)
public interface ProfileViewComponent {
    void inject(ProfileFragment fragment);
}