package com.need.unknown.injection;

import com.need.unknown.view.impl.HomeFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = HomeViewModule.class)
public interface HomeViewComponent {
    void inject(HomeFragment fragment);
}