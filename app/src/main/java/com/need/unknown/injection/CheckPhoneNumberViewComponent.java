package com.need.unknown.injection;

import com.need.unknown.view.impl.CheckPhoneNumberActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = CheckPhoneNumberViewModule.class)
public interface CheckPhoneNumberViewComponent {
    void inject(CheckPhoneNumberActivity activity);
}