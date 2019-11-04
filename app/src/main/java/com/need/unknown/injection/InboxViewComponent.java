package com.need.unknown.injection;

import com.need.unknown.view.impl.InboxFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = InboxViewModule.class)
public interface InboxViewComponent {
    void inject(InboxFragment fragment);
}