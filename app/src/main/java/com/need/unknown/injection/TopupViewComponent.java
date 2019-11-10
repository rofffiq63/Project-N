package com.need.unknown.injection;

import com.need.unknown.view.impl.TopupActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = TopupViewModule.class)
public interface TopupViewComponent {
    void inject(TopupActivity activity);
}