package com.need.unknown.injection;

import com.need.unknown.view.impl.CustomerServiceActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = CustomerViewModule.class)
public interface CustomerViewComponent {
    void inject(CustomerServiceActivity activity);
}