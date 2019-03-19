package edu.und.seau.di.components;

import dagger.Component;
import edu.und.seau.di.module.InteractionModule;
import edu.und.seau.presentation.presenters.SelectUAVPresenter;

@Component(modules = InteractionModule.class)
public interface SelectUAVFragmentComponent {

    SelectUAVPresenter getSelectUAVPresenter();
}
