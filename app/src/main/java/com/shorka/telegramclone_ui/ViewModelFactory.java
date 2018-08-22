package com.shorka.telegramclone_ui;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.shorka.telegramclone_ui.chats_previews_screen.ChatPreviewViewModel;
import com.shorka.telegramclone_ui.contact_chat_screen.ContactChatViewModel;
import com.shorka.telegramclone_ui.db.UserRepository;

/**
 * Created by Kyrylo Avramenko on 8/20/2018.
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @SuppressLint("StaticFieldLeak")
    private static volatile ViewModelFactory INSTANCE;

    private final Application application;
    private final UserRepository userRepo;

    private ViewModelFactory(Application application, UserRepository userRepo) {
        this.application = application;
        this.userRepo = userRepo;
    }

    public static ViewModelFactory getInstance(Application application) {
//
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application, Injection.provideUserRepo(application));
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(ChatPreviewViewModel.class)) {
            return (T) new ChatPreviewViewModel(application, userRepo);
        }

        else if (modelClass.isAssignableFrom(ContactChatViewModel.class)) {
            return (T) new ContactChatViewModel(application, userRepo);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}