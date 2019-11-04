package com.need.unknown.injection;

import com.need.unknown.view.impl.HistoryFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = HistoryViewModule.class)
public interface HistoryViewComponent {
    void inject(HistoryFragment fragment);
}